package org.glydar.api.events.manager;

import org.glydar.api.event.EventHandler;
import org.glydar.api.event.EventPriority;
import org.glydar.api.event.Listener;
import org.glydar.api.events.manager.HelpersEvent.CancellableEvent;
import org.glydar.api.events.manager.HelpersEvent.DummyEvent;
import org.glydar.api.events.manager.HelpersEvent.SubEvent;

public class HelpersListener {

	public static class CallListener implements Listener {

		private boolean testEventCalled = false;
		private boolean testSubEventCalled = false;

		@EventHandler
		public void on(DummyEvent event) {
			testEventCalled = true;
		}

		@EventHandler
		public void on(SubEvent event) {
			testSubEventCalled = true;
		}

		public boolean testEventCalled() {
			boolean was = testEventCalled;
			testEventCalled = false;
			return was;
		}

		public boolean testSubEventCalled() {
			boolean was = testSubEventCalled;
			testSubEventCalled = false;
			return was;
		}
	}

	public static class CancellableListener implements Listener {

		private boolean ignoredCalled = false;

		@EventHandler
		public void on(CancellableEvent event) {
			event.setCancelled(true);
		}

		@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
		public void ignored(CancellableEvent event) {
			ignoredCalled = true;
		}

		public boolean ignoreCalled() {
			return ignoredCalled;
		}
	}

	public static class PrioritiesListener implements Listener {

		private int position = 0;
		private int testLowestEventCallPosition = -1;
		private int testNormalEventCallPosition = -1;
		private int testHighestEventCallPosition = -1;

		@EventHandler(priority = EventPriority.NORMAL)
		public void onNormal(DummyEvent event) {
			testNormalEventCallPosition = position++;
		}

		@EventHandler(priority = EventPriority.HIGHEST)
		public void onHighest(DummyEvent event) {
			testHighestEventCallPosition = position++;
		}

		@EventHandler(priority = EventPriority.LOWEST)
		public void onLowest(DummyEvent event) {
			testLowestEventCallPosition = position++;
		}

		public int getTestLowestEventCallPosition() {
			return testLowestEventCallPosition;
		}

		public int getTestNormalEventCallPosition() {
			return testNormalEventCallPosition;
		}

		public int getTestHighestEventCallPosition() {
			return testHighestEventCallPosition;
		}
	}
}
