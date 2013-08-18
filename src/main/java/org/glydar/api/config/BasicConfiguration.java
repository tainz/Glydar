package org.glydar.api.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author YoshiGenius
 */
public class BasicConfiguration extends Configuration {
    
    public static BasicConfiguration loadConfiguration(File file) {
        BasicConfiguration c = new BasicConfiguration();
        if (!c.load(file)) {
            return null;
        }
        return c;
    }
    
    private HashMap<String, String> keys = new HashMap<>();

    @Override
    public boolean load(File file) {
        if (file == null || !file.getName().endsWith(".cfg")) {
            return false;
        } else {
            try {
                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNext()) {
                        String line = scanner.next();
                        if (line == null || line.equalsIgnoreCase("")) {
                            continue;
                        }
                        String[] values = line.split("=", 2);
                        keys.put(values[0].trim(), values[1].trim());
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String getString(String key) {
        return keys.get(key.trim()).trim();
    }
    
    @Override
    public boolean contains(String key) {
        return keys.containsKey(key.trim());
    }
    
    @Override
    public void set(String key, String value) {
        if (key == null || value == null || key.equals("") || value.equals("")) {
        } else {
            keys.put(key.trim(), value.trim());
        }
    }
    
    @Override
    public void save(File file) {
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
        }
    }

}