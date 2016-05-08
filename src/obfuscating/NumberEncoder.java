/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.util.Random;
import org.json.simple.JSONObject;
import traversing.JSONWalker;
import traversing.TraversingOption;

/**
 *
 * @author Sergey
 */
public class NumberEncoder implements Mangler {

    private final Random random = new Random(System.nanoTime());

    @Override
    public void mangle(JSONObject code) {

        divideFloatNumbers(code);

        transformIntegers(code);
        
    }

    private void divideFloatNumbers(JSONObject code) {
        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    String nodeType = (String) node.get("type");
                    if (nodeType.equals("Literal")) {
                        Object valueObj = node.get("value");
                        if (valueObj instanceof Number) {
                            Number value = (Number) valueObj;
                            double remainder = value.doubleValue() % 1;
                            if (remainder > 0 && value.longValue() > 0) {
                                JSONObject leftOperand = new JSONObject();
                                leftOperand.put("type", "Literal");
                                leftOperand.put("value", remainder);
                                leftOperand.put("raw", String.valueOf(remainder));

                                JSONObject rightOperand = new JSONObject();
                                rightOperand.put("type", "Literal");
                                rightOperand.put("value", value.longValue());
                                rightOperand.put("raw", String.valueOf(value.longValue()));

                                JSONObject binaryOperation = new JSONObject();
                                binaryOperation.put("type", "BinaryExpression");
                                binaryOperation.put("operator", "+");
                                binaryOperation.put("left", leftOperand);
                                binaryOperation.put("right", rightOperand);

                                JSONWalker.replaceNodeInParent(parent, node, binaryOperation);

                                //skipping not to make recursion, because new 
                                //nodes are added to current node
                                return TraversingOption.SKIP;
                            }
                        }
                    }
                    return TraversingOption.CONTINUE;
                });
    }

    private void transformIntegers(JSONObject code) {
        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    String nodeType = (String) node.get("type");
                    if (nodeType.equals("Literal")) {
                        Object valueObj = node.get("value");
                        if (valueObj instanceof Number) {
                            Number value = (Number) valueObj;
                            double remainder = value.doubleValue() % 1;
                            if (remainder == 0 && value.longValue() > 0) {
                                node.put("type", "UnaryExpression");
                                node.put("operator", "+");
                                node.put("prefix", true);
                                
                                node.remove("raw");
                                node.remove("value");
                                
                                JSONObject argumentNode = new JSONObject();
                                argumentNode.put("type", "Literal");
                                argumentNode.put("raw", String.valueOf(value.longValue()));
                                argumentNode.put("value", toHexString((int) value.longValue()));
                                
                                node.put("argument", argumentNode);
                            }
                        }
                    }
                    return TraversingOption.CONTINUE;
                });
    }

    private String toHexString(int number) {
        return "0X" + Integer.toHexString(number);
    }
}
