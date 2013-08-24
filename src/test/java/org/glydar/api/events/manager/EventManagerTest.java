package org.glydar.api.events.manager;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.glydar.api.event.Listener;
import org.glydar.api.event.manager.EventManager;
import org.glydar.api.events.manager.HelpersEvent.CancellableEvent;
import org.glydar.api.events.manager.HelpersEvent.DummyEvent;
import org.glydar.api.events.manager.HelpersEvent.SubEvent;
import org.glydar.api.events.manager.HelpersListener.CallListener;
import org.glydar.api.events.manager.HelpersListener.CancellableListener;
import org.glydar.api.events.manager.HelpersListener.PrioritiesListener;
import org.glydar.api.plugin.Plugin;
import org.junit.Before;
import org.junit.Test;

public class EventManagerTest {

	private Plugin plugin;
	private EventManager eventManager;

	@Before
	public void setUp() {
		this.plugin = new DummyPlugin();
		this.eventManager = new EventManager();
	}

	private <L extends Listener> L register(L listener) {
		eventManager.register(plugin, listener);
		return listener;
	}

	@Test
	public void testDummyEvent() {
		eventManager.callEvent(new DummyEvent());
		// Nothing registered, ensure there's no exception
	}

	@Test
	public void testRegisterAndCall() {
		CallListener listener = register(new CallListener());

		eventManager.callEvent(new DummyEvent());
		assertTrue(listener.testEventCalled());
	}

	@Test
	public void testRegisterAndCallSubEvent() {
		CallListener listener = register(new CallListener());

		eventManager.callEvent(new SubEvent());
		assertTrue(listener.testEventCalled());
		assertTrue(listener.testSubEventCalled());
		eventManager.callEvent(new DummyEvent());
		assertTrue(listener.testEventCalled());
		assertFalse(listener.testSubEventCalled());
	}

	@Test
	public void testRegisterAndUnregisterByListener() {
		CallListener listener = register(new CallListener());

		eventManager.callEvent(new DummyEvent());
		assertTrue(listener.testEventCalled());
		eventManager.unregister(listener);
		eventManager.callEvent(new DummyEvent());
		assertFalse(listener.testEventCalled());
	}

	@Test
	public void testRegisterAndUnregisterByPlugin() {
		CallListener listener = register(new CallListener());

		eventManager.callEvent(new DummyEvent());
		assertTrue(listener.testEventCalled());
		eventManager.unregisterAll(plugin);
		eventManager.callEvent(new DummyEvent());
		assertFalse(listener.testEventCalled());
	}

	@Test
	public void testPriorities() {
		PrioritiesListener listener = register(new PrioritiesListener());

		eventManager.callEvent(new DummyEvent());
		assertEquals(0, listener.getTestLowestEventCallPosition());
		assertEquals(1, listener.getTestNormalEventCallPosition());
		assertEquals(2, listener.getTestHighestEventCallPosition());
	}

	@Test
	public void testCancellable() {
		CancellableListener listener = register(new CancellableListener());

		eventManager.callEvent(new CancellableEvent());
		assertFalse(listener.ignoreCalled());
	}
}
