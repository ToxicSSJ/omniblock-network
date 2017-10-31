package net.omniblock.network.library.addons.configaddon;

import org.bukkit.configuration.file.FileConfiguration;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.library.addons.configaddon.object.Config;

public class Factory {

	public enum ConfigType {

		DATABASE(new Config(OmniNetwork.getInstance(), "database/database.yml")), SSH(
				new Config(OmniNetwork.getInstance(), "ssh/ssh.yml")),

		;

		private Config config;

		ConfigType(Config config) {
			this.config = config;
		}

		public Config getConfigObject() {
			return config;
		}

		public FileConfiguration getConfig() {
			return config.getConfigFile();
		}

		public void setConfig(Config config) {
			this.config = config;
		}

	}

}
