package net.omniblock.network.systems.adapters;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.omniblock.network.library.utils.TextUtil;

public class GameCHATAdapter implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChat(AsyncPlayerChatEvent e) {

		if (e.isCancelled())
			return;

		e.setFormat(TextUtil.format("%s:&f %s"));

	}

}
