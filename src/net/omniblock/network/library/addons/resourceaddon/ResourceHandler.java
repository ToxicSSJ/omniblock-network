package net.omniblock.network.library.addons.resourceaddon;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.omniblock.network.library.addons.resourceaddon.api.RPApiPlugin;
import net.omniblock.network.library.addons.resourceaddon.api.ResourcePackAPI;
import net.omniblock.network.library.addons.resourceaddon.type.ResourceType;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.data.PacketStructure;
import net.omniblock.packets.network.structure.data.PacketStructure.DataType;
import net.omniblock.packets.network.structure.packet.RequestTexturepackPacket;
import net.omniblock.packets.network.structure.packet.ResposeTexturepackPacket;
import net.omniblock.packets.network.structure.packet.PlayerSendTexturepackPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.network.tool.object.PacketResponder;

public class ResourceHandler {

	public static void setup() {
		RPApiPlugin.setup();
	}

	/**
	 * 
	 * - Este metodo no debe usarse debido a que es el metodo manual de
	 * insercción de paquete de texturas.
	 * 
	 * @param player
	 *            El jugador que se le seteará el resource pack.
	 * @param type
	 *            El tipo de resource pack que se enviará.
	 * @deprecated
	 */
	public static void setResourcePack(Player player, ResourceType type) {
		ResourcePackAPI.setResourcepack(player, type.getPack().getUrl(), type.getPack().getHash());
		return;
	}

	/**
	 * 
	 * - Este metodo es el que debe usarse y funciona en base a la conexión por
	 * paquetes entre OmniCore y OmniCord. Está acutalizado y funciona perfecto
	 * para la no repetición del paquete de texturas.
	 * 
	 * @param player
	 *            El jugador que se le seteará el resource pack.
	 * @param type
	 *            El tipo de resource pack que se enviará.
	 */
	public static void sendResourcePack(Player player, ResourceType type) {

		Packets.STREAMER.streamPacket(new PlayerSendTexturepackPacket().setPlayername(player.getName())
				.setTexturehash(type.getPack().getName()).build().setReceiver(PacketSenderType.OMNICORD));

	}

	/**
	 * 
	 * - Este metodo proporciona mediante un paquete de información del paquete
	 * actual que posee el jugador, elegír un paquete de texturas al jugador si
	 * el que el posee en el OmniCord no es el que se está solicitando.
	 * 
	 * @param player
	 *            El jugador que se le seteará el resource pack.
	 * @param type
	 *            El tipo de resource pack que se enviará.
	 */
	public static void requestResourcePack(Player player, ResourceType type) {

		Packets.STREAMER.streamPacketAndRespose(
				new RequestTexturepackPacket().setServername(Bukkit.getServerName()).setPlayername(player.getName())
						.build().setReceiver(PacketSenderType.OMNICORD),
				new PacketResponder<ResposeTexturepackPacket>() {

					@Override
					public void readRespose(PacketSocketData<ResposeTexturepackPacket> packetsocketdata) {

						PacketStructure structure = packetsocketdata.getStructure();

						String resourcename = structure.get(DataType.STRINGS, "resourcetype");

						ResourceType cachetype = ResourceType.getByName(resourcename);

						if (type != cachetype) {

							setResourcePack(player, type);
							return;

						}

					}

				});

	}

}
