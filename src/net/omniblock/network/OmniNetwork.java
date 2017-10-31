package net.omniblock.network;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.omniblock.network.handlers.Handlers;
import net.omniblock.network.handlers.base.sql.Database;
import net.omniblock.network.handlers.network.NetworkManager;
import net.omniblock.network.handlers.packets.PacketsAdapter;
import net.omniblock.network.handlers.updater.object.Updatable;
import net.omniblock.network.handlers.updater.type.PluginType;
import net.omniblock.network.library.Libraries;
import net.omniblock.network.library.addons.sshaddon.InternalAdapter;
import net.omniblock.network.systems.AdapterPatcher;
import net.omniblock.packets.OmniPackets;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.socket.Sockets;
import net.omniblock.packets.network.socket.helper.SocketHelper;
import net.omniblock.packets.network.structure.packet.ServerRemoveInfoPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.object.external.GamePreset;
import net.omniblock.packets.object.external.ServerType;
import net.omniblock.packets.object.external.SystemType;

public class OmniNetwork extends JavaPlugin implements Updatable {

	public static Plugin plugin;
	public static OmniNetwork instance;

	public static boolean debugMode = false;

	@Override
	public void onEnable() {

		plugin = this;
		instance = this;

		if (debugMode) {
			Handlers.LOGGER.sendWarning("&d[DEBUG MODE]", "&bOmniNetwork en modo &fDepuración & Prueba&b.");
		}

		InternalAdapter.CONNECTION.setup();
		if (update(PluginType.OMNINETWORK, this))
			return;

		Implements();

	}

	@Override
	public void onDisable() {

		Packets.STREAMER.streamPacket(new ServerRemoveInfoPacket().setServername(Bukkit.getServerName()).build()
				.setReceiver(PacketSenderType.OMNICORE));

	}

	/**
	 * 
	 * Este metodo es el encargado de registrar tanto eventos como comandos o
	 * cualquier tipo de datos que sea necesario manejar de manera externa a los
	 * metodos que trata el plugin como tál.
	 * 
	 * Es decir, es util para inicializar librerias ya sean los registros de los
	 * eventos de dichas librerias o cualquier tipo de inicio (NMS, Packets), y
	 * su segunda función es de implementarle información tipo generica (Osea
	 * información extra o cosas necesarias en cierto parche de forma temporal)
	 * a cualquier variable o clase del plugin.
	 * 
	 */
	public void Implements() {

		Database.makeConnection();

		OmniPackets.setupSystem(SystemType.OMNINETWORK);
		PacketsAdapter.registerReaders();

		Sockets.SERVER.startServer(SocketHelper.getOpenPort());

		AdapterPatcher.setup();
		NetworkManager.start();
		Libraries.start();

	}

	public static OmniNetwork getInstance() {
		return instance;
	}

	public static ServerType getServerTypeByServername(String servername) {
		for (ServerType st : ServerType.values()) {
			if (servername.startsWith(st.getServertypekey())) {
				return st;
			}
		}
		return ServerType.MAIN_LOBBY_SERVER;
	}

	public static GamePreset getGamePresetByServername(String servername) {

		if (NetworkManager.getServertype() == ServerType.SKYWARS_GAME_SERVER) {
			return GamePreset.SKYWARS_MASK;
		}

		return GamePreset.NONE;
	}

	public static boolean isLobbyServer(ServerType type) {
		for (ServerType st : ServerType.values()) {

			if (st == ServerType.MAIN_LOBBY_SERVER) {
				return true;
			}

		}
		return false;
	}

	public static boolean isGameServer(ServerType type) {
		for (ServerType st : ServerType.values()) {

			if (st == ServerType.SKYWARS_GAME_SERVER) {
				return true;
			}

		}
		return false;
	}

	public static boolean isGameLobbyServer(ServerType type) {
		for (ServerType st : ServerType.values()) {

			if (st == ServerType.SKYWARS_LOBBY_SERVER) {
				return true;
			}

		}
		return false;
	}

	public static boolean isStaffServer(ServerType type) {
		return false;
	}

	public static boolean isOtherServer(ServerType type) {
		return false;
	}

}
