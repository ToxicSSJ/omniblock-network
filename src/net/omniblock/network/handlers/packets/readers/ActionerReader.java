package net.omniblock.network.handlers.packets.readers;

import net.omniblock.network.handlers.packets.PacketsTools;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.data.PacketStructure;
import net.omniblock.packets.network.structure.data.PacketStructure.DataType;
import net.omniblock.packets.network.structure.packet.RequestActionExecutorPacket;
import net.omniblock.packets.network.structure.packet.ResposeActionExecutorPacket;
import net.omniblock.packets.network.tool.object.PacketReader;

public class ActionerReader {


	public static void start() {
		
		Packets.READER.registerReader(new PacketReader<RequestActionExecutorPacket>(){

			@Override
			public void readPacket(PacketSocketData<RequestActionExecutorPacket> packetsocketdata) {
				
				PacketStructure structure = packetsocketdata.getStructure();
				
				String requestaction = structure.get(DataType.STRINGS, "requestaction");
				String[] args = ((String) structure.get(DataType.STRINGS, "args")).split(",");
				
				Integer requesterport = structure.get(DataType.INTEGERS, "requesterport");
				
				for(ActionExecutorType type : ActionExecutorType.values()) {
					
					if(type.getRequest().equalsIgnoreCase(requestaction)) {
						
						Packets.STREAMER.streamPacket(
								type.getExecutor().execute(args).build()
									.setPacketUUID(packetsocketdata.getPacketUUID())
									.setReceiver(requesterport));
						return;
						
					}
					
				}
				
				return;
				
			}

			@Override
			public Class<RequestActionExecutorPacket> getAttachedPacketClass() {
				return RequestActionExecutorPacket.class;
			}
			
		});
		
	}
	
	public static enum ActionExecutorType {
		
		REGISTER_SERVER("registerrequest", 0, new ActionExecutor() {

			@Override
			public ResposeActionExecutorPacket execute(String[] args) {
				
				System.out.println("test registerrequest of actionerreader!");
				
				PacketsTools.sendRegisterInfo();
				
				return new ResposeActionExecutorPacket()
						.setResponse("Se ha registrado el servidor correctamente!");
				
			}
			
		}),
		
		STATUS("statusrequest", 0, new ActionExecutor() {

			@Override
			public ResposeActionExecutorPacket execute(String[] args) {
				
				System.out.println("test statusrequest of actionerreader!");
				
				return new ResposeActionExecutorPacket()
						.setResponse("ONLINE");
				
			}
			
		})
		
		;
		
		private String request;
		private int argslength;
		
		private ActionExecutor executor;
		
		ActionExecutorType(String request, int argslength, ActionExecutor executor){
			
			this.request = request;
			
			this.argslength = argslength;
			this.executor = executor;
			
		}
		
		public ActionExecutor getExecutor() {
			return executor;
		}
		
		public String getRequest() {
			return request;
		}
		
		public int getArgsLength() {
			return argslength;
		}
		
		public static interface ActionExecutor {
			
			public ResposeActionExecutorPacket execute(String[] args);
			
		}
		
	}
	
}
