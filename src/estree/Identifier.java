/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree;

import estree.expressions.AssignmentLeftInterface;
import estree.expressions.Expression;
import estree.expressions.PropertyKeyInterface;

/**
 *
 * @author Sergey
 */
public class Identifier extends Expression implements PropertyKeyInterface,
        AssignmentLeftInterface {
    
    private String name;
    
}
