/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import traversing.JSONWalker;
import traversing.TraversingOption;

/**
 * Class transforms if-else statements into ternary operator (x)?y:z; It's
 * possible only if consequent and alternate consists of expressions.
 *
 * If alternate is null, then it's filled with rubbish function.
 *
 * @author Sergey
 */
public class TernaryTransformer implements Mangler {

    private final int MAX_RUBBISH_FUNCTIONS = 5;

    private ArrayList<String> rubbishFunctions;

    private boolean isRubbishNeeded = false;

    @Override
    @SuppressWarnings("unchecked")
    public void mangle(JSONObject code) {

        //checking the necessity of rubbish functions
        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    isRubbishNeeded |= node.get("type").equals("IfStatement")
                    && node.get("alternate") == null;
                    return TraversingOption.CONTINUE;
                });
        
        if (isRubbishNeeded) {
            rubbishFunctions = new ArrayList<>();
            
            int functionsNumber = new Random(System.nanoTime()).nextInt(MAX_RUBBISH_FUNCTIONS) + 1;
            
            for (int i = 0; i < functionsNumber; i++) {
                String functionName = addRubbishFunction(code);
                rubbishFunctions.add(functionName);
            }
        }

        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    if (node.get("type").equals("IfStatement") && parent != null) {
                        if (parent instanceof JSONObject) {
                            for (Object objEntry : ((Map) parent).entrySet()) {
                                Entry entry = (Entry) objEntry;
                                if (entry.getValue().equals(node)) {
                                    if (canTransformIfNode(node)) {
                                        entry.setValue(transformIfStatement(node));
                                    }
                                }
                            }
                        } else {
                            JSONArray parentArray = (JSONArray) parent;
                            for (int i = 0; i < parentArray.size(); i++) {
                                JSONObject child = (JSONObject) parentArray.get(i);
                                if (child == node) {
                                    if (canTransformIfNode(node)) {
                                        parentArray.set(i, transformIfStatement(node));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    return TraversingOption.CONTINUE;
                });

    }

    private JSONObject transformIfStatement(JSONObject ifStatement) {
        JSONObject transformedNode = new JSONObject();
        transformedNode.put("type", "ExpressionStatement");

        JSONObject expressionNode = new JSONObject();
        expressionNode.put("type", "ConditionalExpression");
        expressionNode.put("test", ifStatement.get("test"));

        JSONObject consequentNode = (JSONObject) ifStatement.get("consequent");
        switch (consequentNode.get("type").toString()) {
            case "ExpressionStatement":
                consequentNode = (JSONObject) consequentNode.get("expression");
                break;

            case "BlockStatement":
                consequentNode.put("type", "SequenceExpression");
                consequentNode.put("expressions", consequentNode.get("body"));
                consequentNode.remove("body");
                JSONArray expressions = (JSONArray) consequentNode.get("expressions");
                for (int i = 0; i < expressions.size(); i++) {
                    JSONObject nestedExpr = (JSONObject) expressions.get(i);
                    if (nestedExpr.get("type").equals("ExpressionStatement")) {
                        expressions.set(i, nestedExpr.get("expression"));
                    }
                }
                break;
        }

        JSONObject alternateNode = (JSONObject) ifStatement.get("alternate");
        if (alternateNode != null) {
            switch (alternateNode.get("type").toString()) {
                case "ExpressionStatement":
                    alternateNode = (JSONObject) alternateNode.get("expression");
                    break;

                case "BlockStatement":
                    alternateNode.put("type", "SequenceExpression");
                    alternateNode.put("expressions", alternateNode.get("body"));
                    alternateNode.remove("body");
                    JSONArray expressions = (JSONArray) alternateNode.get("expressions");
                    for (int i = 0; i < expressions.size(); i++) {
                        JSONObject nestedExpr = (JSONObject) expressions.get(i);
                        if (nestedExpr.get("type").equals("ExpressionStatement")) {
                            expressions.set(i, nestedExpr.get("expression"));
                        }
                    }
                    break;
            }
        } else {
            String functionName = getRandomFuncName();

            JSONObject calleeNode = new JSONObject();
            calleeNode.put("type", "Identifier");
            calleeNode.put("name", functionName);

            JSONObject expression = new JSONObject();
            expression.put("type", "CallExpression");
            expression.put("arguments", new JSONArray());
            expression.put("callee", calleeNode);

            alternateNode = new JSONObject();
            alternateNode.put("type", "ExpressionStatement");
            alternateNode.put("expression", expression);
        }

        expressionNode.put("consequent", consequentNode);
        expressionNode.put("alternate", alternateNode);

        transformedNode.put("expression", expressionNode);

        return transformedNode;
    }

    private String getRandomFuncName() {
        int numberOfFunctions = rubbishFunctions.size();
        String functionName = rubbishFunctions.get(
                new Random(System.nanoTime()).nextInt(numberOfFunctions));
        return functionName;
    }

    /**
     * Checks ifObject for transforming into ternary operator.
     *
     * @param ifObject
     * @return
     */
    private boolean canTransformIfNode(JSONObject ifObject) {
        return isNodeExpression((JSONObject) ifObject.get("consequent"))
                && isNodeExpression((JSONObject) ifObject.get("alternate"));
    }

    /**
     * Checks whether this node is expression, or if it's block statement,
     * checks all children of the block.
     *
     * @param node
     * @return
     */
    private boolean isNodeExpression(JSONObject node) {
        if (node == null) {
            return true; //for else branch
        }
        String nodeType = (String) node.get("type");
        switch (nodeType) {
            case "ExpressionStatement":
                return true;

            case "BlockStatement":
                JSONArray children = (JSONArray) node.get("body");
                boolean result = true;
                for (Object child : children) {
                    result &= isNodeExpression((JSONObject) child);
                }
                return result;

            default:
                return nodeType.contains("Expression");
        }
    }

    /**
     * Generates rubbish function and inserts it in global scope in source code.
     *
     * @param code
     * @return name of generated function
     */
    private String addRubbishFunction(JSONObject code) {
        Random r = new Random(System.nanoTime());
        final int numOfExpressions = r.nextInt(5) + 1;

        StringBuilder newExpression = new StringBuilder();
        for (int i = 0; i < numOfExpressions - 1; i++) {
            newExpression.append(r.nextDouble());
            switch (r.nextInt(3)) {
                case 0:
                    newExpression.append("/");
                    break;
                case 1:
                    newExpression.append("*");
                    break;
                case 2:
                    newExpression.append("+");
                    break;
            }
        }
        newExpression.append(r.nextDouble());

        int[] numberCharCodes = newExpression.chars().toArray();
        newExpression.setLength(0);
        String funcName = "f" + System.nanoTime();
        newExpression.append("function ").append(funcName);
        newExpression.append("(){eval(\"String.fromCharCode(");
        for (int i = 0; i < numberCharCodes.length; i++) {
            newExpression.append(numberCharCodes[i]);
            if (i < numberCharCodes.length - 1) {
                newExpression.append(",");
            }
        }
        newExpression.append(")\")}");

        try {
            JSONObject newFunction = new Esprima().parseCode(newExpression.toString());
            newFunction = (JSONObject) ((List) newFunction.get("body")).get(0);

            JSONArray bodyArray = (JSONArray) code.get("body");
            if (bodyArray != null) {
                bodyArray.add(newFunction);
            }
        } catch (FileNotFoundException | ScriptException ex) {
            Logger.getLogger(TernaryTransformer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return funcName;
    }

}
