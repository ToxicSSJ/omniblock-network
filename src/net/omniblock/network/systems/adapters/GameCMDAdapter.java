package net.omniblock.network.systems.adapters;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.omniblock.network.handlers.network.NetworkManager;

public class GameCMDAdapter implements Listener {

	public static Set<String> ACTIVED_COMMANDS = new HashSet<String>();
	public static Set<String> BLOCKED_COMMANDS = new HashSet<String>();
	
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
		ACTIVED_COMMANDS.add("warps");
		ACTIVED_COMMANDS.add("warp");
		ACTIVED_COMMANDS.add("home");
		ACTIVED_COMMANDS.add("sethome");
		ACTIVED_COMMANDS.add("makeWarp");
		ACTIVED_COMMANDS.add("lobby");
		ACTIVED_COMMANDS.add("hub");
		ACTIVED_COMMANDS.add("spawn");

		ACTIVED_COMMANDS.add("pblocks");
		ACTIVED_COMMANDS.add("protectionb");
		ACTIVED_COMMANDS.add("ores");
		ACTIVED_COMMANDS.add("pb");
		ACTIVED_COMMANDS.add("ps");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCommand(PlayerCommandPreprocessEvent e) {

		if (!e.getPlayer().hasPermission("omniblock.network.cmd.ignore")) {

			if (e.getMessage().startsWith("/")) {

				String cmd = e.getMessage().replaceFirst("/", "");
				String[] cmdArguments = cmd.split(" ");
				
				if (!ACTIVED_COMMANDS.contains(cmdArguments[0])) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
				}
			}

		}

	}
}
