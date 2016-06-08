/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import jdk.nashorn.api.scripting.JSObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Sergey
 */
final public class Obfuscator extends SwingWorker<String, String> {

    private final ScriptEngine nashornEngine;

    private final JSObject esprima;

    private final JSObject escodegen;

    private final JSObject JSON;

    private final String sourceCode;

    private final JLabel progressStatus;

    private final boolean reformatting;

    private final ArrayList<Mangler> manglers = new ArrayList<>();

    public Obfuscator(String sourceCode, Properties obfuscatingProps,
            JLabel progressStatus) throws FileNotFoundException, ScriptException {
        this.nashornEngine = new ScriptEngineManager().getEngineByName("nashorn");

        nashornEngine.eval(new FileReader("lib/esprima.js"));
        nashornEngine.eval(new FileReader("lib/escodegen.js"));

        this.esprima = (JSObject) nashornEngine.get("esprima");
        this.escodegen = (JSObject) nashornEngine.get("escodegen");
        this.JSON = (JSObject) nashornEngine.get("JSON");

        this.sourceCode = sourceCode;
        this.progressStatus = progressStatus;

        Invocable inv = (Invocable) nashornEngine;
        try {
            inv.invokeMethod(esprima, "parse", sourceCode);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Obfuscator.class.getName()).log(Level.SEVERE, null, ex);
        }

        reformatting = Boolean.parseBoolean(
                (String) obfuscatingProps.getOrDefault("reformatting", "false"));
        manglers.addAll(getManglers(obfuscatingProps));
    }

    /**
     * Generates list of manglers regarding given obfuscating options.
     *
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

        if (obfuscatingOptions.getProperty("conditionMangling", "false").equals("true")) {
            manglers.add(new ConditionMangler());
        }

        if (obfuscatingOptions.getProperty("numberEncoding", "false").equals("true")) {
            manglers.add(new NumberEncoder());
        }

        if (obfuscatingOptions.getProperty("stringEncoding", "false").equals("true")) {
            manglers.add(new StringEncoder());
        }

        if (obfuscatingOptions.getProperty("renaming", "false").equals("true")) {
            manglers.add(new RenamingMangler());
        }

        return manglers;
    }

    @Override
    protected String doInBackground() throws Exception {
        final int numOfStages = manglers.size() + 2;

        setProgress(0);
        publish("Подключение Nashorn");

        Invocable inv = (Invocable) nashornEngine;

        Object tree = inv.invokeMethod(esprima, "parse", sourceCode);

        String JSONTree = (String) inv.invokeMethod(JSON, "stringify", tree);
        JSONObject JSONTreeObject = (JSONObject) new JSONParser().parse(JSONTree);

        for (int i = 0; i < manglers.size(); i++) {
            failIfInterrupted();
            
            Mangler m = manglers.get(i);
            String progressMessage = "";

            if (m instanceof ConstantPruner) {
                progressMessage = "Сокращение констант";
            }

            if (m instanceof TernaryTransformer) {
                progressMessage = "Преобразование if-else";
            }

            if (m instanceof NumberEncoder) {
                progressMessage = "Кодирование чисел";
            }

            if (m instanceof StringEncoder) {
                progressMessage = "Кодирование строк";
            }

            if (m instanceof RenamingMangler) {
                progressMessage = "Переименование";
            }

            if (m instanceof ConditionMangler) {
                progressMessage = "Логическое преобразование";
            }

            setProgress(Math.round((float) (i + 1) / numOfStages * 100));
            publish(progressMessage);
            m.mangle(JSONTreeObject);
        }

        setProgress(Math.round((float) (numOfStages - 1) / numOfStages * 100));
        publish("Построение кода");

        new FunctionShuffler().mangle(JSONTreeObject);

        tree = inv.invokeMethod(JSON, "parse", JSONTreeObject.toJSONString());

        Object generatingOptions = inv.invokeMethod(JSON, "parse",
                "{\"format\":{ \"indent\":{ \"style\":"
                + ((reformatting) ? "\"\"" : "\"  \"")
                + ", \"base\": 0}, \"quotes\": \"auto\", "
                + "\"compact\": " + reformatting + "}}");
        Object minifiedCode = inv.invokeMethod(escodegen, "generate", tree, generatingOptions);

        setProgress(100);

        return (String) minifiedCode;
    }

    @Override
    protected void process(List<String> chunks) {
        progressStatus.setVisible(true);
        progressStatus.setText(chunks.get(chunks.size() - 1) + "...");
    }

    private static void failIfInterrupted() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Interrupted while obfuscating!");
        }
    }
}
