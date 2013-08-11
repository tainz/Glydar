package org.glydar.glydar.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.glydar.glydar.plugin.Plugin;

public class StoredListener{
    	private Plugin plugin;
    	private Listener listener;
    	private HashMap<String, ArrayList<Method>> methods = new HashMap<String, ArrayList<Method>>();
    	private HashMap<Method, EventPriority> priorities = new HashMap<Method, EventPriority>();
    	private List<Method> ignoreCancelled = new ArrayList<Method>();
    	
    	public StoredListener(Plugin p, Listener l){
    		plugin = p;
    		listener = l;
    	}
    	
    	public Plugin getPlugin(){
    		return plugin;
    	}
    	
    	public Listener getListener(){
    		return listener;
    	}
    	
    	public void addMethod(String eventName, Method method, EventPriority priority, Boolean ic){
    		if (methods.containsKey(eventName)){
    			methods.get(eventName).add(method);
    		} else {
    			methods.put(eventName, new ArrayList<Method>());
    			methods.get(eventName).add(method);
    		}
    		if (ic){
    			ignoreCancelled.add(method);
    		}
    		priorities.put(method, priority);
    	}
    	
    	public boolean hasEvent(String eventName){
    		if (methods.containsKey(eventName)){
    			return true;
    		} else {
    			return false;
    		}
    	}
    	
    	public List<Method> getMethods(String eventName){
    		return methods.get(eventName);
    	}
    	
    	public boolean checkPriority(Method m, EventPriority ep){
    		if (priorities.get(m).equals(ep)){
    			return true;
    		} else {
    			return false;
    		}
    	}
    	
    	public boolean checkIgnoreCancelled (Method m){
    		if (ignoreCancelled.contains(m)){
    			return true;
    		} else {
    			return false;
    		}
    	}
    }