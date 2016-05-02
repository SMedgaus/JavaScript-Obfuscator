/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.declarations;

import estree.expressions.Expression;
import estree.Identifier;
import estree.Node;

/**
 *
 * @author Sergey
 */
public class VariableDeclarator extends Node {
    
    private Identifier id;
    
    private Expression init;
}
