/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.expressions;

import estree.Identifier;

/**
 *
 * @author Sergey
 */
public class MemberExpression extends Identifier {
    
    private Expression object;
    
    private Expression property;
    
    private boolean computed;
    
}
