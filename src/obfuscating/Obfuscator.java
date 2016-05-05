/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Properties;
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
final public class Obfuscator {

    private final ScriptEngine nashornEngine;

    private final JSObject esprima;

    private final JSObject escodegen;

    private final JSObject JSON;

    public Obfuscator() throws FileNotFoundException, ScriptException {
        this.nashornEngine = new ScriptEngineManager().getEngineByName("nashorn");

        nashornEngine.eval(new FileReader("libs/esprima.js"));
        nashornEngine.eval(new FileReader("libs/escodegen.js"));

        this.esprima = (JSObject) nashornEngine.get("esprima");
        this.escodegen = (JSObject) nashornEngine.get("escodegen");
        this.JSON = (JSObject) nashornEngine.get("JSON");
    }

    /**
     * Obfuscate code with the given options.
     *
     * @param code
     * @param obfuscatingOptions
     * @return
     * @throws javax.script.ScriptException
     * @throws java.lang.NoSuchMethodException
     * @throws org.json.simple.parser.ParseException
     */
    public String obfuscateCode(String code, Properties obfuscatingOptions)
            throws ScriptException, NoSuchMethodException, ParseException {
        Invocable inv = (Invocable) nashornEngine;

        Object tree = inv.invokeMethod(esprima, "parse", code);

        String JSONTree = (String) inv.invokeMethod(JSON, "stringify", tree);
        JSONObject JSONTreeObject = (JSONObject) new JSONParser().parse(JSONTree);

        ArrayList<Mangler> manglers = getManglers(obfuscatingOptions);

        manglers.stream().forEach((mangler) -> {
            mangler.mangle(JSONTreeObject);
        });

        tree = inv.invokeMethod(JSON, "parse", JSONTreeObject.toJSONString());

        Object generatingOptions = inv.invokeMethod(JSON, "parse",
                "{\"format\":{ \"indent\":{ \"style\": \"\", \"base\": 0}, \"quotes\": \"auto\", \"compact\": true}}");
        Object minifiedCode = inv.invokeMethod(escodegen, "generate", tree, generatingOptions);
        return (String) minifiedCode;
    }

    /**
     * Generates list of manglers regarding given obfuscating options.
     * @param obfuscatingOptions
     * @return 
     */
    private ArrayList<Mangler> getManglers(Properties obfuscatingOptions) {
        ArrayList<Mangler> manglers = new ArrayList<>();
        
        if (obfuscatingOptions.getProperty("constantPruner", "false").equals("true")) {
            manglers.add(new ConstantPruner());
        }
        
        if (obfuscatingOptions.getProperty("ternaryTransformer", "false").equals("true")) {
            manglers.add(new TernaryTransformer());
        }
        
        if (obfuscatingOptions.getProperty("renaming", "false").equals("true")) {
            manglers.add(new RenamingMangler());
        }
        
        return manglers;
    }
}
