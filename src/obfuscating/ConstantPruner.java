/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import traversing.JSONWalker;
import traversing.TraversingOption;

/**
 *
 * @author Sergey
 */
public class ConstantPruner implements Mangler {

    //key - variable name, value - initialzing value
    private final HashMap<String, JSONObject> constVars = new HashMap<>();

    private JSONObject code;

    @Override
    public void mangle(JSONObject code) {

        this.code = code;

        findPossibleConstants();

        exludeChangingVariables();

        exludeNonLiteralConstants();

        replaceConstantUsage();

    }

    private void findPossibleConstants() {
        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    if (node.get("type").equals("VariableDeclarator")) {
                        String varName = (String) ((Map) node.get("id")).get("name");
                        constVars.put(varName, (JSONObject) node.get("init"));
                    }

                    return TraversingOption.CONTINUE;
                });
    }

    private void exludeChangingVariables() {
        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    String nodeType = (String) node.get("type");

                    if (nodeType.equals("UpdateExpression")) {
                        String varName = (String) ((Map) node.get("argument")).get("name");
                        constVars.remove(varName);
                    }

                    if (nodeType.equals("AssignmentExpression")) {
                        String varName = (String) ((Map) node.get("left")).get("name");
                        JSONObject init = (JSONObject) node.get("right");

                        if (constVars.containsKey(varName)) {
                            if (constVars.get(varName) != null) { //variable is already initialized
                                constVars.remove(varName);
                            } else {
                                constVars.put(varName, init);
                            }
                        }
                    }

                    return TraversingOption.CONTINUE;
                });
    }

    private void exludeNonLiteralConstants() {
        constVars.entrySet().removeIf((Map.Entry<String, JSONObject> t)
                -> t.getValue() == null);
        
        constVars.entrySet().removeIf((Map.Entry<String, JSONObject> t)
                -> !t.getValue().get("type").equals("Literal"));
    }

    private void replaceConstantUsage() {
        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    String nodeType = (String) node.get("type");

                    if (nodeType.equals("VariableDeclaration")) {
                        JSONArray declarations = (JSONArray) node.get("declarations");
                        for (Iterator it = declarations.iterator(); it.hasNext();) {
                            JSONObject d = (JSONObject) it.next();
                            String variableName = (String) ((Map) d.get("id")).get("name");
                            if (constVars.containsKey(variableName)) {
                                it.remove();
                            }
                        }

                        if (declarations.isEmpty()) {
                            node.remove("kind");
                            node.remove("declarations");
                            node.put("type", "EmptyStatement");
                        }
                    }

                    return TraversingOption.CONTINUE;
                });
        
        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    String nodeType = (String) node.get("type");

                    if (nodeType.equals("Identifier")) {
                        String varName = (String) node.get("name");
                        if (constVars.containsKey(varName)) {
                            JSONWalker.replaceNodeInParent(parent, node, constVars.get(varName));
                        }
                    }

                    return TraversingOption.CONTINUE;
                });

    }
}
