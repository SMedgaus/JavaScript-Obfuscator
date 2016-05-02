/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traversing;

import org.json.simple.JSONObject;

/**
 *
 * @author Sergey
 */
public class JSONWalker {
    
    public TraversingOption walk(JSONObject obj, NodeWorker worker) {
        TraversingOption workResult = worker.workWithNode(obj);
        
        if (workResult == TraversingOption.CONTINUE) {
            for(Object objPropery : obj.keySet()) {
                if (objPropery instanceof JSONObject) {
                    if (walk((JSONObject) objPropery, worker) 
                            == TraversingOption.BREAK) {
                        return TraversingOption.BREAK;
                    }
                }
            }
        }
        
        return workResult;
    }
    
}
