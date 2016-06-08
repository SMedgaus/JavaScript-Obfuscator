/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import abbot.tester.Robot;
import java.util.concurrent.TimeUnit;
import javax.swing.JCheckBox;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Sergey
 */
public class MainFormTest {
    
    private MainForm mainForm;
    
    private Robot r;
    
    @Before
    public void initialize() {
        mainForm = new MainForm();
        mainForm.setVisible(true);
        r = new Robot();
        r.focus(mainForm.sourceCodeTextArea);
        r.keyString("var a = \"Hello, World!\";");
    }
    
    @Test
    public void testMainForm() throws InterruptedException {
        
        //TEST #1
        r.click(mainForm.conditionManglingChBox);
        assertEquals(true, mainForm.conditionManglingChBox.isSelected());
        
        //TEST #2
        r.click(mainForm.chooseAllOptionsChBox);
        assertEquals(true, mainForm.chooseAllOptionsChBox.isSelected());
        for (JCheckBox optioningCheckBox : mainForm.optioningCheckBoxes) {
            assertEquals(true, optioningCheckBox.isSelected());
        }
        
        //TEST #3
        r.click(mainForm.chooseAllOptionsChBox);
        assertEquals(false, mainForm.chooseAllOptionsChBox.isSelected());
        for (JCheckBox optioningCheckBox : mainForm.optioningCheckBoxes) {
            assertEquals(false, optioningCheckBox.isSelected());
        }
        assertEquals(false, mainForm.obfuscateBtn.isEnabled());

        r.click(mainForm.chooseAllOptionsChBox);

        //TEST #4
        r.click(mainForm.obfuscateBtn);
        TimeUnit.MILLISECONDS.sleep(500);
        assertEquals(false, mainForm.obfuscationProgressBar.isVisible());
        TimeUnit.SECONDS.sleep(2);
        
        
        //TEST #5
        r.click(mainForm.obfuscateBtn);
        TimeUnit.SECONDS.sleep(5);
        assertEquals(false, mainForm.mangledCodeTextArea.getText().isEmpty());
    }

    
}
