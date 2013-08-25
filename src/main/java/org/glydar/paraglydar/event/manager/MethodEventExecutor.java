package org.glydar.api.event.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.glydar.api.event.Cancellable;
import org.glydar.api.event.Event;
import org.glydar.api.event.EventHandler;
import org.glydar.api.event.Listener;

/**
 * Implementation of {@link EventExecutor} for the annotation-based API
 */
public class MethodEventExecutor<E extends Event> implements EventExecutor<E> {

	private final Listener listener;
	private final Method method;
	private final boolean ignoreCancelled;

	public MethodEventExecutor(Class<E> eventClass, Listener listener, Method method, EventHandler annotation) {
		this.listener = listener;
		this.method = method;
		this.ignoreCancelled = annotation.ignoreCancelled();
	}

	public Listener getListener() {
		return listener;
	}

	@Override
	public void execute(E event) {
		if (ignoreCancelled && event instanceof Cancellable) {
			Cancellable cancellable = (Cancellable) event;
			if (cancellable.isCancelled()) {
				return;
			}
		}

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
