package net.omniblock.network.systems.adapters;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.omniblock.network.handlers.base.bases.type.RankBase;
import net.omniblock.network.library.utils.TagUtil;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.library.utils.TagUtil.TeamAction;
import net.omniblock.network.systems.rank.type.RankType;

public class GameJOINAdapter implements Listener {

	private static boolean ENABLED_JOIN_MESSAGE = true;
	private static boolean ENABLED_TAG_TITLE = true;
	
	private static final Map<RankType, String> JOIN_MESSAGES = new HashMap<RankType, String>() {

		private static final long serialVersionUID = 1L;

		{
			
			put(RankType.GOLEM, "&8&l(&e✯&8&l) &7El jugador &rPLAYER&7 ha ingresado!");
			put(RankType.TITAN, "&8&l(&e✯&8&l) &7El jugador &rPLAYER&7 ha ingresado!");
			
			put(RankType.HELPER, "&8&l(&a✯&8&l) &7El ayudante &rPLAYER&7 ha ingresado!");
			put(RankType.MOD, "&8&l(&c✯&8&l) &7El moderador &rPLAYER&7 ha ingresado!");
			put(RankType.BNF, "&8&l(&9✯&8&l) &7El benefactor &rPLAYER&7 ha ingresado!");
			
			put(RankType.GM, "&8&l(&b✯&8&l) &7El game master &rPLAYER&7 ha ingresado!");
			put(RankType.ADMIN, "&8&l(&4✯&8&l) &7El administrador &rPLAYER&7 ha ingresado!");
			put(RankType.DIRECTOR, "&8&l(&3✯&8&l) &7El director general &rPLAYER&7 ha ingresado!");
			
		}
		
	};
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e) {

		e.setJoinMessage(null);

		RankType rank = RankBase.getRank(e.getPlayer());
		
		if(ENABLED_TAG_TITLE)
			TagUtil.changePlayerName(e.getPlayer(), TextUtil.format(rank.getPrefix() + " &f"), "", TeamAction.CREATE);
		
		if(JOIN_MESSAGES.containsKey(rank) && ENABLED_JOIN_MESSAGE)
			Bukkit.broadcastMessage(TextUtil.format(
					JOIN_MESSAGES.get(rank).replaceFirst("PLAYER", rank.getCustomName(e.getPlayer(), 'f'))));
		return;

	}

	public static void toggleJoinMSG(boolean status) {

		ENABLED_JOIN_MESSAGE = status;
		return;

	}
	
	public static void toggleTagTitle(boolean status) {

		ENABLED_TAG_TITLE = status;
		return;

	}

}
