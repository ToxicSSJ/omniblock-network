package net.omniblock.network.library.helpers.actions;

import org.bukkit.event.Event;

import net.omniblock.network.systems.ActionsPatcher;

public abstract class SimpleEventListener<T extends Event> {

	private Class<T> clazz;
	private boolean autoConsume;

	public SimpleEventListener(Class<T> clazz, boolean autoConsume) {
		this.clazz = clazz;
		this.autoConsume = autoConsume;
		ActionsPatcher.addSimpleEventListener(clazz, this);
	}

	public abstract boolean incomingEvent(T e);

	@SuppressWarnings("unchecked")
	public void handle(Event e) {
		if (incomingEvent((T) e) && autoConsume) {
			ActionsPatcher.removeSimpleEventListener(clazz, this);
		}
	}

}
