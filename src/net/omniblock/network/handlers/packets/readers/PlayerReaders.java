package net.omniblock.network.handlers.packets.readers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import net.omniblock.network.handlers.base.bases.type.AccountBase;
import net.omniblock.network.handlers.base.bases.type.BoosterBase;
import net.omniblock.network.handlers.packets.PacketsTools;
import net.omniblock.network.library.helpers.ItemBuilder;
import net.omniblock.network.library.helpers.RandomFirework;
import net.omniblock.network.library.helpers.TextureType;
import net.omniblock.network.library.helpers.inventory.InventoryBuilder;
import net.omniblock.network.library.helpers.inventory.InventoryBuilder.Action;
import net.omniblock.network.library.helpers.inventory.InventoryBuilder.RowsIntegers;
import net.omniblock.network.library.utils.NumberUtil;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.account.AccountManager.AccountBoosterType;
import net.omniblock.network.systems.account.items.NetworkBoosterType;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.data.PacketStructure;
import net.omniblock.packets.network.structure.data.PacketStructure.DataType;
import net.omniblock.packets.network.structure.packet.PlayerSendTexturepackPacket;
import net.omniblock.packets.network.structure.packet.PlayerSendToNamedServerPacket;
import net.omniblock.packets.network.structure.packet.ResposeGamePartyInfoPacket;
import net.omniblock.packets.network.structure.packet.ResposePlayerGameLobbiesPacket;
import net.omniblock.packets.network.structure.packet.ResposePlayerNetworkBoosterPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.network.tool.object.PacketReader;

public class PlayerReaders {

