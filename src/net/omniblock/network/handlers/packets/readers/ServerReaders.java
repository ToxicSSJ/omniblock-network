package net.omniblock.network.handlers.packets.readers;

import net.omniblock.network.handlers.packets.PacketsTools;
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

				PacketsTools.setBoosters(structure.get(DataType.STRINGS, "boostedgames"));

			}

			@Override
			public Class<ResposeBoostedGamesPacket> getAttachedPacketClass() {
				return ResposeBoostedGamesPacket.class;
			}

		});

	}

}
