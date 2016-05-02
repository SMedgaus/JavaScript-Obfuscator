/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.expressions;

import estree.Node;

/**
 *
 * @author Sergey
 */
public class Property extends Node {
    
    private PropertyKeyInterface key;
    
    private Expression value;
    
    private String kind;
    
}
