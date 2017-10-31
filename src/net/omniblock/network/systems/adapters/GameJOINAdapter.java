package net.omniblock.network.systems.adapters;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.omniblock.network.handlers.base.bases.type.RankBase;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.rank.type.RankType;

public class GameJOINAdapter implements Listener {

	private static boolean ENABLED_JOIN_MESSAGE = false;

	public static String GOLEM_JOIN_MESSAGE = TextUtil
			.format("&8&l(&e✯&8&l) &7El jugador &rVIP_PLAYER&7 ha ingresado!");
	public static String TITAN_JOIN_MESSAGE = TextUtil
			.format("&8&l(&6✯&8&l) &7El jugador &rVIP_PLAYER&7 ha ingresado!");

	public static String HELPER_JOIN_MESSAGE = TextUtil
			.format("&8&l(&a✯&8&l) &7El ayudante &rHELPER_PLAYER&7 ha ingresado!");
	public static String GM_JOIN_MESSAGE = TextUtil
			.format("&8&l(&9✯&8&l) &7El game master &rGAME_MASTER_PLAYER&7 ha ingresado!");

	@EventHandler(priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent e) {

		if (!ENABLED_JOIN_MESSAGE)
			return;

		RankType rank = RankBase.getRank(e.getPlayer());

		if (rank == RankType.GOLEM) {

			Bukkit.broadcastMessage(
					GOLEM_JOIN_MESSAGE.replaceFirst("VIP_PLAYER", rank.getCustomName(e.getPlayer(), 'f')));
			return;

		}

		if (rank == RankType.TITAN) {

			Bukkit.broadcastMessage(
					TITAN_JOIN_MESSAGE.replaceFirst("VIP_PLAYER", rank.getCustomName(e.getPlayer(), 'f')));
			return;

		}

		if (rank == RankType.HELPER) {

			Bukkit.broadcastMessage(
					HELPER_JOIN_MESSAGE.replaceFirst("HELPER_PLAYER", rank.getCustomName(e.getPlayer(), 'f')));
			return;

		}

		if (rank == RankType.GM) {

			Bukkit.broadcastMessage(
					GM_JOIN_MESSAGE.replaceFirst("GAME_MASTER_PLAYER", rank.getCustomName(e.getPlayer(), 'f')));
			return;

		}

		return;

	}

	public static void toggleJoinMSG(boolean status) {

		ENABLED_JOIN_MESSAGE = status;
		return;

	}

}
