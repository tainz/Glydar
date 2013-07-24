package org.glydar.glydar.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author YoshiGenius
 */
public class EventManager {
    
    private EventManager(){}
    // TODO: Replace String references to plugins with a Plugin object
    private static HashMap<Listener, String> pluginListeners = new HashMap<Listener, String>();
    private static HashMap<Class<? extends Event>, List<Listener>> eventListeners = new HashMap<Class<? extends Event>, List<Listener>>();
    private static HashMap<Listener, List<Method>> methodListeners = new HashMap<Listener, List<Method>>();
    private static HashMap<Method, Boolean> ignoreCancelled = new HashMap<Method, Boolean>();
    
    public static List<Listener> getListeners(Class<? extends Event> evt) {
        if (evt == null) {
            return new ArrayList<Listener>();
        }
        if (eventListeners.get(evt) == null) {
            return eventListeners.put(evt, new ArrayList<Listener>());
        }
        return eventListeners.get(evt);
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
    
    public static boolean registerListener(String plugin, Listener listener) {
        if (plugin == null || listener == null || pluginListeners.containsKey(listener)) {
            return false;
        }
        List<Method> methods = new ArrayList<Method>();
        for (Method m : listener.getClass().getMethods()) {
            if (m.getParameterTypes().length == 0) {
                continue;
            }
            for (Annotation an : m.getAnnotations()) {
                if (an.annotationType() == EventHandler.class) {
                    Class par1 = m.getParameterTypes()[0];
                    if (par1.isAssignableFrom(Event.class)) {
                        Class<? extends Event> ec = (Class<? extends Event>) par1;
                        List<Listener> listeners = getListeners(ec);
                        listeners.add(listener);
                        EventHandler eh = (EventHandler) an;
                        methods.add(m);
                        ignoreCancelled.put(m, eh.ignoreCancelled());
                        eventListeners.put(ec, listeners);
                    }
                }
            }
        }
        methodListeners.put(listener, methods);
        pluginListeners.put(listener, plugin);
        return true;
    }
    
    @SuppressWarnings("CallToThreadDumpStack")
    public static void callEvent(Event evt) {
        if (evt == null) {
            return;
        }
        for (Class<? extends Event> ec : eventListeners.keySet()) {
            List<Listener> listeners = getListeners(ec);
            for (Listener listener : listeners) {
                for (Method m : methodListeners.get(listener)) {
                    if (m == null) {
                        continue;
                    }
                    if (evt instanceof Cancellable) {
                        Cancellable cancellable = (Cancellable) evt;
                        try {
                            if (!(cancellable.isCancelled() && ignoreCancelled.get(m))) {
                                m.invoke(listener, evt);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            m.invoke(listener, evt);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}