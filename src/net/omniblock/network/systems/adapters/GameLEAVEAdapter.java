package net.omniblock.network.systems.adapters;

import net.omniblock.network.library.utils.ExpirablePlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameLEAVEAdapter implements Listener {

	@EventHandler
	public void event(PlayerQuitEvent e) {
		ExpirablePlayerData.handleDisconnection(e.getPlayer().getUniqueId());
	}

}
