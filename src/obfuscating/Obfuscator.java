/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.JSObject;

/**
 *
 * @author Sergey
 */
final public class Obfuscator {

    private final ScriptEngine nashornEngine;

    private final JSObject esprima;

    private final JSObject escodegen;

    private final JSObject esmangle;

    public Obfuscator() throws FileNotFoundException, ScriptException {
        this.nashornEngine = new ScriptEngineManager().getEngineByName("nashorn");
        this.esprima = (JSObject) nashornEngine.eval(new FileReader("libs/esprima.js"));
        this.escodegen = (JSObject) nashornEngine.eval(new FileReader("libs/escodegen.js"));
        this.esmangle = null;
    }
    
    /**
     * Obfuscate code with the given properties.
     * @param code
     * @param obfuscatingOptions
     * @return 
     */
    public String obfuscateCode(String code, Properties obfuscatingOptions) {
        throw new UnsupportedOperationException();
    }

}
