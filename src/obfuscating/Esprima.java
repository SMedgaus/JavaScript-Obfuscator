/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.JSObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Sergey
 */
public class Esprima {

    private static ScriptEngine nashornEngine;

    private static JSObject esprima;

    private static JSObject escodegen;

    private static JSObject JSON;
    
    private static boolean initialized = false;

    /**
     * Creates Esprima parser and code generator.
     * @throws FileNotFoundException - if third-party libraries are not found.
     * @throws ScriptException - source code of libraries is suspicious.
     */
    public Esprima() throws FileNotFoundException, ScriptException {
        if (!initialized) {
            nashornEngine = new ScriptEngineManager().getEngineByName("nashorn");
            
            nashornEngine.eval(new FileReader("lib/esprima.js"));
            nashornEngine.eval(new FileReader("lib/escodegen.js"));
            
            esprima = (JSObject) nashornEngine.get("esprima");
            escodegen = (JSObject) nashornEngine.get("escodegen");
            JSON = (JSObject) nashornEngine.get("JSON");
            
            initialized = true;
        }
    }

    public JSONObject parseCode(String code) throws ScriptException {
        try {
            Invocable inv = (Invocable) nashornEngine;
            Object parsedCode = inv.invokeMethod(esprima, "parse", code);
            String JSONTree = (String) inv.invokeMethod(JSON, "stringify", parsedCode);
            JSONObject JSONTreeObject = (JSONObject) new JSONParser().parse(JSONTree);
            return JSONTreeObject;
        } catch (NoSuchMethodException | ParseException ex) {
            Logger.getLogger(Esprima.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String generateCode(JSONObject program) throws ScriptException {
        try {
            Invocable inv = (Invocable) nashornEngine;
            Object generatingOptions = inv.invokeMethod(JSON, "parse",
                    "{\"format\":{ \"indent\":{ \"style\": \"\", \"base\": 0}, \"quotes\": \"auto\", \"compact\": true}}");
            Object minifiedCode = inv.invokeMethod(escodegen, "generate", program.toJSONString(), generatingOptions);
            return minifiedCode.toString();
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Esprima.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
