/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.declarations;

import estree.FunctionInterface;
import estree.Identifier;
import estree.statements.BlockStatement;
import java.util.Collection;

/**
 *
 * @author Sergey
 */
public class FunctionDeclaration extends Declaration implements FunctionInterface {
    
    private Identifier id;
    
    private Collection<Identifier> params;
    
    private BlockStatement body;
}
