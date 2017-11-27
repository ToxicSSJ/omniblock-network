package net.omniblock.network.handlers.games;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import net.omniblock.network.handlers.packets.PacketsTools;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.data.PacketStructure;
import net.omniblock.packets.network.structure.data.PacketStructure.DataType;
import net.omniblock.packets.network.structure.packet.RequestBoostedGamesPacket;
import net.omniblock.packets.network.structure.packet.RequestGamePresetPlayerCountPacket;
import net.omniblock.packets.network.structure.packet.ResposeBoostedGamesPacket;
import net.omniblock.packets.network.structure.packet.ResposeGamePresetPlayerCountPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.network.tool.object.PacketResponder;
import net.omniblock.packets.object.external.GamePreset;
import net.omniblock.packets.object.external.ServerType;

public abstract class NetworkSaver {

	public static Map<ServerType, Integer> SERVER_ONLINE_PLAYERS = new HashMap<ServerType, Integer>();
	public static Map<GamePreset, Integer> GAMES_ONLINE_PLAYERS = new HashMap<GamePreset, Integer>();

	@SuppressWarnings("unlikely-arg-type")
	public static int getPlayers(GamePreset gp) {
		return SERVER_ONLINE_PLAYERS.get(gp);
	}

	public static int getPlayers(ServerType st) {
		return SERVER_ONLINE_PLAYERS.get(st);
	}

	public static void requestBoostedGames() {

		Packets.STREAMER.streamPacketAndRespose(new RequestBoostedGamesPacket().setServername(Bukkit.getServerName())
				.build().setReceiver(PacketSenderType.OMNICORE),

				new PacketResponder<ResposeBoostedGamesPacket>() {

					@Override
					public void readRespose(PacketSocketData<ResposeBoostedGamesPacket> packetsocketdata) {

						PacketStructure structure = packetsocketdata.getStructure();

						PacketsTools.setBoosters(structure.get(DataType.STRINGS, "boostedgames"));
						return;

					}

				});
		return;

	}

	public static void requestServerCount(GamePreset preset) {

		if (SERVER_ONLINE_PLAYERS.size() <= 0) {
			Arrays.asList(ServerType.values()).forEach(type -> SERVER_ONLINE_PLAYERS.put(type, 0));
		}

		Packets.STREAMER
				.streamPacketAndRespose(new RequestGamePresetPlayerCountPacket().setServername(Bukkit.getServerName())
						.setGamepreset(preset).build().setReceiver(PacketSenderType.OMNICORE),

						new PacketResponder<ResposeGamePresetPlayerCountPacket>() {

							@Override
							public void readRespose(
									PacketSocketData<ResposeGamePresetPlayerCountPacket> packetsocketdata) {

								PacketStructure data = packetsocketdata.getStructure();

								GamePreset gamepreset = GamePreset.valueOf(data.get(DataType.STRINGS, "gamepreset"));
								Integer playercount = data.get(DataType.INTEGERS, "playercount");

								GAMES_ONLINE_PLAYERS.put(gamepreset, playercount);

							}

						});
		return;

	}

}
