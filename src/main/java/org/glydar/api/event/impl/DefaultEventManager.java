package org.glydar.api.event.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;

import org.glydar.api.event.Event;
import org.glydar.api.event.EventExecutor;
import org.glydar.api.event.EventHandler;
import org.glydar.api.event.EventManager;
import org.glydar.api.event.EventOrder;
import org.glydar.api.event.EventPhase;
import org.glydar.api.event.Listener;
import org.glydar.glydar.Glydar;

public class DefaultEventManager implements EventManager {

    private static final String EVENT_SUPPORT_FIELD = "EVENT_SUPPORT";

    public void register(Listener listener) {
        for (Method method : listener.getClass().getDeclaredMethods()) {
            EventHandler annotation = method.getAnnotation(EventHandler.class);
            if (annotation == null) {
                continue;
            }

            if (Modifier.isStatic(method.getModifiers())) {
                logInvalidEventHandlerMethod(method, "is static");
                continue;
            }

            if (!Modifier.isPublic(method.getModifiers())) {
                logInvalidEventHandlerMethod(method, "is not public");
                continue;
            }

            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                logInvalidEventHandlerMethod(method, "does not have one and only one parameter");
                continue;
            }

            if (!Event.class.isAssignableFrom(parameterTypes[0])) {
                logInvalidEventHandlerMethod(method, "is not of type `Event`");
                continue;
            }

            register(listener, method, parameterTypes[0].asSubclass(Event.class), annotation);
        }
    }

    private void logInvalidEventHandlerMethod(Object... args) {
        Glydar.getServer().getLogger().log(Level.WARNING, "Event Handler Method `{0}` {1}, skipping", args);
    }

    private <E extends Event> void register(Listener listener, Method method, Class<E> eventClass,
            EventHandler annotation) {
        MethodEventExecutor<E> executor = new MethodEventExecutor<E>(eventClass, listener, method);
        register(annotation.phase(), annotation.order(), eventClass, executor);
    }

    @Override
    public <E extends Event> void register(EventPhase phase, EventOrder order, Class<E> eventClass, EventExecutor<E> executor) {
        StoredHandler<E> stored = new StoredHandler<E>(order, executor);
        EventSupport<E> eventSupport = eventSupport(eventClass);
        eventSupport.addHandler(phase, stored);
    }

    private <E extends Event> EventSupport<E> eventSupport(Class<E> eventClass) {
        try {
            Field field = eventClass.getDeclaredField(EVENT_SUPPORT_FIELD);
            if (!Modifier.isStatic(field.getModifiers())) {
                Glydar.getServer().getLogger().log(Level.WARNING,
                        "Field `" + EVENT_SUPPORT_FIELD + "` is not static for Event " + eventClass.getName());
                return null;
            }
            if (!EventSupport.class.isAssignableFrom(field.getType())) {
                System.out.println(field.getType());
                Glydar.getServer().getLogger().log(Level.WARNING,
                        "Field `eventSupport` is not an instance of `EventSupport`" + eventClass.getName());
                return null;
            }

            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            EventSupport<E> support = (EventSupport<E>) field.get(null);
            return support;
        }
        catch (NoSuchFieldException exc) {
            Glydar.getServer().getLogger().log(Level.WARNING, "Missing `" + EVENT_SUPPORT_FIELD
                    + "` field for Event " + eventClass.getName(), exc);
        }
        catch (SecurityException | IllegalArgumentException | IllegalAccessException exc) {
            Glydar.getServer().getLogger().log(Level.WARNING,
                    "Unable to access `eventSupport` field for Event " + eventClass.getName(), exc);
        }

        return null;
    }

    @Override
    public <E extends Event> E callEvent(E event) {
        @SuppressWarnings("unchecked")
        EventSupport<E> support = (EventSupport<E>) event.getEventSupport();
        for (StoredHandler<E> handler : support.getHandlers(EventPhase.HANDLE)) {
            handler.execute(event);
        }
        for (StoredHandler<E> handler : support.getHandlers(EventPhase.MONITOR)) {
            handler.execute(event);
        }

        return event;
    }
}
