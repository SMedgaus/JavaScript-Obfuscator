/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree;

import estree.statements.Statement;
import java.util.Collection;

/**
 *
 * @author Sergey
 */
public class Program extends Node {

    private String sourceType;
    
    private Collection<Statement> body;
    
}
