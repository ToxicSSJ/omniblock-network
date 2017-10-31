package net.omniblock.network.systems.bank;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.omniblock.network.handlers.base.bases.type.BankBase;
import net.omniblock.network.handlers.base.sql.util.Resolver;
import net.omniblock.network.handlers.network.NetworkManager;
import net.omniblock.network.library.utils.NumberUtil;
import net.omniblock.network.library.utils.TextUtil;

public class BankManager implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
			return false;
		if (!(sender.hasPermission("omniblock.network.bank"))) {
			sender.sendMessage(NetworkManager.NOT_RECOGNIZED_COMMAND);
			return false;
		}

		if (cmd.getName().equalsIgnoreCase("banco") || cmd.getName().equalsIgnoreCase("dinero")) {

			if (args.length >= 3) {

				if (!Resolver.hasLastName(args[0])) {

					sender.sendMessage(TextUtil.format("&cEl jugador " + args[0] + " no existe!"));
					return true;

				}

				if (args[1].equalsIgnoreCase("dar") || args[1].equalsIgnoreCase("añadir")) {

					int money = NumberUtil.valueOf(args[2]);
					BankBase.addMoney((Player) sender, money);

					sender.sendMessage(TextUtil
							.format("&aLe has añadido &7" + money + " &ade dinero al jugador &7" + args[0] + "&a!"));
					return true;

				}

				if (args[1].equalsIgnoreCase("setear") || args[1].equalsIgnoreCase("elegir")) {

					int money = NumberUtil.valueOf(args[2]);
					BankBase.setMoney((Player) sender, money);

					sender.sendMessage(TextUtil
							.format("&aLe has seteado &7" + money + " &ade dinero al jugador &7" + args[0] + "&a!"));
					return true;

				}

				sender.sendMessage(TextUtil.format("&cNo se ha identificado el argumento &7" + args[1] + "&c!"));
				return true;

			}

			sender.sendMessage(TextUtil.format("&cNo se ha reconocido el argumento &7" + args[0] + "&c!"));
			return true;

		}

		return false;

	}
}
