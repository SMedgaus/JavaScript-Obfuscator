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
public interface NodeWorker {

    public TraversingOption workWithNode(JSONObject node, Object parent);

}
