package net.omniblock.network.handlers.packets.readers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.handlers.network.NetworkManager;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.packet.PlayerSendToServerPacket;
import net.omniblock.packets.network.structure.packet.ResposeReloadInfoPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.network.tool.object.PacketReader;
import net.omniblock.packets.object.external.ServerType;

public class OtherReaders {

	public static void start() {

		System.out.println("registering reader!");
		
		/*
		 * 
		 * El siguiente reader es el que se encarga de recibir la respuesta del
		 * paquete de reloadeo que generalmente es enviada por el OmniCord.
		 * 
		 */
		Packets.READER.registerReader(new PacketReader<ResposeReloadInfoPacket>() {

			@Override
			public void readPacket(PacketSocketData<ResposeReloadInfoPacket> packetsocketdata) {

				if(NetworkManager.getServertype() != ServerType.MAIN_AUTH_SERVER) {
					
					for (Player p : Bukkit.getOnlinePlayers()) {

						Packets.STREAMER
								.streamPacket(new PlayerSendToServerPacket().setServertype(ServerType.MAIN_LOBBY_SERVER)
										.setPlayername(p.getName()).build().setReceiver(PacketSenderType.OMNICORE));
						continue;

					}
					
				}

				new BukkitRunnable() {

					@Override
					public void run() {

						if (Bukkit.getOnlinePlayers().size() <= 0) {

							cancel();
							Bukkit.getServer().shutdown();
							return;

						} else {

							for (Player p : Bukkit.getOnlinePlayers()) {

								p.kickPlayer(TextUtil.format("&6¡Se están relodeando los Servidores! \n "
										+ "&7Se te ha enviado ha expulsado mientras se hace dicho proceso."));
								continue;

							}

						}

					}

				}.runTaskTimer(OmniNetwork.getInstance(), 20L, 2L);

			}

			@Override
			public Class<ResposeReloadInfoPacket> getAttachedPacketClass() {
				return ResposeReloadInfoPacket.class;
			}

		});

	}

}
