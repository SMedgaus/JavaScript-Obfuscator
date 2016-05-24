/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import org.json.simple.JSONObject;
import traversing.JSONWalker;
import traversing.NodeWorker;
import traversing.TraversingOption;

/**
 *
 * @author Sergey
 */
public class ConditionMangler implements Mangler {

    @Override
    public void mangle(JSONObject code) {
        
        invertComparisons(code);
        
    }

    private void invertComparisons(JSONObject code) {
        JSONWalker.walk(code, new NodeWorker() {

            @Override
            public TraversingOption workWithNode(JSONObject node, Object parent) {

                String type = (String) node.get("type");
                if (type.equals("BinaryExpression")) {

                    String operator = (String) node.get("operator");
                    
                    switch(operator) {
                        case "<":
                            operator = ">=";
                            break;
                        case ">":
                            operator = "<=";
                            break;
                        case "<=":
                            operator = ">";
                            break;
                        case ">=":
                            operator = "<";
                            break;
                        case "==":
                            operator = "!=";
                            break;
                        case "===":
                            operator = "!==";
                            break;
                        default:
                            return TraversingOption.CONTINUE;
                    }
                    
                    JSONObject oldNode = node;

                    JSONObject newNode = new JSONObject();
                    newNode.put("type", "UnaryExpression");
                    newNode.put("operator", "!");
                    newNode.put("prefix", true);

                    JSONWalker.replaceNodeInParent(parent, oldNode, newNode);

                    oldNode.put("operator", operator);

                    newNode.put("argument", oldNode);

                }

                return TraversingOption.CONTINUE;
            }
        });
    }

}
