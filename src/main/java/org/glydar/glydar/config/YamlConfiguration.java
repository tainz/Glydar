package org.glydar.glydar.config;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glydar.api.config.Configuration;
import org.glydar.glydar.Glydar;
import org.yaml.snakeyaml.Yaml;

/**
 * @author YoshiGenius
 */
public class YamlConfiguration extends Configuration {

    public static YamlConfiguration loadConfiguration(File file) {
        YamlConfiguration c = new YamlConfiguration();
        if (!c.load(file)) {
            return null;
        }
        return c;
    }
    
    private HashMap<String, Object> keys = new HashMap<>();
    private Yaml yaml = new Yaml();

    @Override
    public boolean load(File file) {
        if (file == null || !file.getName().endsWith(".yml")) {
            return false;
        } else {
            try {
                Map<?, ?> map = (Map<?,?>) yaml.load(new BufferedInputStream(new FileInputStream(file)));
            }
            catch (FileNotFoundException ex) {
                Glydar.getServer().getLogger().log(Level.SEVERE, "Error loading yaml file!", ex);
            }
        }
        return true;
    }
    
    public Object get(String key) {
        return keys.get(key);
    }
    
    @Override
    public String getString(String key) {
        if (get(key.trim()) instanceof String) {
            return ((String) keys.get(key.trim())).trim();
        }
        return null;
    }
    
    public int getInt(String key) {
        if (get(key.trim()) instanceof Integer) {
            return (int)get(key.trim());
        }
        return 0;
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            // yaml.dumpAll(keys.values().iterator(), writer);
            for (String key : keys.keySet()) {
                String dump = yaml.dump(keys.get(key));
                
            }
        } catch (Exception e) {
        }
    }

}