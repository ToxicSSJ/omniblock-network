package net.omniblock.network.systems;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.omniblock.network.OmniNetwork;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.socket.adapter.ServerSocketAdapter;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.data.PacketStructure.DataType;
import net.omniblock.packets.network.structure.packet.RequestActionExecutorPacket;
import net.omniblock.packets.network.structure.packet.ResposeActionExecutorPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.network.tool.object.PacketResponder;

public class InformationCenterPatcher {

	protected static Map<Information, String> last_data = new HashMap<Information, String>();
	protected static Map<AutoInformation, BukkitTask> tasks_data = new HashMap<AutoInformation, BukkitTask>();
	
	public static Information registerAutoInformation(Information information, String[] args, int delay, int ticks) {
		
		AutoInformation autoinformation = new AutoInformation(information, args, delay, ticks);
		tasks_data.put(autoinformation, autoinformation.makeAutoInfo());
		return information;
		
	}
	
	public static String getLastInformation(Information information) {
		
		for(Map.Entry<Information, String> data : last_data.entrySet()) {
			
			if(data.getKey().getReference().equalsIgnoreCase(information.getReference()))
				if(data.getKey().getType() == information.getType())
					return data.getValue();
			
		}
		
		
		
		return "none";
		
	}
	
	private static void renewInformation(Information information, String data) {
		last_data.put(information, data);
	}
	
	public static class AutoInformation {
		
		private Information information;
		private String[] args;
		
		private int delay, ticks;
		
		public AutoInformation(Information information, String[] args, int delay, int ticks) {
			
			this.information = information;
			this.args = args;
			
			this.delay = delay;
			this.ticks = ticks;
			
		}

		public BukkitTask makeAutoInfo() {
			
			return new BukkitRunnable() {

				@Override
				public void run() {
					
					Packets.STREAMER.streamPacketAndRespose(
							new RequestActionExecutorPacket()
								.setRequestAction(information.getType().getRequest())
								.setRequesterPort(ServerSocketAdapter.serverPort)
								.setArgs(args)
								.build().setReceiver(information.getType().getUnpackager().getReceiver()),
								
							new PacketResponder<ResposeActionExecutorPacket>() {

								@Override
								public void readRespose(PacketSocketData<ResposeActionExecutorPacket> packetsocketdata) {
									
									String response = packetsocketdata.getStructure().get(DataType.STRINGS, "response");
									renewInformation(information, information.getType().getUnpackager().unpackData(response));
									return;
									
								}
								
							});
					
				}
				
			}.runTaskTimer(OmniNetwork.getInstance(), delay, ticks);
			
		}
		
		public Information getInformation() {
			return information;
		}

		public String[] getArgs() {
			return args;
		}

		public int getDelay() {
			return delay;
		}

		public int getTicks() {
			return ticks;
		}
		
	}
	
	public static class Information {
		
		private InformationType type;
		private String reference;
		
		public Information(InformationType type, String reference) {
			
			this.type = type;
			this.reference = reference;
			
		}
		
		public InformationType getType() {
			return type;
		}

		public String getReference() {
			return reference;
		}
		
	}
	
	public static enum InformationType {
		
		SKYWARS_Z("skywarsrequest", new InformationUnpackager() {

			@Override
			public String unpackData(String response) {
				return response;
			}
			
			@Override
			public PacketSenderType getReceiver() {
				return PacketSenderType.OMNICORE;
			}
			
		}),
		
		NETWORK_BOOSTER("networkboosterrequest", new InformationUnpackager() {

			@Override
			public String unpackData(String response) {
				
				if(response.length() < 1)
					return "none";
				
				return response;
				
			}

			@Override
			public PacketSenderType getReceiver() {
				return PacketSenderType.OMNICORD;
			}
			
		}),
		
		;
		
		private String request;
		private InformationUnpackager unpackager;
		
		InformationType(String request, InformationUnpackager unpackager){
			
			this.request = request;
			this.unpackager = unpackager;
			
		}

		public String getRequest() {
			return request;
		}
		
		public InformationUnpackager getUnpackager() {
			return unpackager;
		}

		public static interface InformationUnpackager {
			
			public String unpackData(String response);
			
			public PacketSenderType getReceiver();
			
		}
		
	}
	
}
