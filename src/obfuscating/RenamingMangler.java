/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscating;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import traversing.JSONWalker;
import traversing.TraversingOption;

/**
 *
 * @author Sergey
 */
public class RenamingMangler implements Mangler {

    // key - original name, value - mangled name
    private final HashMap<String, String> names = new HashMap<>();

    @Override
    public void mangle(JSONObject code) {

        //recording all variable and function declarations
        JSONWalker.walk(code, (JSONObject node,
                Object parentObj) -> {
                    if (node != null && node.get("type").equals("Identifier")) {
                        try {
                            boolean isArray = parentObj instanceof JSONArray;
                            if (isArray || ((JSONObject) parentObj).get("type").equals("VariableDeclarator")
                            || ((JSONObject) parentObj).get("type").equals("FunctionDeclaration")) {
                                String identifierValue = (String) node.get("name");
                                if (!names.containsKey(identifierValue)) {
                                    String hash = getMD5String(identifierValue.getBytes());
                                    String newValue = mangleHash(hash);
                                    names.put(identifierValue, newValue);
                                }
                            }
                        } catch (ClassCastException e) {
                            e.printStackTrace();
                        }

                    }
                    return TraversingOption.CONTINUE;
                });

        //replacing variable and function names
        JSONWalker.walk(code, (JSONObject node,
                Object parent) -> {
                    if (node != null && node.get("type").equals("Identifier")) {
                        String identifierValue = (String) node.get("name");
                        if (names.containsKey(identifierValue)) {
                            node.put("name", names.get(identifierValue));
                        }
                    }
                    return TraversingOption.CONTINUE;
                });
    }

    /**
     * Hash data with MD5 algorithm.
     *
     * @param data - array of bytes to be hashed
     * @return hash of bytes (String representation)
     */
    public static String getMD5String(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(data);
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RenamingMangler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Converts hash string to another symbols difficult for understanding.
     *
     * @param hash
     * @return
     */
    public static String mangleHash(String hash) {
        StringBuilder result = new StringBuilder();
        hash = hash.substring(0, hash.length() / 2);
        final int START_LETTER = 0x0540; //armenian letter 
        for (int c : hash.chars().toArray()) {
            result.append((char) (START_LETTER + c % 16));
        }
        return result.toString();
    }

}
