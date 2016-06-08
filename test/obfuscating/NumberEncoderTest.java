/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Sergey
 */
public class NumberEncoderTest {
    
    public NumberEncoderTest() {
    }

    @Test
    public void testToHex() {
        int number = 67;
        Assert.assertEquals("0X43", NumberEncoder.toHexString(number));
    }
    
}
