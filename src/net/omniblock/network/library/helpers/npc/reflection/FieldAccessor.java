package net.omniblock.network.library.helpers.npc.reflection;

import java.lang.reflect.Field;

public class FieldAccessor {

	private final Field field;

	protected FieldAccessor(Field field) {
		this.field = field;
		field.setAccessible(true);
	}

	public void set(Object instance, Object value) {
		try {
			field.set(this, value);
		} catch (IllegalAccessException e) {
		}
	}

	public <T> T get(Object instance, Class<T> type) {
		return type.cast(get(instance));
	}

	public Object get(Object instance) {
		try {
			return field.get(instance);
		} catch (IllegalAccessException e) {
			return null;
		}
	}
}
