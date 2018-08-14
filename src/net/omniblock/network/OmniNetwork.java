package net.omniblock.network;

import net.omniblock.network.library.addons.configaddon.Factory;
import net.omniblock.packets.network.structure.packet.RemoveSystemServerPacket;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.omniblock.network.handlers.Handlers;
import net.omniblock.network.handlers.base.sql.Database;
import net.omniblock.network.systems.AdapterPatcher;
import net.omniblock.packets.OmniPackets;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.socket.Sockets;
import net.omniblock.packets.network.socket.helper.SocketHelper;
import net.omniblock.packets.network.structure.packet.ServerRemoveInfoPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.object.external.ServerType;
import net.omniblock.packets.object.external.SystemType;

@Deprecated
public class OmniNetwork extends JavaPlugin {

	public static Plugin plugin;
	public static OmniNetwork instance;

	public static boolean debugMode = true;

	@Override
	public void onEnable() {

		plugin = this;
		instance = this;

		Handlers.LOGGER.sendWarning(
				"####################################",
				"OmniNetwork Está siendo eliminado poco a poco",
				"NO UTILICE ESTE PLUGIN COMO DEPENDENCIA YA QUE SUS",
				"METODOS SERÁN ELIMINADOS.",
				"####################################");

		if (debugMode)
			Handlers.LOGGER.sendWarning("&d[DEBUG MODE]", "&bOmniNetwork en modo &fDepuración & Prueba&b.");

		Implements();

	}

	@Override
	public void onDisable() {}

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

		Bukkit.getPluginManager().registerEvents(new SingleListener(), this);

		Database.makeConnection();
		OmniPackets.setupSystem(SystemType.OMNINETWORK);

		AdapterPatcher.setup();

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

}
