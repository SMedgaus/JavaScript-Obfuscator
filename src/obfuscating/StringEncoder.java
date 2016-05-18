/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import traversing.JSONWalker;
import traversing.TraversingOption;

/**
 *
 * @author Sergey
 */
public class StringEncoder implements Mangler {

    private final static Random random = new Random(System.nanoTime());

    private HashMap<String, Integer> allConstants = new HashMap<>();

    private HashMap<String, Integer> replacedConstants = new HashMap<>();

    private int constantCounter = 0;

    private final String functionName = "f" + System.nanoTime();

    @Override
    public void mangle(JSONObject code) {

        splitWords(code);

        extractConstantsToFunction(code);

        representInEncodedFormat(code);

    }

    private void splitWords(JSONObject code) {
        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    String type = (String) node.get("type");
                    String rawData = (String) node.get("raw");
                    if (type.equals("Literal")
                    && (rawData.contains("'") || rawData.contains("\""))) {
                        boolean splitting = random.nextBoolean();
                        if (splitting) {
                            String value = (String) node.get("value");
                            int splittingIndex = random.nextInt(value.length() + 1);
                            String leftValue = value.substring(0, splittingIndex);
                            String rightValue = value.substring(splittingIndex);

                            JSONObject newNode = new JSONObject();
                            newNode.put("type", "BinaryExpression");
                            newNode.put("operator", "+");

                            JSONObject leftNode = new JSONObject();
                            leftNode.put("type", "Literal");
                            leftNode.put("value", leftValue);
                            leftNode.put("raw", "'" + leftValue + "'");

                            JSONObject rightNode = new JSONObject();
                            rightNode.put("type", "Literal");
                            rightNode.put("value", rightValue);
                            rightNode.put("raw", "'" + rightValue + "'");

                            newNode.put("left", leftNode);
                            newNode.put("right", rightNode);

                            JSONWalker.replaceNodeInParent(parent, node, newNode);
                        }
                    }
                    return TraversingOption.CONTINUE;
                });
    }

    private void extractConstantsToFunction(JSONObject code) {
        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    String type = (String) node.get("type");
                    String rawData = (String) node.get("raw");
                    if (type.equals("Literal")
                    && (rawData.contains("'") || rawData.contains("\""))) {
                        String value = (String) node.get("value");
                        allConstants.putIfAbsent(value, constantCounter++);
                    }
                    return TraversingOption.CONTINUE;
                });

        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    String type = (String) node.get("type");
                    String rawData = (String) node.get("raw");
                    if (type.equals("Literal")
                    && (rawData.contains("'") || rawData.contains("\""))) {
                        String value = (String) node.get("value");
                        boolean replacing = random.nextBoolean();
                        if (replacing && allConstants.containsKey(value)) {
                            int number = allConstants.get(value);
                            replacedConstants.put(value, number);

                            JSONObject newNode = new JSONObject();
                            newNode.put("type", "CallExpression");

                            JSONObject callee = new JSONObject();
                            callee.put("type", "Identifier");
                            callee.put("name", functionName);
                            newNode.put("callee", callee);

                            JSONObject argument = new JSONObject();
                            argument.put("type", "Literal");
                            argument.put("value", number);
                            argument.put("raw", "'" + value + "'");

                            JSONArray arguments = new JSONArray();
                            arguments.add(argument);

                            newNode.put("arguments", arguments);

                            JSONWalker.replaceNodeInParent(parent, node, newNode);
                        }
                    }
                    return TraversingOption.CONTINUE;
                });

        final StringBuilder sb = new StringBuilder();
        sb.append("function ").append(functionName).append("(number){");

        allConstants.forEach((String s,
                Integer i) -> {
                    sb.append("if(").append(i).append(")return '").append(s).append("';");
                });

        sb.append("}");

        try {
            JSONObject newFunction = new Esprima().parseCode(sb.toString());
            newFunction = (JSONObject) ((List) newFunction.get("body")).get(0);

            JSONArray bodyArray = (JSONArray) code.get("body");
            if (bodyArray != null) {
                bodyArray.add(newFunction);
            }
        } catch (FileNotFoundException | ScriptException ex) {
            Logger.getLogger(TernaryTransformer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void representInEncodedFormat(JSONObject code) {

        final String BASE64DecoderFunc = "f" + System.nanoTime();

        final StringBuilder stringBuilder = new StringBuilder();

        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    String type = (String) node.get("type");
                    if (type.equals("Literal") && (node.get("value") instanceof String)) {
                        String value = (String) node.get("value");
                        
                        boolean toBASE64 = random.nextBoolean();
                        if (toBASE64) {
                            String encodedValue = null;
                            try {
                                encodedValue = Base64.getEncoder().encodeToString(value.getBytes("UTF-8"));
                            } catch (UnsupportedEncodingException ex) {
                                Logger.getLogger(StringEncoder.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            node.remove("raw");
                            node.remove("value");
                            
                            node.put("type", "CallExpression");

                            JSONObject callee = new JSONObject();
                            callee.put("type", "Identifier");
                            callee.put("name", BASE64DecoderFunc);

                            node.put("callee", callee);

                            JSONArray arguments = new JSONArray();
                            
                            JSONObject argument = new JSONObject();
                            argument.put("type", "Literal");
                            argument.put("raw", "'" + encodedValue + "'");
                            argument.put("value", encodedValue);
                            arguments.add(argument);
                            
                            node.put("arguments", arguments);
                            
                            return TraversingOption.SKIP;
                            
                        } else { //unicode representation
                            stringBuilder.setLength(0);
                            for (int i = 0; i < value.chars().toArray().length; i++) {
                                int valueChar = (char) value.chars().toArray()[i];
                                stringBuilder.append("\\u").append(Integer.toHexString(valueChar));
                            }
                            String newValue = stringBuilder.toString();
                            node.put("raw", "'" + newValue + "'");
                            node.put("value", newValue);
                        }
                    }
                    return TraversingOption.CONTINUE;
                });
    }

}
