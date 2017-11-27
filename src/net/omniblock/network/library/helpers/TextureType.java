package net.omniblock.network.library.helpers;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;

public enum TextureType {

	OMNIBLOCK_DEFAULT(
			
			"OMNIBLOCKDEF",
			"http://omniblock.net/gameserver/DEFAULT.zip",
			"1798caf219d9eaaf7d33e61053a49a32988ae9eb",
			1
			
			),
	
	SKYWARS_Z_PACK(
			
			"SKWZ",
			"http://omniblock.net/gameserver/SKWZvC1.zip",
			"d4a6b3cf9b72fe2d63adbf29fcb101f9f9c362f4",
			1
			
			);
	
	;
	
	private String name;
	
	private String url;
	private String hash;
	
	private int format;
	
	TextureType(String name, String url, String hash, int format){
		
		this.name = name;
		this.url = url;
		this.hash = hash;
		this.format = format;
		
	}

	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getHash() {
		return hash;
	}
	
	public int getFormat() {
		return format;
	}
	
	public void sendPack(Player player){
		
		System.out.println("testing... " + url);
		
		ProtocolManager manager = ProtocolLibrary.getProtocolManager();;
		
		player.setResourcePack("[url]" + url + "[/url]");
		
		PacketContainer resourcePackSend = manager.createPacket(PacketType.Play.Server.RESOURCE_PACK_SEND);
		resourcePackSend.getStrings().write(0, url).write(1, hash);
		
		try {
			manager.sendServerPacket(player, resourcePackSend);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	public static TextureType getFromName(String name) {
		
		for(TextureType type : TextureType.values()) {
			
			if(type.getName().equalsIgnoreCase(name)) {
				
				return type;
				
			}
			
		}
		
		return null;
		
	}
	
}
