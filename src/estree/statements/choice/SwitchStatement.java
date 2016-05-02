/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.statements.choice;

import estree.expressions.Expression;
import estree.statements.Statement;
import java.util.Collection;

/**
 *
 * @author Sergey
 */
public class SwitchStatement extends Statement {
    
    private Expression discriminant;
    
    private Collection<SwitchCase> cases;
}
