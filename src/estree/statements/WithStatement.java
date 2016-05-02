/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.statements;

import estree.expressions.Expression;

/**
 *
 * @author Sergey
 */
public class WithStatement extends Statement {
    
    private Expression object;
    
    private Statement body;
}
