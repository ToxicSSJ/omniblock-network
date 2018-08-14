package net.omniblock.network;

import net.omniblock.network.library.helpers.PlayerCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@Deprecated
public class SingleListener implements Listener {

	@EventHandler
	public void event(PlayerQuitEvent e) {
		PlayerCache.handlePlayerDisconnection(e.getPlayer().getUniqueId());
	}

}