	public static void start() {
		
		/*
		 * 
		 * Con este reader se puede elegir el texturepack de un jugador en base
		 * a la información enviada.
		 * 
		 */
		Packets.READER.registerReader(new PacketReader<PlayerSendTexturepackPacket>(){

			@Override
			public void readPacket(PacketSocketData<PlayerSendTexturepackPacket> packetsocketdata) {
				
				PacketStructure structure = packetsocketdata.getStructure();
				
				String playername = structure.get(DataType.STRINGS, "playername");
				String texturehash = structure.get(DataType.STRINGS, "texturehash");
				
				TextureType.getFromName(texturehash).sendPack(Bukkit.getPlayer(playername));
				return;
				
			}

			@Override
			public Class<PlayerSendTexturepackPacket> getAttachedPacketClass() {
				return PlayerSendTexturepackPacket.class;
			}
			
		});
		
		/*
		 * 
		 * Este reader es el que se encarga de ejecutar el metodo de
		 * reconocimiento de parties para modalidades tipo teams.
		 * 
		 */
		Packets.READER.registerReader(new PacketReader<ResposeGamePartyInfoPacket>() {

			@Override
			public void readPacket(PacketSocketData<ResposeGamePartyInfoPacket> packetsocketdata) {

				PacketStructure data = packetsocketdata.getStructure();

				String players = data.get(DataType.STRINGS, "players");

				if (Bukkit.getPluginManager().isPluginEnabled("Skywars")) {
					PacketsTools.reflectionSkywarsExecute("$ TEAM " + players);
				}

			}

			@Override
			public Class<ResposeGamePartyInfoPacket> getAttachedPacketClass() {
				return ResposeGamePartyInfoPacket.class;
			}

		});

		/*
		 * 
		 * Este reader es el encargado de recibir la respuesta positiva del
		 * sistema al intentar activar un booster, de tal manera que es activado
		 * y se generan sus mensajes y efectos de activación.
		 * 
		 */
		Packets.READER.registerReader(new PacketReader<ResposePlayerNetworkBoosterPacket>() {

			@Override
			public void readPacket(PacketSocketData<ResposePlayerNetworkBoosterPacket> packetsocketdata) {

				PacketStructure data = packetsocketdata.getStructure();

				String gametype = data.get(DataType.STRINGS, "gametype");
				String name = data.get(DataType.STRINGS, "playername");
				String key = data.get(DataType.STRINGS, "key");

				AccountBase.removeTag(name, key);

				BoosterBase.removeEnabledBooster(name, AccountBoosterType.NETWORK_BOOSTER);
				BoosterBase.startBooster(name, key, AccountBoosterType.NETWORK_BOOSTER, gametype);

				for (Player player : Bukkit.getOnlinePlayers()) {

					if (player.getName() == name) {

						int x = 0;
						while (x < 5) {
							RandomFirework.spawnRandomFirework(player.getLocation());
							x++;
						}

						player.sendMessage(
								TextUtil.format(TextUtil.getCenteredMessage("&a&m--------------------------------")));
						player.sendMessage(
								TextUtil.format(TextUtil.getCenteredMessage("&7¡Has activado un booster global!")));
						player.sendMessage(TextUtil.format(TextUtil.getCenteredMessage("")));
						player.sendMessage(TextUtil.format(TextUtil
								.getCenteredMessage("&8Nombre: &b" + NetworkBoosterType.fromKey(key).getName())));
						player.sendMessage(TextUtil.format(TextUtil.getCenteredMessage("&8Modalidad: &b" + gametype)));
						player.sendMessage(TextUtil.format(TextUtil.getCenteredMessage("&8Finalizará: &b"
								+ BoosterBase.parseExpireDate(NetworkBoosterType.fromKey(key).getEndDate()))));
						player.sendMessage(
								TextUtil.format(TextUtil.getCenteredMessage("&a&m--------------------------------")));

					}

				}

			}

			@Override
			public Class<ResposePlayerNetworkBoosterPacket> getAttachedPacketClass() {
				return ResposePlayerNetworkBoosterPacket.class;
			}

		});

		/*
		 * 
		 * Este reader es el encargado de abrirle una gui al usuario con los
		 * datos de los servidores tipo lobby que el usuario solicitó con el
		 * paquete de solicitud.
		 * 
		 */
		Packets.READER.registerReader(new PacketReader<ResposePlayerGameLobbiesPacket>() {

			@Override
			public void readPacket(PacketSocketData<ResposePlayerGameLobbiesPacket> packetsocketdata) {

				PacketStructure data = packetsocketdata.getStructure();

				String name = data.get(DataType.STRINGS, "playername");
				String servers = data.get(DataType.STRINGS, "servers");

				if (Bukkit.getPlayer(name) != null) {

					Map<String, Integer> SERVERS_UNPACK = new HashMap<String, Integer>();

					if (servers.contains(",")) {

						for (String k : servers.split(",")) {

							Integer players = NumberUtil.valueOf(k.split("*")[1]);
							SERVERS_UNPACK.put(k.split("*")[0], players);
							continue;

						}

					} else {

						SERVERS_UNPACK.put(servers.split("\\*")[0], NumberUtil.valueOf(servers.split("\\*")[1]));

					}

					InventoryBuilder ib = new InventoryBuilder(TextUtil.format("&b&lLobbies Disponibles"), 9 * 6, true);

					ib.fill(new ItemBuilder(Material.STAINED_GLASS_PANE).durability((short) 15)
							.name(TextUtil.format("&8-")).hideAtributes().build(), RowsIntegers.ROW_1);
					ib.fill(new ItemBuilder(Material.STAINED_GLASS_PANE).durability((short) 15)
							.name(TextUtil.format("&8-")).hideAtributes().build(), RowsIntegers.ROW_6);

					int i = 9;

					for (Map.Entry<String, Integer> k : SERVERS_UNPACK.entrySet()) {

						if (i >= 45)
							break;

						String server = k.getKey();
						Integer players = k.getValue();

						ib.addItem(new ItemBuilder(server.equalsIgnoreCase(Bukkit.getServerName())
								? Material.SLIME_BLOCK : Material.STAINED_GLASS).durability((short) 0)
										.name(TextUtil.format("&f" + server)).lore("")
										.lore(TextUtil
												.format("&7Estado: " + (players <= 99 ? "&a&lONLINE" : "&e&lLLENO")))
										.lore(TextUtil.format("&7Jugadores: &f" + players)).lore("")
										.lore(TextUtil.format(server.equalsIgnoreCase(Bukkit.getServerName())
												? "&6(Ya te encuentras aquí)"
												: players <= 99 ? "&a(Click para entrar)" : "&c(Cerrado)"))
										.hideAtributes().build(),
								i, new Action() {

									@Override
									public void click(ClickType click, Player player) {

										player.closeInventory();

										Packets.STREAMER.streamPacket(new PlayerSendToNamedServerPacket()
												.setServername(server).setPlayername(player.getName()).build()
												.setReceiver(PacketSenderType.OMNICORD));

										return;

									}

								});

						i++;
						continue;

					}

					ib.open(Bukkit.getPlayer(name));
					return;

				}

			}

			@Override
			public Class<ResposePlayerGameLobbiesPacket> getAttachedPacketClass() {
				return ResposePlayerGameLobbiesPacket.class;
			}

		});

	}

}
