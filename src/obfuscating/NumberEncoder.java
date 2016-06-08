/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import org.json.simple.JSONObject;
import traversing.JSONWalker;
import traversing.TraversingOption;

/**
 *
 * @author Sergey
 */
public class NumberEncoder implements Mangler {

    private static final int MAX_EXPRESSION_LENGTH = 5;

    private static final long MIDDLE_NUMBER = 500;

    private final Random random = new Random(System.nanoTime());

    @Override
    public void mangle(JSONObject code) {

        divideFloatNumbers(code);
        
        representIntegersAsExpressions(code);

        convertIntegersToHEX(code);

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

    private void convertIntegersToHEX(JSONObject code) {
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

    public static String toHexString(int number) {
        return "0X" + Integer.toHexString(number);
    }

    private void representIntegersAsExpressions(JSONObject code) {
        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    String nodeType = (String) node.get("type");

                    if (nodeType.equals("Literal")) {
                        Object value = node.get("value");
                        if (value instanceof Number
                        && ((Number) value).doubleValue() % 1 == 0) {
                            final long val = ((Number) value).longValue();

                            int expressionsNum = random.nextInt(MAX_EXPRESSION_LENGTH) + 1;

                            String expression = expandIntegerToExpression(val, expressionsNum);

                            try {
                                JSONObject newNode = new Esprima().parseCode(expression);
                                newNode = (JSONObject) ((List) newNode.get("body")).get(0);
                                newNode = (JSONObject) newNode.get("expression");

                                JSONWalker.replaceNodeInParent(parent, node, newNode);
                            } catch (FileNotFoundException | ScriptException ex) {
                                Logger.getLogger(NumberEncoder.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            return TraversingOption.SKIP;
                        }
                    }
                    return TraversingOption.CONTINUE;
                });
    }

    private String expandIntegerToExpression(long longValue, int numOfOperators) {
        Expression exp = new Expression(longValue);
        for (int j = 0; j < numOfOperators; j++) {
            exp.addOperator();
        }
        return exp.getStringExpression();
    }

    private class Expression {

        private static final int OPERATORS_NUM = 2;

        private LinkedList<String> members = new LinkedList<>();

        private long lastNumber;

        public Expression(long value) {
            this.lastNumber = value;
            members.add(Long.toString(value));
        }

        public void addOperator() {
            int bracesCounter = 0;
            while (members.peekLast().equals(")")) {
                members.pollLast();
                bracesCounter++;
            }
            members.pollLast();
            members.add("(");

            String operator = getOperator(random.nextInt(OPERATORS_NUM), lastNumber < MIDDLE_NUMBER);
            int randNumber = (random.nextInt((int) MIDDLE_NUMBER) + 1);
            long newValue = getNewValue(lastNumber, randNumber, operator);
            members.add(Long.toString(newValue));
            members.add(operator);
            members.add(Integer.toString(randNumber));

            members.add(")");
            for (int i = 0; i < bracesCounter; i++) {
                members.add(")");
            }
            lastNumber = randNumber;
        }

        public String getStringExpression() {
            StringBuilder sb = new StringBuilder();
            for (String member : members) {
                sb.append(member);
            }
            return sb.toString();
        }

        private String getOperator(int operatorNum, boolean reducing) {
            if (reducing) {
                switch (operatorNum) {
                    case 0:
                        return "/";
                    case 1:
                        return "-";
                }
            } else {
                switch (operatorNum) {
                    case 0:
                        return "*";
                    case 1:
                        return "+";
                }
            }
            return null;
        }

        private long getNewValue(long value, long newValue, String operator) {
            switch (operator) {
                case "/":
                    return value * newValue;
                case "-":
                    return value + newValue;

                case "*":
                    return value / newValue;
                case "+":
                    return value - newValue;
            }
            return Long.MIN_VALUE;
        }
    }
}
