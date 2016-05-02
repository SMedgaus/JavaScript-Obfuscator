/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.declarations;

import estree.statements.loops.ForStatementInit;
import java.util.Collection;

/**
 *
 * @author Sergey
 */
public class VariableDeclaration extends Declaration implements ForStatementInit {
    
    private Collection<VariableDeclarator> declarations;
    
    private String kind;
    
}
