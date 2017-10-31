/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.network.library.utils;

import org.bukkit.ChatColor;
import org.bukkit.map.MinecraftFont;

public class TextUtil {

	private final static int CENTER_PX = 154;

	public static String format(String s) {
		return s.replaceAll("&", String.valueOf(ChatColor.COLOR_CHAR));
	}

	public static String replaceAllTextWith(String text, char replace) {
		char chArray[] = text.toCharArray();
		for (int i = 0; i < chArray.length; i++) {
			chArray[i] = replace;
		}

		return String.valueOf(chArray);
	}

	public static String getCenteredMessage(String message) {

		return getCenteredMessage(message, false);

	}

	@SuppressWarnings("static-access")
	public static String getCenteredMessage(String message, boolean old) {
		if (message == null || message.equals(""))
			message = format(message);

		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (char c : message.toCharArray()) {
			if (c == '§') {
				previousCode = true;
				continue;
			} else if (previousCode == true) {
				previousCode = false;
				if (c == 'l' || c == 'L') {
					isBold = true;
					continue;
				} else
					isBold = false;
			} else {

				if (!old) {

					messagePxSize += isBold ? MinecraftFont.Font.getWidth(new String().valueOf(c)) + 1
							: MinecraftFont.Font.getWidth(new String().valueOf(c));
					messagePxSize++;

				} else {

					DefaultFontInfoUtil dFI = DefaultFontInfoUtil.getDefaultFontInfo(c);
					messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
					messagePxSize++;

				}
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		int toCompensate = CENTER_PX - halvedMessageSize;
		int spaceLength = 4;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();
		while (compensated < toCompensate) {
			sb.append(" ");
			compensated += spaceLength;
		}
		return TextUtil.format(sb.toString() + message);
	}

}
