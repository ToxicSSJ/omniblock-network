/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.network.handlers.network;

import java.util.Calendar;
import java.util.Date;

import net.omniblock.packets.network.structure.packet.RegisterSystemServerPacket;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.handlers.Handlers;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.socket.adapter.ServerSocketAdapter;
import net.omniblock.packets.network.structure.packet.GameStructureInfoPacket;
import net.omniblock.packets.network.structure.packet.ServerStructureInfoPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.object.external.GamePreset;
import net.omniblock.packets.object.external.ServerType;

@Deprecated
public class NetworkManager {

	/**
	 * Mensajes guardados que serán utilizados dependiendo el caso.
	 */

	public static final String NOT_RECOGNIZED_COMMAND = TextUtil.format(
			"&8&lC&8omandos &c» &7Comando desconocido!");

	/**
	 * Sección de variables que utiliza el NetworkManager para la comunicación
	 * entre serv - bungeecord - serv.
	 */
	private static ServerType servertype = ServerType.SURVIVAL;
	private static GamePreset gamepreset = GamePreset.NONE;

	private static Date serverDate;
	private static Calendar serverCalendar;

	/**
	 * Estos metodos dan inicio y comprobación al estado del canal de datos.
	 */
	@Deprecated
	public static void start() {}

	public static Date getServerDate() {
		return serverDate;
	}

	public static void setServerDate(Date serverDate) {
		NetworkManager.serverDate = serverDate;
	}

	public static Calendar getServerCalendar() {
		return serverCalendar;
	}

	public static void setServerCalendar(Calendar serverCalendar) {
		NetworkManager.serverCalendar = serverCalendar;
	}

	public static GamePreset getGamepreset() {
		return gamepreset;
	}

	public static void setGamepreset(GamePreset gamepreset) {
		NetworkManager.gamepreset = gamepreset;
	}

	public static ServerType getServertype() {
		return servertype;
	}

	public static void setServertype(ServerType servertype) {
		NetworkManager.servertype = servertype;
	}

}
