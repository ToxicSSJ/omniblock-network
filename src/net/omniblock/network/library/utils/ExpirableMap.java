package net.omniblock.network.library.utils;

import net.omniblock.network.OmniNetwork;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class ExpirableMap<K,V> {

	private static List<ExpirableMap<?,?>> instances = new ArrayList<>();
	private static int instanceIndex = 0;

	static {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(instances.isEmpty()) {
					return;
				}

				//solo una instancia por segundo, asi se evita lag
				if(instanceIndex >= instances.size()) {
					instanceIndex = 0;
				}

				ExpirableMap<?,?> instance = instances.get(instanceIndex);

				instanceIndex++;

				long timeToKeepMillis = instance.timeUnit.toMillis(instance.timeToKeep);
				long currentTime = System.currentTimeMillis();

				Iterator<? extends ExpirableEntry<?>> iterator = instance.values().iterator();
				while(iterator.hasNext()) {
					ExpirableEntry<?> exp = iterator.next();
					long diff = currentTime - exp.creationTime;
					if(diff >= timeToKeepMillis) {
						iterator.remove();
					}
				}
			}
		}.runTaskTimer(OmniNetwork.getInstance(), 20, 20);
	}

	private int timeToKeep;
	private TimeUnit timeUnit;
	private Map<K,ExpirableEntry<V>> data = new HashMap<>();

	public ExpirableMap(int timeToKeep, TimeUnit timeUnit) {
		instances.add(this);
		this.timeToKeep = timeToKeep;
		this.timeUnit = timeUnit;
	}

	public void put(K key, V value) {
		data.put(key, new ExpirableEntry<>(value));
	}

	public V get(K key) {
		ExpirableEntry<V> value = data.get(key);

		if(value != null) {
			value.updateTime();
		}

		return value != null ? value.getValue() : null;
	}

	public V remove(K key) {
		ExpirableEntry<V> value = data.remove(key);
		return value != null ? value.getValue() : null;
	}

	public boolean containsKey(K key) {
		return data.containsKey(key);
	}

	public boolean containsValue(V value) {
		//noinspection SuspiciousMethodCalls
		return data.containsValue(value);
	}

	public Collection<ExpirableEntry<V>> values() {
		return data.values();
	}

	public Set<K> keySet() {
		return data.keySet();
	}

	public Set<Map.Entry<K, ExpirableEntry<V>>> entrySet() {
		return data.entrySet();
	}

	public boolean isEmpty() {
		return data.isEmpty();
	}

	public int size() {
		return data.size();
	}

	public void clear() {
		data.clear();
	}

	public void forEach(BiConsumer<K, ExpirableEntry<V>> consume) {
		data.forEach(consume);
	}

	public static class ExpirableEntry<V> {
		private long creationTime;
		private V value;

		public ExpirableEntry(V value) {
			this.creationTime = System.currentTimeMillis();
			this.value = value;
		}

		public V getValue() {
			return value;
		}

		@Override
		public boolean equals(Object obj) {
			if(obj instanceof ExpirableEntry) {
				ExpirableEntry<?> other = (ExpirableEntry<?>) obj;
				return other.getValue().equals(value);
			} else {
				return obj.equals(value);
			}
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(value);
		}

		public void updateTime() {
			//Evitar que el dado expire si está siendo usado.
			this.creationTime = System.currentTimeMillis();
		}
	}

}
