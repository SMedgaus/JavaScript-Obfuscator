/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traversing;

import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Sergey
 */
public abstract class JSONWalker {

    public static void walk(JSONObject node, NodeWorker worker) {
        if (node != null) {
            walk(node, null, worker);
        }
    }

    private static TraversingOption walk(JSONObject node, Object parent, NodeWorker worker) {
        TraversingOption workResult = worker.workWithNode(node, parent);

        if (workResult == TraversingOption.CONTINUE) {
            for (Object nodeValue : node.values()) {
                if (nodeValue != null) {

                    if (nodeValue instanceof JSONObject) {
                        if (walk((JSONObject) nodeValue, node, worker)
                                == TraversingOption.BREAK) {
                            return TraversingOption.BREAK;
                        }
                    }

                    if (nodeValue instanceof JSONArray) {
                        for (Object arrayElement : (Iterable<? extends Object>) nodeValue) {
                            if (arrayElement != null) {
                                if (walk((JSONObject) arrayElement, nodeValue, worker)
                                        == TraversingOption.BREAK) {
                                    return TraversingOption.BREAK;
                                }
                            }
                        }
                    }
                }
            }
        }

        return workResult;
    }

    /**
     * @param parent can be JSONObject or JSONArray
     * @param oldNode
     * @param newNode
     */
    public static void replaceNodeInParent(Object parent, JSONObject oldNode, JSONObject newNode) {
        if (parent != null) {
            if (parent instanceof JSONObject) {
                for (Object objEntry : ((Map) parent).entrySet()) {
                    Map.Entry entry = (Map.Entry) objEntry;
                    if (entry.getValue().equals(oldNode)) {
                        entry.setValue(newNode);
                        return;
                    }
                }
            } else {
                JSONArray parentArray = (JSONArray) parent;
                for (int i = 0; i < parentArray.size(); i++) {
                    JSONObject child = (JSONObject) parentArray.get(i);
                    if (child == oldNode) {
                        parentArray.set(i, newNode);
                        return;
                    }
                }
            }
        }
    }
}
