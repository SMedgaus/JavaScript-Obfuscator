/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traversing;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Sergey
 */
public class JSONWalker {

    public void walk(JSONObject node, NodeWorker worker) {
        if (node != null) {
            walk(node, null, worker);
        }
    }

    private TraversingOption walk(JSONObject node, Object parent, NodeWorker worker) {
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

}
