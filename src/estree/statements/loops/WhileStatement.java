/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.statements.loops;

import estree.expressions.Expression;
import estree.statements.Statement;

/**
 *
 * @author Sergey
 */
public class WhileStatement extends Statement {
    
    private Expression test;
    
    private Statement body;
}
