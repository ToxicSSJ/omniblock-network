package net.omniblock.network.systems.adapters;

import net.omniblock.network.systems.rank.RankManager;
import net.omniblock.network.systems.rank.type.RankType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.omniblock.network.library.utils.TextUtil;

import java.util.Optional;

public class GameCHATAdapter implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChat(AsyncPlayerChatEvent e) {

		if (e.isCancelled())
			return;

		Optional<RankType> optionalValue = RankManager.getCachedRank(e.getPlayer());
		optionalValue.ifPresent(rankType -> e.getPlayer().setDisplayName(rankType.getCustomName(e.getPlayer())));

		e.setFormat(TextUtil.format("%s:&f %s"));
	}

}
