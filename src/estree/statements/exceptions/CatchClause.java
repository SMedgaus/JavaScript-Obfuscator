/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.statements.exceptions;

import estree.Identifier;
import estree.Node;
import estree.statements.BlockStatement;

/**
 *
 * @author Sergey
 */
public class CatchClause extends Node {
    
    private Identifier param;
    
    private BlockStatement body;
}
