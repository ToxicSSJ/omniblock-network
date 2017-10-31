package net.omniblock.network.library.addons.resourceaddon.object;

import org.bukkit.entity.Player;

public class ResourcePack {

	private String name;
	private String url;
	private String hash;

	public ResourcePack(String name, String url, String hash) {

		this.name = name;
		this.url = url;
		this.hash = hash;

	}

	@SuppressWarnings("deprecation")
	public void send(Player player) {

		if (name != null && url != null && hash != null) {

			try {

				player.setTexturePack(url);
				return;

			} catch (Exception e) {

				new RuntimeException("No se ha podido cargar el paquete de recursos " + name + ".");
				return;

			}

		}

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

}
