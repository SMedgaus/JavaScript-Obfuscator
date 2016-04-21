/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

/**
 *
 * @author Sergey
 */
@FunctionalInterface
public interface Mangler {

    public void mangle(String code);
    
}
