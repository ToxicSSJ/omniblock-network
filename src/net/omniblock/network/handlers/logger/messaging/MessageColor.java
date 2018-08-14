package net.omniblock.network.handlers.logger.messaging;

import org.bukkit.ChatColor;

@Deprecated
public enum MessageColor {

	GENERAL_TEXT_COLOR(ChatColor.GRAY),
	SFTP_COLOR(ChatColor.LIGHT_PURPLE),
	DATABASE_COLOR(ChatColor.AQUA),
	MODULES_COLOR(ChatColor.GREEN),
	ERROR_COLOR(ChatColor.RED),

	;

	private ChatColor color;

	MessageColor(ChatColor color) {

		this.color = color;

	}

	public ChatColor getColor() {
		return color;
	}

}
