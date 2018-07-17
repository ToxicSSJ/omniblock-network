package net.omniblock.network.library.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

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

}
