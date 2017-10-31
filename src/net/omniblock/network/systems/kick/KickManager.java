package net.omniblock.network.systems.kick;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.handlers.base.bases.type.BanBase;
import net.omniblock.network.handlers.base.bases.type.RankBase;
import net.omniblock.network.handlers.base.sql.type.TableType;
import net.omniblock.network.handlers.base.sql.util.Resolver;
import net.omniblock.network.handlers.network.NetworkManager;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.CommandPatcher;
import net.omniblock.network.systems.rank.type.RankType;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.packet.PlayerSendBanPacket;
import net.omniblock.packets.network.structure.packet.PlayerSendKickPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;

public class KickManager implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
			return false;
		if (!(sender.hasPermission("omniblock.network.moderator"))) {
			sender.sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
			return false;
		}

		if (cmd.getName().equalsIgnoreCase("kick") || cmd.getName().equalsIgnoreCase("kickear")) {

			if (args.length >= 2) {

				String player = args[0];
				String reason = args[1];

				if (args.length >= 3) {

					reason = "";
					StringBuffer cache = new StringBuffer("");

					for (int i = 2; i >= (args.length - 1); i++) {

						cache.append(args[i] + " ");
						continue;

					}

					reason = cache.toString();

				}

				if (player.equals(sender.getName())) {
					sender.sendMessage(TextUtil.format("&cNo te puedes kickear a ti mismo!"));
					return true;
				}

				RankType rank = RankBase.getRank(player);

				if (rank.isStaff()) {

					RankType senderrank = RankBase.getRank(sender.getName());

					if (!(senderrank == RankType.CEO || senderrank == RankType.ADMIN)) {

						sender.sendMessage(" ");
						sender.sendMessage(CommandPatcher.BAR);
						sender.sendMessage(TextUtil
								.getCenteredMessage(" &8§lK§8ickeos &b&l» &7Se ha detectado que estás tratando"));
						sender.sendMessage(
								TextUtil.getCenteredMessage(" &7de expulsar a un miembro del equipo Staff!"));
						sender.sendMessage(TextUtil
								.getCenteredMessage(" &7Por consiguiente serás baneado con el codigo &b&lBPBS#2"));
						sender.sendMessage(TextUtil
								.getCenteredMessage(" &7que implica que cuando un administrador se enteré de tu "));
						sender.sendMessage(
								TextUtil.getCenteredMessage(" &7acción, serás interrogado o expulsado del equipo."));
						sender.sendMessage(CommandPatcher.BAR);
						sender.sendMessage(" ");

						Date date = new Date();
						String hash = UUID.randomUUID().toString().toString().substring(1, 10);

						String banned_id = Resolver.getNetworkIDByName(sender.getName());
						String mod_id = "CONSOLE";

						reason = "BPBS#2";

						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						calendar.add(Calendar.YEAR, 10);

						String from_str = format.format(date);
						String to_str = format.format(calendar.getTime());

						String[] data = new String[] { hash, mod_id, banned_id, reason, from_str, to_str };
						String insert = getBanInsertSQL(data);

						if (insert == null) {
							sender.sendMessage(TextUtil
									.format("&cEl sistema de kickeos se encuentra deshabilitado temporalmente..."));
							return true;
						}

						new BukkitRunnable() {
							@Override
							public void run() {

								Packets.STREAMER.streamPacket(new PlayerSendBanPacket().setPlayername(sender.getName())
										.build().setReceiver(PacketSenderType.OMNICORD));

								BanBase.setBanStatus(sender.getName(), true);
								BanBase.insertBanRegistry(insert);

							}
						}.runTaskLater(OmniNetwork.getInstance(), 20 * 3);

						return true;

					}

				}

				Packets.STREAMER.streamPacket(
						new PlayerSendKickPacket().setPlayername(sender.getName()).setModerator(sender.getName())
								.setReason(reason).build().setReceiver(PacketSenderType.OMNICORD));

				sender.sendMessage(" ");
				sender.sendMessage(CommandPatcher.BAR);
				sender.sendMessage(TextUtil.getCenteredMessage(" &a¡Has hecho un kickeo correctamente!"));
				sender.sendMessage(TextUtil.getCenteredMessage(""));
				sender.sendMessage(TextUtil.getCenteredMessage(" &cJugador: &7" + player));
				sender.sendMessage(TextUtil.getCenteredMessage(" &cRazón: &7" + reason));
				sender.sendMessage(TextUtil.getCenteredMessage(""));
				sender.sendMessage(CommandPatcher.BAR);
				sender.sendMessage(" ");
				return true;

			}

			sender.sendMessage(" ");
			sender.sendMessage(CommandPatcher.BAR);
			sender.sendMessage(TextUtil.getCenteredMessage(" &8&lK&8ickeos &b&l» &7Te ha faltado un argumento!"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &7Rercuerda que todos los datos deben estár puestos!"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &7El formato actual de kickeos es el siguiente: "));
			sender.sendMessage(TextUtil.getCenteredMessage(" &bFormato:  &e/kickear [jugador] [razón]"));
			sender.sendMessage(TextUtil.getCenteredMessage(" &bEjemplo:  &7/kickear KamiKaze No hagas spam!"));
			sender.sendMessage(CommandPatcher.BAR);
			sender.sendMessage(" ");
			return true;

		}

		return false;

	}

	private String getBanInsertSQL(String[] data) {

		if (data.length >= 6) {

			String SQL = TableType.BAN_REGISTRY.getInserter().getInserterSQL();

			if (SQL.contains("VAR_BAN_HASH")) {
				SQL = SQL.replaceAll("VAR_BAN_HASH", "'" + data[0] + "'");
			}
			if (SQL.contains("VAR_MOD")) {
				SQL = SQL.replaceAll("VAR_MOD", "'" + data[1] + "'");
			}
			if (SQL.contains("VAR_BANNED")) {
				SQL = SQL.replaceAll("VAR_BANNED", "'" + data[2] + "'");
			}
			if (SQL.contains("VAR_REASON")) {
				SQL = SQL.replaceAll("VAR_REASON", "'" + data[3] + "'");
			}
			if (SQL.contains("VAR_BAN_TIME_FROM")) {
				SQL = SQL.replaceAll("VAR_BAN_TIME_FROM", "'" + data[4] + "'");
			}
			if (SQL.contains("VAR_BAN_TIME_TO")) {
				SQL = SQL.replaceAll("VAR_BAN_TIME_TO", "'" + data[5] + "'");
			}

			return SQL;

		}

		return null;
	}

}
