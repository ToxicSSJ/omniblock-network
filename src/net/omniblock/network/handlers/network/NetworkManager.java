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
	private static ServerType servertype = ServerType.MAIN_LOBBY_SERVER;
	private static GamePreset gamepreset = GamePreset.NONE;

	private static Date serverDate;
	private static Calendar serverCalendar;

	/**
	 * Estos metodos dan inicio y comprobación al estado del canal de datos.
	 */
	public static void start() {

		Calendar time = Calendar.getInstance();
		time.add(Calendar.MILLISECOND, -time.getTimeZone().getOffset(time.getTimeInMillis()));
		setServerDate(time.getTime());
		setServerCalendar(time);

		servertype = OmniNetwork.getServerTypeByServername(Bukkit.getServerName());
		gamepreset = OmniNetwork.getGamePresetByServername(Bukkit.getServerName());

		new BukkitRunnable() {
			@Override
			public void run() {

				Handlers.LOGGER
						.sendInfo(TextUtil.format("&7Se ha detectado este servidor como: " + servertype.toString()));

				Packets.STREAMER.streamPacket(new ServerStructureInfoPacket()

						.setServername(Bukkit.getServerName()).setServerport(Bukkit.getPort())
						.setSocketport(ServerSocketAdapter.serverPort).setServertype(servertype)

						.setOnlineplayers(Bukkit.getOnlinePlayers().size()).setMaxplayers(Bukkit.getMaxPlayers())
						.setMapname(Bukkit.getWorlds().get(0).getName()).setInfo("")

						.setFull(false).setLobbyserver(OmniNetwork.isLobbyServer(NetworkManager.getServertype()))
						.setGameserver(OmniNetwork.isGameServer(NetworkManager.getServertype()))
						.setGamelobbyserver(OmniNetwork.isGameLobbyServer(NetworkManager.getServertype()))
						.setStaffserver(OmniNetwork.isStaffServer(NetworkManager.getServertype()))
						.setOtherserver(OmniNetwork.isOtherServer(NetworkManager.getServertype())).build()
						.setReceiver(PacketSenderType.OMNICORE));

			}
		}.runTaskLater(OmniNetwork.getInstance(), 1L);

		if (gamepreset != GamePreset.NONE) {

			new BukkitRunnable() {
				@Override
				public void run() {

					Handlers.LOGGER.sendInfo(TextUtil
							.format("&7Se ha detectado un minijuego en este servidor: " + gamepreset.toString()));

					Packets.STREAMER.streamPacket(new GameStructureInfoPacket()
							.setServername(Bukkit.getServerName())
							.setGamepreset(gamepreset)

							.setMinimiumplayers(2)
							.setMaximiumplayers(12)

							.setOnlineplayers(Bukkit.getOnlinePlayers() != null ? Bukkit.getOnlinePlayers().size() : 0)

							.setMapname("OmniMundo").setExtrainfo("")

							.setLocked(false).setStarted(false).setReload(false).setNext(false).build()
							.setReceiver(PacketSenderType.OMNICORE));

				}
			}.runTaskLater(OmniNetwork.getInstance(), 20 * 2);

		}

	}

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
