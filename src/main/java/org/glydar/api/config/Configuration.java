package org.glydar.glydar.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author YoshiGenius
 */
public class Configuration {
    
    public static Configuration loadConfiguration(File file) {
        Configuration c = new Configuration();
        if (!c.load(file)) {
            return null;
        }
        return c;
    }
    
    private HashMap<String, String> keys = new HashMap<String, String>();

    public boolean load(File file) {
        if (file == null || !file.getName().endsWith(".cfg")) {
            return false;
        } else {
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String line = scanner.next();
                    if (line == null || line.equalsIgnoreCase("")) {
                        continue;
                    }
                    String[] values = line.split("=", 2);
                    keys.put(values[0].trim(), values[1].trim());
                }
                scanner.close();
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
    
    public String getString(String key) {
        return keys.get(key.trim()).trim();
    }
    
    public boolean contains(String key) {
        return keys.containsKey(key.trim());
    }
    
    public Configuration set(String key, String value) {
        if (key == null || value == null || key.equals("") || value.equals("")) {
            return this;
        } else {
            keys.put(key.trim(), value.trim());
            return this;
        }
    }
    
    public Configuration save(File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
            for (String key : keys.keySet()) {
                String value = keys.get(key);
                BufferedWriter output = new BufferedWriter(new FileWriter(file));
                output.write(key + "=" + value);
                output.close();
            }
        } catch (Exception e) {
            return this;
        }
        return this;
    }

}