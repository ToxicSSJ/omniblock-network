package net.omniblock.network.handlers.packets.readers;

import net.omniblock.network.handlers.games.NetworkSaver;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.data.PacketStructure;
import net.omniblock.packets.network.structure.data.PacketStructure.DataType;
import net.omniblock.packets.network.structure.packet.ResposeBoostedGamesPacket;
import net.omniblock.packets.network.tool.object.PacketReader;

public class ServerReaders {

	public static void start() {

		/*
		 * 
		 * Este reader guarda información que es globalizada por el omnicore en
		 * cuanto a los servidores con boosters.
		 * 
		 */
		Packets.READER.registerReader(new PacketReader<ResposeBoostedGamesPacket>() {

			@Override
			public void readPacket(PacketSocketData<ResposeBoostedGamesPacket> packetsocketdata) {

				PacketStructure structure = packetsocketdata.getStructure();

				String data = structure.get(DataType.STRINGS, "boostedgames");

				if (data.length() > 0) {

					if (data.contains(",")) {

						String[] master = data.split(",");

						for (String k : master) {

							if (k.contains("#")) {

								String[] container = k.split("#");

								String gametype = container[0];
								String name = container[1];

								NetworkSaver.ACTIVED_BOOSTERS.put(gametype, name);
								continue;

							}

						}

						return;

					}

					if (data.contains("#")) {

						String[] container = data.split("#");

						String gametype = container[0];
						String name = container[1];

						NetworkSaver.ACTIVED_BOOSTERS.put(gametype, name);
						return;

					}

				}

			}

			@Override
			public Class<ResposeBoostedGamesPacket> getAttachedPacketClass() {
				return ResposeBoostedGamesPacket.class;
			}

		});

	}

}
