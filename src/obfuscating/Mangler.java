/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import org.json.simple.JSONObject;

/**
 *
 * @author Sergey
 */
@FunctionalInterface
public interface Mangler {

    public void mangle(JSONObject code);
    
}
