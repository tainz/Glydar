package org.glydar.glydar.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.plugin.Plugin;

public class EventManager {
    
    public EventManager(){}
    private static List<StoredListener> storedListeners = new ArrayList<StoredListener>();
    
    public static boolean registerListener(Plugin plugin, Listener listener) {
        if (plugin == null || listener == null) {
            return false;
        }
        StoredListener sl = new StoredListener(plugin, listener);
        for (Method m : listener.getClass().getMethods()) {
            if (m.getParameterTypes().length != 1) {
                continue;
            }
            for (Annotation an : m.getAnnotations()) {
                if (an.annotationType() == EventHandler.class) {
                    Class par1 = m.getParameterTypes()[0];
                    if (Event.class.isAssignableFrom(par1)) {
                    	EventHandler eh = (EventHandler) an;
                        sl.addMethod(par1.getName(), m, eh.priority(), eh.ignoreCancelled());
                        Glydar.getServer().getLogger().info("Method: " + m.getName());
                    }
                    break;
                }
            }
        }
        storedListeners.add(sl);
        return true;
    }
    
    public static void unregisterListener(Listener listener){
    	for (StoredListener sl : storedListeners){
    		if (sl.getListener().equals(listener)){
    			storedListeners.remove(sl);
    		}
    	}
    }
    
    public static void unregisterListeners(Plugin plugin){
    	for (StoredListener sl : storedListeners){
    		if (sl.getPlugin().equals(plugin)){
    			storedListeners.remove(sl);
    		}
    	}
    }
    
    @SuppressWarnings("CallToThreadDumpStack")
    public static Event callEvent(Event evt) {
    	HashMap<Method, StoredListener> methods = new HashMap<Method, StoredListener>();
        if (evt == null) {
            return null;
        }
        
        String evtName = evt.getClass().getName();
        for (StoredListener sl : storedListeners){
        	if (sl.hasEvent(evtName)){
        		for (Method m : sl.getMethods(evtName)){
        			methods.put(m, sl);
        			//Glydar.getServer().getLogger().info("Method Found!");
        		}
        	}
        }
        
        for (EventPriority ep : EventPriority.values()){
        	for (Method m : methods.keySet()){
        		if (methods.get(m).checkPriority(m, ep)){
        			if (evt instanceof Cancellable) {
                        Cancellable cancellable = (Cancellable) evt;
                        try {
                            if (!(cancellable.isCancelled() && methods.get(m).checkIgnoreCancelled(m))) {
                                m.invoke(methods.get(m).getListener(), evt);
                                //Glydar.getServer().getLogger().info("Method:" + m.getName());
                            }
                        } catch (Exception ex) {
                        	//Glydar.getServer().getLogger().info("ERROR: Method:" + m.getName());
                            ex.printStackTrace();
                            methods.remove(m);
                        }
        			} else {
	        			try{
	    	        		m.invoke(methods.get(m).getListener(), evt);
	    	        		//Glydar.getServer().getLogger().info(m.getName() + "Invoked!");
	            		} catch (Exception e){
	            			//Glydar.getServer().getLogger().info("ERROR: Method:" + m.getName());
	            			e.printStackTrace();
	            			methods.remove(m);
	            		}
        			}
        		}
        	}
        }

        return evt;
    }
    
}