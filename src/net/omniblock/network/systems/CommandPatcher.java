package net.omniblock.network.systems;

import org.bukkit.command.CommandExecutor;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.bank.BankManager;
import net.omniblock.network.systems.rank.RankManager;

public class CommandPatcher {

	public static final String BAR = TextUtil.getCenteredMessage("&8&l&m=============================================");

	public static void setup() {

		CommandExecutor BANK_EXECUTOR = new BankManager();

		OmniNetwork.getInstance().getCommand("banco").setExecutor(BANK_EXECUTOR);
		OmniNetwork.getInstance().getCommand("dinero").setExecutor(BANK_EXECUTOR);

		CommandExecutor RANK_EXECUTOR = new RankManager();

		OmniNetwork.getInstance().getCommand("rango").setExecutor(RANK_EXECUTOR);
		OmniNetwork.getInstance().getCommand("rank").setExecutor(RANK_EXECUTOR);
		
	}

}
