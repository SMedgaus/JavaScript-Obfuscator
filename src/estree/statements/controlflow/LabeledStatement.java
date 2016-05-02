/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.statements.controlflow;

import estree.Identifier;
import estree.statements.Statement;

/**
 *
 * @author Sergey
 */
public class LabeledStatement extends Statement {

    private Identifier label;

    private Statement body;

}
