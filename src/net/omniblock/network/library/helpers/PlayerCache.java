package net.omniblock.network.library.helpers;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Stream;

public class PlayerCache<T> {

	private static Set<PlayerCache<?>> INSTANCES = new HashSet<>();

	public static void handlePlayerDisconnection(UUID uuid) {
		INSTANCES.forEach(pc -> pc.remove(uuid));
	}

	private Map<UUID, T> cacheData = new HashMap<>();
	private boolean destroyed = false;

	public PlayerCache() {
		INSTANCES.add(this);
	}

	/**
	 * Usar cuando el Cache desea ser destruido, al usar destroy() este objeto quera irrecuperable y no puede
	 * ser usado de nuevo para almacenar datos.<br><br>
	 * Este método no tiene efecto cuando el Cache ya está destruido.
	 */
	public void destroy() {
		if(destroyed) return;

		INSTANCES.remove(this);
		clear();
		destroyed = true;
	}

	public void put(UUID uuid, T data) {
		verifyDestroyStatus();
		cacheData.put(uuid, data);
	}

	public void remove(UUID uuid) {
		verifyDestroyStatus();
		cacheData.remove(uuid);
	}

	public boolean containsPlayer(UUID uuid) {
		verifyDestroyStatus();
		return cacheData.containsKey(uuid);
	}

	public boolean containsPlayer(Player player) {
		verifyDestroyStatus();
		return containsPlayer(player.getUniqueId());
	}

	public boolean contains(T data) {
		verifyDestroyStatus();
		return cacheData.containsValue(data);
	}

	public int size() {
		verifyDestroyStatus();
		return cacheData.size();
	}

	public Stream<Map.Entry<UUID, T>> cacheStream(){
		verifyDestroyStatus();
		return cacheData.entrySet().stream();
	}

	public T get(UUID uuid) {
		verifyDestroyStatus();
		return cacheData.get(uuid);
	}

	public T get(Player player) {
		verifyDestroyStatus();
		return get(player.getUniqueId());
	}

	public T getOrDefault(UUID uuid, T def) {
		verifyDestroyStatus();
		return cacheData.getOrDefault(uuid, def);
	}

	public T getOrDefault(Player player, T def) {
		verifyDestroyStatus();
		return getOrDefault(player.getUniqueId(), def);
	}

	public void clear() {
		verifyDestroyStatus();
		cacheData.clear();
	}

	public Collection<T> values() {
		verifyDestroyStatus();
		return cacheData.values();
	}

	public Set<UUID> keySet() {
		verifyDestroyStatus();
		return cacheData.keySet();
	}

	public Set<Map.Entry<UUID, T>> entrySet() {
		verifyDestroyStatus();
		return cacheData.entrySet();
	}

	private void verifyDestroyStatus() {
		if(destroyed) {
			throw new IllegalStateException("Cache destruido, no se permite realizar la operacion solicitada.");
		}
	}
}
