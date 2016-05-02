/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;
import javax.script.Invocable;
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
    
    private final JSObject JSON;

    public Obfuscator() throws FileNotFoundException, ScriptException {
        this.nashornEngine = new ScriptEngineManager().getEngineByName("nashorn");

        nashornEngine.eval(new FileReader("libs/esprima.js"));
        nashornEngine.eval(new FileReader("libs/escodegen.js"));
        nashornEngine.eval(new FileReader("libs/esmangle.js"));
        
        this.esprima = (JSObject) nashornEngine.get("esprima");
        this.escodegen = (JSObject) nashornEngine.get("escodegen");
        this.esmangle = (JSObject) nashornEngine.get("esmangle");
        this.JSON = (JSObject) nashornEngine.get("JSON");
    }
    
    /**
     * Obfuscate code with the given properties.
     * @param code
     * @param obfuscatingOptions
     * @return 
     * @throws javax.script.ScriptException 
     * @throws java.lang.NoSuchMethodException 
     */
    public String obfuscateCode(String code, Properties obfuscatingOptions) 
            throws ScriptException, NoSuchMethodException {
        Invocable inv = (Invocable) nashornEngine;
        
        Object tree = inv.invokeMethod(esprima, "parse", code);
        
        Object mangledTree = inv.invokeMethod(esmangle, "mangle", tree);
        Object generatingOptions = inv.invokeMethod(JSON, "parse", 
                "{\"format\":{ \"indent\":{ \"style\": \"\", \"base\": 0}, \"quotes\": \"auto\", \"compact\": true}}");
        Object minifiedCode = inv.invokeMethod(escodegen, "generate", mangledTree, generatingOptions);
        return (String) minifiedCode;
    }
    
}
