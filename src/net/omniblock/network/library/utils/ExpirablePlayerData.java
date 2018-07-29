package net.omniblock.network.library.utils;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.BiConsumer;

public class ExpirablePlayerData<T> {

	private static HashSet<ExpirablePlayerData<?>> instances = new HashSet<>();

	public static void handleDisconnection(UUID uuid) {
		instances.forEach(instance -> instance.remove(uuid));
	}

	public ExpirablePlayerData() {
		instances.add(this);
	}

	private Map<UUID, T> players = new HashMap<>();

	public void put(Player player, T data) {
		put(player.getUniqueId(), data);
	}

	public void put(UUID player, T data) {
		players.put(player, data);
	}

	public T get(Player player) {
		return get(player.getUniqueId());
	}

	public T get(UUID player) {
		return players.get(player);
	}

	public boolean containsPlayer(Player player) {
		return containsPlayer(player.getUniqueId());
	}

	public boolean containsPlayer(UUID player) {
		return players.containsKey(player);
	}

	public boolean containsValue(T data) {
		return players.containsValue(data);
	}

	public T remove(Player player) {
		return remove(player.getUniqueId());
	}

	public T remove(UUID player) {
		return players.remove(player);
	}

	public void forEach(BiConsumer<UUID, T> consumer) {
		players.forEach(consumer);
	}

	public Set<Map.Entry<UUID, T>> entrySet() {
		return players.entrySet();
	}

	public void clear() {
		players.clear();
	}

	public int size() {
		return players.size();
	}

	public boolean isEmpty() {
		return players.isEmpty();
	}

	public Collection<T> values() {
		return players.values();
	}

	public Set<UUID> keySet() {
		return players.keySet();
	}

}
