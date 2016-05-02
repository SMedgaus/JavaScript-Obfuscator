/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import org.json.simple.JSONObject;
import traversing.JSONWalker;
import traversing.TraversingOption;

/**
 *
 * @author Sergey
 */
public class TernaryTransformer implements Mangler {

    @Override
    public void mangle(JSONObject code) {
        
        JSONWalker walker = new JSONWalker();
        walker.walk(code, (JSONObject object) -> {
            if (object.get("type").equals("IfStatement")) {
                System.out.println(object.toJSONString());
            }
            return TraversingOption.CONTINUE;
        });
        
    }
    
}
