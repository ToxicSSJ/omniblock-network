package net.omniblock.network.systems.rank;

import java.util.*;

import net.omniblock.network.library.utils.ExpirablePlayerData;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.handlers.Handlers;
import net.omniblock.network.handlers.base.bases.type.RankBase;
import net.omniblock.network.handlers.base.sql.util.Resolver;
import net.omniblock.network.handlers.network.NetworkManager;
import net.omniblock.network.library.addons.xmladdon.XMLReader;
import net.omniblock.network.library.addons.xmladdon.XMLType;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.rank.type.RankType;

public class RankManager implements Listener, CommandExecutor {

	public static Map<Player, PermissionAttachment> attachments = new HashMap<Player, PermissionAttachment>();

	public static Map<RankType, Set<String>> permissions = new HashMap<RankType, Set<String>>();
	public static Map<RankType, RankType> carriers = new HashMap<RankType, RankType>();
	private static ExpirablePlayerData<RankType> cachedRanks = new ExpirablePlayerData<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
			return false;
		if (!(sender.hasPermission("omniblock.network.rank"))) {
			sender.sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
			return false;
		}
		
		if (cmd.getName().equalsIgnoreCase("rango") || cmd.getName().equalsIgnoreCase("rank")) {

			if (args.length >= 3) {

				if(!Resolver.hasLastName(args[1])) {

					sender.sendMessage(TextUtil.format("&cEl jugador " + args[1] + " no existe!"));
					return true;

				}

				if(!args[0].equalsIgnoreCase("definir") || args[0].equalsIgnoreCase("set")) {
					
					sender.sendMessage(TextUtil.format("&cEl argumento " + args[0] + " no existe!"));
					return true;
					
				}
				
				if(!RankType.exists(args[2])) {
					
					sender.sendMessage(TextUtil.format("&cEl rango " + args[2] + " no existe!"));
					return true;
					
				}
				
				RankBase.setRank(args[1], RankType.getByName(args[2]));
				sender.sendMessage(TextUtil.format("&aSe le ha definido el rango &7" + args[2] + " &aal jugador &7" + args[1] + "&a!"));
				return true;
			}

			sender.sendMessage(TextUtil.format("&cFaltan más argumentos!"));
			return true;
		}
		
		return false;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player player = e.getPlayer();

		e.setJoinMessage(null);

		RankType userRank = RankBase.getRank(e.getPlayer());

		e.getPlayer().setPlayerListName(userRank.getCustomName(e.getPlayer()));
		e.getPlayer().setDisplayName(userRank.getCustomName(e.getPlayer()));

		cachedRanks.put(e.getPlayer(), userRank);

		if (player.isOp()) {
			if (OmniNetwork.debugMode) {
				Handlers.LOGGER.sendWarning("En el modo de prueba se preserva el modo operador (op).");
			} else {
				player.setOp(false);
			}
		}

		if (attachments.containsKey(player)) {

			attachments.entrySet().stream().forEach(entry -> entry.getValue().getPermissions().entrySet()
					.forEach(permissions -> attachments.get(player).unsetPermission(permissions.getKey())));

			attachments.remove(player);

		}

		RankType rank = RankBase.getRank(player);
		putAttachment(player);

		A: for (String permission : rank.getPermissions()) {

			attachments.get(player).setPermission(permission, true);
			continue A;

		}

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		if (attachments.containsKey(e.getPlayer())) {

			attachments.get(e.getPlayer()).remove();
			attachments.remove(e.getPlayer());
			return;

		}

	}

	public static RankType getCachedRank(Player player) {
		if(!cachedRanks.containsPlayer(player)) {
			RankType userRank = RankBase.getRank(player);
			cachedRanks.put(player, userRank);
			return userRank;
		}

		return cachedRanks.get(player);
	}

	public static void updatePermissions() {

		if (permissions.size() > 0)
			permissions.clear();
		if (carriers.size() > 0)
			carriers.clear();

		XMLReader reader = XMLType.PERMISSIONS.getReader();

		for (RankType type : RankType.values()) {

			Set<String> list_set = new HashSet<String>();

			String list_str = reader.get("Ranks//Enum[.=\"" + type.toString() + "\"]/../Permissions");
			String carrier = reader.get("Ranks//Enum[.=\"" + type.toString() + "\"]/../Carrier");

			if (list_str == null) {
				carriers.put(type, type != RankType.USER ? RankType.USER : null);
				permissions.put(type, list_set);
				continue;
			}
			if (!list_str.contains(";")) {
				list_set.add(list_str);
				carriers.put(type, type != RankType.USER ? RankType.USER : null);
				permissions.put(type, list_set);
				continue;
			}

			String[] list_arr = StringUtils.split(list_str, ";");
			if (list_arr.length > 0) {
				for (String k : list_arr) {
					list_set.add(k.replaceAll(" ", ""));
				}
			}

			if (carrier == null || carrier.equals("") || carrier.equals("@NONE")) {
				carriers.put(type, type != RankType.USER ? RankType.USER : null);
				permissions.put(type, list_set);
				continue;
			}
			if (RankType.valueOf(carrier) != null) {
				carriers.put(type, RankType.valueOf(carrier));
				permissions.put(type, list_set);
				continue;
			}

			carriers.put(type, type != RankType.USER ? RankType.USER : null);
			continue;

		}

		for (Player player : Bukkit.getOnlinePlayers()) {

			if (player.isOp()) {
				if (OmniNetwork.debugMode) {
					Handlers.LOGGER.sendWarning("En el modo de prueba se preserva el modo operador (op).");
				} else {
					player.setOp(false);
				}
			}

			if (attachments.containsKey(player)) {

				attachments.entrySet().stream().forEach(entry -> entry.getValue().getPermissions().entrySet()
						.forEach(permissions -> attachments.get(player).unsetPermission(permissions.getKey())));

				attachments.remove(player);

			}

			RankType rank = RankBase.getRank(player);
			putAttachment(player);

			A: for (String permission : rank.getPermissions()) {

				attachments.get(player).setPermission(permission, true);
				continue A;

			}

			continue;

		}

	}

	public static void addPermission(Player player, String permission) {

		if (attachments.containsKey(player)) {

			attachments.get(player).setPermission(permission, true);
			return;

		}

		RankType rank = RankBase.getRank(player);
		putAttachment(player);

		A: for (String l : rank.getPermissions()) {

			attachments.get(player).setPermission(l, true);
			continue A;

		}

		attachments.get(player).setPermission(permission, true);

	}

	public static void removePermission(Player player, String permission) {

		if (attachments.containsKey(player)) {

			attachments.get(player).unsetPermission(permission);
			return;

		}

		RankType rank = RankBase.getRank(player);
		putAttachment(player);

		A: for (String l : rank.getPermissions()) {

			attachments.get(player).setPermission(l, true);
			continue A;

		}

		attachments.get(player).unsetPermission(permission);

	}

	public static void putAttachment(Player player) {

		if (attachments.containsKey(player)) {

			player.removeAttachment(attachments.get(player));
			attachments.remove(player);

		}

		PermissionAttachment attachment = player.addAttachment(OmniNetwork.getInstance());
		attachments.put(player, attachment);

	}

}
