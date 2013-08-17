package org.glydar.api.config;

import java.io.File;

/**
 * @author YoshiGenius
 */
public abstract class Configuration {

    public abstract boolean load(File file);
    
    public abstract String getString(String key);
    
    public abstract boolean contains(String key);
    
    public abstract void set(String key, String value);
    
    public abstract void save(File file);

}