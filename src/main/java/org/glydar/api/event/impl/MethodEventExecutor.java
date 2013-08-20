package org.glydar.api.event.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.glydar.api.event.Event;
import org.glydar.api.event.EventExecutor;
import org.glydar.api.event.Listener;

public class MethodEventExecutor<E extends Event> implements EventExecutor<E> {

    private final Object listener;
    private final Method method;

    public MethodEventExecutor(Class<E> eventClass, Listener listener, Method method) {
        this.listener = listener;
        this.method = method;
    }

    @Override
    public void execute(E event) {
        try {
            method.invoke(listener, event);
        }
        catch (IllegalAccessException | IllegalArgumentException exc) {
            throw new MethodEventExecutorException(exc);
        }
        catch (InvocationTargetException exc) {
            throw new MethodEventExecutorException(exc.getCause());
        }
    }

    public static class MethodEventExecutorException extends RuntimeException {

        private static final long serialVersionUID = 1694598385544729424L;

        public MethodEventExecutorException(Throwable throwable) {
            super(throwable);
        }
    }
}