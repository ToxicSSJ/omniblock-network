package net.omniblock.network.systems.adapters;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

public class GameTABAdapter implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void tabComplete(PlayerChatTabCompleteEvent event) {
		event.getTabCompletions().clear();
	}

}
