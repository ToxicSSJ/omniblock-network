package net.omniblock.network.library.addons.resourceaddon.type;

import org.bukkit.entity.Player;

import com.google.common.io.BaseEncoding;

import net.omniblock.network.library.addons.resourceaddon.object.ResourcePack;

public enum ResourceType {

	OMNIBLOCK_DEFAULT(new ResourcePack(

			"OMNIBLOCKDEF", "http://download1645.mediafire.com/kjocn4144whg/45r35w628sfid24/DEFAULT.zip",
			"1798caf219d9eaaf7d33e61053a49a32988ae9eb"

	)),

	SKYWARS_Z_PACK(new ResourcePack(

			"SKWZ", "http://download1595.mediafire.com/85mv8upvkkyg/gl524rvbwza8ih7/SKWZK8.zip",
			"b73fe8d443a0ff11fff36991689abd460b29d1d"

	));

	;

	private ResourcePack pack;

	ResourceType(ResourcePack pack) {
		this.pack = pack;
	}

	public ResourcePack getPack() {
		return pack;
	}

	public String getHash() {
		return BaseEncoding.base16().lowerCase().encode(new String(pack.getHash()).getBytes());
	}

	public void sendPack(Player player) {

		pack.send(player);
		return;

	}

	public static ResourceType getByName(String name) {

		for (ResourceType type : ResourceType.values()) {

			if (type.getPack().getName().equalsIgnoreCase(name))
				return type;

		}

		return ResourceType.OMNIBLOCK_DEFAULT;

	}

}
