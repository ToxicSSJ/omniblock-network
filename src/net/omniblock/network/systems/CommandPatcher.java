package net.omniblock.network.systems;

import org.bukkit.command.CommandExecutor;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.ban.BanManager;
import net.omniblock.network.systems.bank.BankManager;
import net.omniblock.network.systems.kick.KickManager;

public class CommandPatcher {

	public static final String BAR = TextUtil.getCenteredMessage("&8&l&m=============================================");

	public static void setup() {

		CommandExecutor BAN_EXECUTOR = new BanManager();

		OmniNetwork.getInstance().getCommand("ban").setExecutor(BAN_EXECUTOR);
		OmniNetwork.getInstance().getCommand("banear").setExecutor(BAN_EXECUTOR);

		CommandExecutor KICK_EXECUTOR = new KickManager();

		OmniNetwork.getInstance().getCommand("kick").setExecutor(KICK_EXECUTOR);
		OmniNetwork.getInstance().getCommand("kickear").setExecutor(KICK_EXECUTOR);

		CommandExecutor BANK_EXECUTOR = new BankManager();

		OmniNetwork.getInstance().getCommand("banco").setExecutor(BANK_EXECUTOR);
		OmniNetwork.getInstance().getCommand("dinero").setExecutor(BANK_EXECUTOR);

	}

}
