/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Sergey
 */
public class ConstantPruner implements Mangler {

    private final int BASE_FUNCTION_LEVEL = 0;

    private final Set<Variable> declaredVars = new HashSet<>();

    /**
     * Variables, which values are changing in code.
     */
    private final Set<Variable> usedVars = new HashSet<>();
    
    private final Set<Variable> changedVars = new HashSet<>();

    @Override
    public void mangle(JSONObject code) {
        JSONArray scriptBody = (JSONArray) code.get("body");
        for (Object statementObj : scriptBody) {
            JSONObject statement = (JSONObject) statementObj;
            switch (statement.get("type").toString()) {
                case "FunctionDeclaration":
                    findVarsInFunctionBody(statement, BASE_FUNCTION_LEVEL + 1);
                    break;

                case "VariableDeclaration":
                    findVarsInDeclaration(statement, BASE_FUNCTION_LEVEL);
                    break;

                case "ExpressionStatement":
                    break;
            }
        }
    }

    private void findVarsInFunctionBody(JSONObject functionBody, int levelOfFunc) {

    }

    private void findVarsInDeclaration(JSONObject statement, int levelOfFunc) {
        JSONArray declarations = (JSONArray) statement.get("declarations");
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> it = declarations.iterator();
        while (it.hasNext()) {
            JSONObject variableDeclaration = it.next();
            String variableName = (String) ((Map) variableDeclaration.get("id")).get("name");
            declaredVars.add(new Variable(levelOfFunc, variableName));

            JSONObject init = (JSONObject) variableDeclaration.get("init");
            if (init != null) {
                String initType = (String) init.get("type");
                if (initType.equals("Identifier")) {
                    usedVars.add(new Variable(levelOfFunc, (String) init.get("name")));
                }
                if (initType.endsWith("Expression")) {
                    findVarsInExpression(init, levelOfFunc);
                }
            }
        }
    }

    private void findVarsInExpression(JSONObject expression, int levelOfFunc) {
        String type = (String) expression.get("type");
        switch (type) {
            case "UnaryExpression":
            case "UpdateExpression":
                JSONObject argument = (JSONObject) expression.get("argument");
                if (argument.get("type").equals("Identifier")) {
                    usedVars.add(new Variable(levelOfFunc, (String) argument.get("name")));
                }
                break;

            case "BinaryExpression":
                JSONObject left = (JSONObject) expression.get("left");
                if (left.get("type").equals("Identifier")) {
                    usedVars.add(new Variable(levelOfFunc, (String) left.get("name")));
                } else if (left.get("type").toString().endsWith("Expression")) {
                    findVarsInExpression(left, levelOfFunc);
                }
                
                JSONObject right = (JSONObject) expression.get("right");
                if (right.get("type").equals("Identifier")) {
                    usedVars.add(new Variable(levelOfFunc, (String) right.get("name")));
                } else if (right.get("type").toString().endsWith("Expression")) {
                    findVarsInExpression(right, levelOfFunc);
                }

        }
    }

}
