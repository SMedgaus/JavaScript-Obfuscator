/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import traversing.JSONWalker;
import traversing.TraversingOption;

/**
 *
 * @author Sergey
 */
public class FunctionShuffler implements Mangler {

    @Override
    public void mangle(JSONObject code) {

        final ArrayList<JSONObject> globalFunctions = new ArrayList<>();
        final ArrayList<JSONObject> notFunctions = new ArrayList<>();

        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    String nodeType = (String) node.get("type");

                    if (nodeType.equals("Program")) {
                        return TraversingOption.CONTINUE;
                    }

                    if (nodeType.equals("FunctionDeclaration")) {
                        globalFunctions.add(node);
                    } else {
                        notFunctions.add(node);
                    }
                    return TraversingOption.SKIP;
                });

        globalFunctions.sort(new Comparator<JSONObject>() {

            final Random r = new Random(System.nanoTime());

            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                return r.nextInt(3) - 1;
            }
        });

        JSONArray newBody = new JSONArray();
        newBody.addAll(globalFunctions);
        newBody.addAll(notFunctions);
        code.put("body", newBody);

    }

}
