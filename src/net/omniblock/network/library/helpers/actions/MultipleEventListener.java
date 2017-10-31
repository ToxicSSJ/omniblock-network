package net.omniblock.network.library.helpers.actions;

import org.bukkit.event.Event;

import net.omniblock.network.systems.ActionsPatcher;

public abstract class MultipleEventListener {

	private boolean autoConsume;
	private Class<? extends Event>[] events;

	@SafeVarargs
	public MultipleEventListener(boolean autoConsume, Class<? extends Event>... clazz) {
		this.autoConsume = autoConsume;
		this.events = clazz;
		ActionsPatcher.addMultipleEventListener(this);
	}

	public void handle(Event e) {
		Class<?> clazz = e.getClass();

		for (Class<? extends Event> clazzE : events) {
			if (clazzE.equals(clazz)) {
				if (incomingEvent(e) && autoConsume) {
					ActionsPatcher.removeMultipleEventListener(this);
				}
			}
		}
	}

	public abstract boolean incomingEvent(Event e);

}
