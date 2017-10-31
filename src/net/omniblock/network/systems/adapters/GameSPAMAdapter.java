package net.omniblock.network.systems.adapters;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.library.utils.TextUtil;

public class GameSPAMAdapter implements Listener {

	public static Map<Player, Integer> SPAM_COUNT = new HashMap<Player, Integer>();
	public static Map<Player, BukkitTask> SPAM_TASK = new HashMap<Player, BukkitTask>();

	public boolean canChat(Player player) {

		if (!SPAM_COUNT.containsKey(player)) {

			SPAM_COUNT.put(player, 1);

			if (!SPAM_TASK.containsKey(player))
				SPAM_TASK.put(player, new BukkitRunnable() {
					@Override
					public void run() {
						if (SPAM_COUNT.containsKey(player)) {
							SPAM_COUNT.remove(player);
							return;
						}
					}
				}.runTaskLater(OmniNetwork.getInstance(), 20 * 8));
			return true;

		}

		if (SPAM_COUNT.get(player) >= 5)
			return false;

		SPAM_COUNT.put(player, SPAM_COUNT.get(player) + 1);
		if (SPAM_TASK.containsKey(player))
			SPAM_TASK.get(player).cancel();

		SPAM_TASK.put(player, new BukkitRunnable() {
			@Override
			public void run() {
				if (SPAM_COUNT.containsKey(player)) {
					SPAM_COUNT.remove(player);
					return;
				}
			}
		}.runTaskLater(OmniNetwork.getInstance(), 20 * 8));

		return true;

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChat(AsyncPlayerChatEvent e) {

		if (e.isCancelled())
			return;

		if (!canChat(e.getPlayer())) {

			e.setCancelled(true);
			e.getPlayer().sendMessage(TextUtil.format("&cPor favor no hagas spam, Procura hablar más despacio..."));
			return;

		}

	}

}
