package net.omniblock.network.systems.adapters;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.omniblock.network.handlers.network.NetworkManager;

public class GameCMDAdapter implements Listener {

	public static Set<String> ACTIVED_COMMANDS = new HashSet<>();
	public static Set<String> BLOCKED_COMMANDS = new HashSet<>();
	
	static {

		ACTIVED_COMMANDS.add("ban");
		ACTIVED_COMMANDS.add("banear");
		ACTIVED_COMMANDS.add("kick");
		ACTIVED_COMMANDS.add("kickear");
		ACTIVED_COMMANDS.add("mute");
		ACTIVED_COMMANDS.add("mutear");

		ACTIVED_COMMANDS.add("party");
		ACTIVED_COMMANDS.add("fiesta");
		ACTIVED_COMMANDS.add("grupo");
		ACTIVED_COMMANDS.add("amigos");
		ACTIVED_COMMANDS.add("friends");
		ACTIVED_COMMANDS.add("rank");

		ACTIVED_COMMANDS.add("money");
		ACTIVED_COMMANDS.add("dinero");
		ACTIVED_COMMANDS.add("trabajos");
		ACTIVED_COMMANDS.add("tienda");
		ACTIVED_COMMANDS.add("shop");
		ACTIVED_COMMANDS.add("warps");
		ACTIVED_COMMANDS.add("tpa");
		ACTIVED_COMMANDS.add("tpaccept");
		ACTIVED_COMMANDS.add("tpdeny");
		ACTIVED_COMMANDS.add("tpahere");
		ACTIVED_COMMANDS.add("tphaccept");
		ACTIVED_COMMANDS.add("tphdeny");
		ACTIVED_COMMANDS.add("back");
		ACTIVED_COMMANDS.add("warp");
		ACTIVED_COMMANDS.add("home");
		ACTIVED_COMMANDS.add("sethome");
		ACTIVED_COMMANDS.add("makeWarp");
		ACTIVED_COMMANDS.add("lobby");
		ACTIVED_COMMANDS.add("hub");
		ACTIVED_COMMANDS.add("spawn");
		ACTIVED_COMMANDS.add("fly");
		ACTIVED_COMMANDS.add("pay");
		ACTIVED_COMMANDS.add("help");
		ACTIVED_COMMANDS.add("ayuda");
		ACTIVED_COMMANDS.add("stoggle");
		ACTIVED_COMMANDS.add("st");

		ACTIVED_COMMANDS.add("pblocks");
		ACTIVED_COMMANDS.add("protectionb");
		ACTIVED_COMMANDS.add("ores");
		ACTIVED_COMMANDS.add("pb");
		ACTIVED_COMMANDS.add("ps");

		ACTIVED_COMMANDS.add("skin");

		ACTIVED_COMMANDS.add("pvp");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCommand(PlayerCommandPreprocessEvent e) {

		if (!e.getPlayer().hasPermission("omniblock.network.cmd.ignore")) {

			if (e.getMessage().startsWith("/")) {

				String cmd = e.getMessage().replaceFirst("/", "");
				String[] cmdArguments = cmd.split(" ");
				
				if (!ACTIVED_COMMANDS.contains(cmdArguments[0].toLowerCase())) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
				}
			}

		}

	}
}
