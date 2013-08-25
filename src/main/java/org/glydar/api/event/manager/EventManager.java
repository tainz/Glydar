package org.glydar.api.event.manager;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import org.glydar.api.event.Event;
import org.glydar.api.event.EventHandler;
import org.glydar.api.event.EventPriority;
import org.glydar.api.event.Listener;
import org.glydar.api.event.manager.RegisteredHandlers.RegisteredHandler;
import org.glydar.api.plugin.Plugin;
import org.glydar.glydar.Glydar;

import com.google.common.base.Predicate;

public class EventManager {

	private int handlerIndex = 0;
	private final Map<Class<? extends Event>, RegisteredHandlers> map = new HashMap<>();

	public boolean register(Plugin plugin, Listener listener) {
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

			register(plugin, listener, method, parameterTypes[0].asSubclass(Event.class), annotation);
		}

		return true;
	}

	private void logInvalidEventHandlerMethod(Object... args) {
		Glydar.getServer().getLogger().log(Level.WARNING, "Event Handler Method `{0}` {1}, skipping", args);
	}

	private <E extends Event> void register(Plugin plugin, Listener listener, Method method, Class<E> eventClass,
			EventHandler annotation) {
		MethodEventExecutor<E> executor = new MethodEventExecutor<E>(eventClass, listener, method, annotation);
		register(plugin, eventClass, executor, annotation.priority());
	}

	public <E extends Event> void register(Plugin plugin, Class<E> eventClass, EventExecutor<E> executor,
			EventPriority priority) {
		if (Modifier.isAbstract(eventClass.getModifiers())) {
			throw new UnsupportedOperationException();
		}

		RegisteredHandler handler = new RegisteredHandler(plugin, handlerIndex++, priority, executor);
		RegisteredHandlers handlers = getHandlers(eventClass);
		handlers.addHandler(handler);
	}

	/**
	 * Retrieves or creates the {@link RegisteredHandlers} for the given event
	 * class.
	 * <p>
	 * If the RegisteredHandlers does not already exist, this method will
	 * create it, recursively calling itself to get or create the 
	 * RegisteredHandlers of the superclass.
	 */
	private RegisteredHandlers getHandlers(Class<? extends Event> eventClass) {
		RegisteredHandlers handlers = map.get(eventClass);
		if (handlers == null) {
			// #asSubclass is safe because we know eventClass is not abstract and so cannot be Event.
			Class<? extends Event> eventSuperClass = eventClass.getSuperclass().asSubclass(Event.class);
			if (!Modifier.isAbstract(eventSuperClass.getModifiers())) {
				RegisteredHandlers parentHandlers = getHandlers(eventSuperClass);
				handlers = new RegisteredHandlers(parentHandlers);
			}
			else {
				handlers = new RegisteredHandlers();
			}

			map.put(eventClass, handlers);
		}

		return handlers;
	}

	public void unregister(final EventExecutor<?> executor) {
		unregisterAllIf(new Predicate<RegisteredHandler>() {

			@Override
			public boolean apply(RegisteredHandler handler) {
				return handler.getExecutor() == executor;
			}
		});
	}

	public void unregister(final Listener listener) {
		unregisterAllIf(new Predicate<RegisteredHandler>() {

			@Override
			public boolean apply(RegisteredHandler handler) {
				if (handler.getExecutor() instanceof MethodEventExecutor<?>) {
					MethodEventExecutor<?> executor = (MethodEventExecutor<?>) handler.getExecutor();
					return executor.getListener() == listener;
				}

				return false;
			}
		});
	}

	public void unregisterAll(final Plugin plugin) {
		unregisterAllIf(new Predicate<RegisteredHandler>() {

			@Override
			public boolean apply(RegisteredHandler handler) {
				return handler.getPlugin() == plugin;
			}
		});
	}

	private void unregisterAllIf(Predicate<RegisteredHandler> predicate) {
		Iterator<RegisteredHandlers> handlersIt = map.values().iterator();
		while (handlersIt.hasNext()) {
			handlersIt.next().removeHandlersIf(predicate);
		}
	}

	public <E extends Event> E callEvent(E event) {
		RegisteredHandlers handlers = getHandlers(event.getClass());
		for (EventExecutor<?> rawExecutor : handlers.resolvedExecutors) {
			@SuppressWarnings("unchecked")
			EventExecutor<? super E> executor = (EventExecutor<? super E>) rawExecutor;
			executor.execute(event);
		}

		return event;
	}
}
