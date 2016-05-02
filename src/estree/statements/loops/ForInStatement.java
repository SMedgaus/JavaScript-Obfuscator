/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.statements.loops;

import estree.expressions.Expression;
import estree.declarations.VariableDeclaration;
import estree.statements.Statement;

/**
 *
 * @author Sergey
 */
public class ForInStatement extends Statement {
    
    private VariableDeclaration left;
    
    private Expression right;
    
    private Statement body;
    
}
