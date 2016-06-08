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
public class RenamingManglerTest {
    
    public RenamingManglerTest() {
    }

    @Test
    public void testGetMD5String() {
        byte[] array = {3, 65, 21};
        Assert.assertEquals("be6bd078dca8b2a63454b7631525d869", 
                RenamingMangler.getMD5String(array));
        
        try {
            RenamingMangler.getMD5String(null);
        } catch (Exception e) {
            Assert.assertEquals(true, e instanceof NullPointerException);
        }
    }
    
    @Test
    public void testMangleHash() {
        String hash = "322AC";
        Assert.assertEquals("ՃՂ", RenamingMangler.mangleHash(hash));
    }
    
}
