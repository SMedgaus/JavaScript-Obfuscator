/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estree.expressions;

/**
 *
 * @author Sergey
 */
public class UpdateExpression extends Expression {
    
    private String operator;
    
    private Expression argument;
    
    private boolean prefix;
}