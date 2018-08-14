package net.omniblock.network.handlers.logger;

import org.bukkit.Bukkit;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.library.utils.TextUtil;

@Deprecated
public class LoggerHandler {

	public void sendLoggerInfo(String... lines) {
		for (String line : lines) {
			OmniNetwork.getInstance().getLogger().info(line);
		}
	}

	public void sendLoggerWarn(String... lines) {
		for (String line : lines) {
			OmniNetwork.getInstance().getLogger().warning(line);
		}
	}

	public void sendLoggerSevere(String... lines) {
		for (String line : lines) {
			OmniNetwork.getInstance().getLogger().severe(line);
		}
	}

	public void sendMessageToConsole(String... lines) {
		sendMessageToConsole(true, lines);
	}

	public void sendMessageToConsole(boolean simplePrefix, String... lines) {
		for (String line : lines) {
			if (simplePrefix) {
				Bukkit.getConsoleSender().sendMessage(TextUtil.format("&7[!] &f" + line));
			} else {
				Bukkit.getConsoleSender().sendMessage(TextUtil.format(line));
			}
		}
	}

	public void sendInfo(String... lines) {
		sendInfo(true, lines);
	}

	public void sendInfo(boolean simplePrefix, String... lines) {
		for (String line : lines) {
			sendMessageToConsole(simplePrefix, "&bINFO &7: &f" + line);
		}
	}

	public void sendModuleInfo(String... lines) {
		for (String line : lines) {
			sendMessageToConsole(true, "&aMODULOS &7: &f" + line);
		}
	}

	public void sendModuleMessage(String modulename, String... lines) {
		for (String line : lines) {
			sendMessageToConsole(true, "&aMODULOS &8'" + modulename + "' " + "&7: &f" + line);
		}
	}

	public void sendDebug(String... lines) {
		for (String line : lines) {
			sendMessageToConsole(true, "&dDEBUG &7: &f" + line);
		}
	}

	public void sendWarning(String... lines) {
		sendWarning(true, lines);
	}

	public void sendWarning(boolean simplePrefix, String... lines) {
		for (String line : lines) {
			sendMessageToConsole(simplePrefix, "&eADVERTENCIA &7: &f" + line);
		}
	}

	public void sendError(String... lines) {
		sendError(true, lines);
	}

	public void sendError(boolean simplePrefix, String... lines) {
		for (String line : lines) {
			sendMessageToConsole(simplePrefix, "&cERROR &7: &f" + line);
		}
	}

}
