/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.statements.exceptions;

import estree.statements.BlockStatement;
import estree.statements.Statement;



/**
 *
 * @author Sergey
 */
public class TryStatement extends Statement {
    
    private BlockStatement block;
    
    private CatchClause handler;
    
    private BlockStatement finalizer;

}
