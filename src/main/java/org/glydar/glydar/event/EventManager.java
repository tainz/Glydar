package org.glydar.glydar.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.plugin.Plugin;

/**
 * @author The Glydar Team, YoshiGenius (For the base)
 */

public class EventManager {
    
    private EventManager(){}
    // TODO: Replace String references to plugins with a Plugin object
    private static HashMap<Listener, String> pluginListeners = new HashMap<Listener, String>();
    private static HashMap<Class<? extends Event>, HashMap<Method, Listener>> eventMethods = new HashMap<Class<? extends Event>, HashMap<Method, Listener>>();
    private static HashMap<Method, Boolean> ignoreCancelled = new HashMap<Method, Boolean>();
    
    public static HashMap<Method, Listener> getMethods(Class<? extends Event> evt) {
        if (evt == null) {
            return new HashMap<Method, Listener>();
        }
        if (eventMethods.get(evt) == null) {
        	//Glydar.getServer().getLogger().info("Not found!");
        	eventMethods.put(evt, new HashMap<Method, Listener>());
            return eventMethods.get(evt);
        }
        //Glydar.getServer().getLogger().info("Found!");
        return eventMethods.get(evt);
    }
    
    public static String getPlugin(Listener listener) {
        if (listener == null) {
            return null;
        }
        if (pluginListeners.get(listener) == null) {
            return null;
        }
        return pluginListeners.get(listener);
    }
    
    public static boolean registerListener(Plugin plugin, Listener listener) {
        if (plugin == null || listener == null || pluginListeners.containsKey(listener)) {
            return false;
        }
        for (Method m : listener.getClass().getMethods()) {
            if (m.getParameterTypes().length == 0) {
                continue;
            }
            for (Annotation an : m.getAnnotations()) {
                if (an.annotationType() == EventHandler.class) {
                    Class par1 = m.getParameterTypes()[0];
                    if (Event.class.isAssignableFrom(par1)) {
                        Class<? extends Event> ec = (Class<? extends Event>) par1;
                        HashMap<Method, Listener> methods = getMethods(ec);
                        methods.put(m, listener);
                        EventHandler eh = (EventHandler) an;
                        ignoreCancelled.put(m, eh.ignoreCancelled());
                        eventMethods.put(ec, methods);
                        //Glydar.getServer().getLogger().info("Method: " + m.getName());
                    }
                }
            }
        }
        pluginListeners.put(listener, plugin.getName());
        return true;
    }
    
    @SuppressWarnings("CallToThreadDumpStack")
    public static Event callEvent(Event evt) {
        if (evt == null) {
            return null;
        }
        for (Class<? extends Event> ec : eventMethods.keySet()) {
        	if (!evt.getClass().getName().equals(ec.getName())){
        		continue;
        	}
            for (Method m : eventMethods.get(ec).keySet()) {
                if (m == null) {
                    continue;
                }
                if (evt instanceof Cancellable) {
                    Cancellable cancellable = (Cancellable) evt;
                    try {
                        if (!(cancellable.isCancelled() && ignoreCancelled.get(m))) {
                            m.invoke(eventMethods.get(ec).get(m), evt);
                            //Glydar.getServer().getLogger().info("Method:" + m.getName());
                        }
                    } catch (Exception ex) {
                    	//Glydar.getServer().getLogger().info("ERROR: Method:" + m.getName());
                        ex.printStackTrace();
                    }
                } else {
                    try {
                       m.invoke(eventMethods.get(ec).get(m), evt);
                       //Glydar.getServer().getLogger().info("Method:" + m.getName());
                    } catch (Exception ex) {
                    	//Glydar.getServer().getLogger().info("ERROR: Method:" + m.getName());
                        ex.printStackTrace();
                    }
                }
            }
        }

        return evt;
    }

}