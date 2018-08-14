/*
 *  TheXTeam - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.network.library.addons.configaddon.object;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import net.omniblock.network.handlers.Handlers;
import net.omniblock.network.library.utils.FileRefactorUtil;

@Deprecated
public class Config {

	public Plugin plugin;

	private FileConfiguration config;
	private File file;

	public Config(Plugin instance, String resource) {

		plugin = instance;
		file = new File(plugin.getDataFolder(), resource);

		if (!file.exists()) {

			try {

				file.getParentFile().mkdirs();
				FileRefactorUtil.copyFile(plugin.getResource(resource), file);
				config = YamlConfiguration.loadConfiguration(file);

			} catch (Exception e) {
				Handlers.LOGGER.sendError("Un error fatal al crear un archivo de texto!");
				e.printStackTrace();
			}

		} else {

			config = YamlConfiguration.loadConfiguration(file);

		}

	}

	public void save() {

		try {

			config.save(file);

		} catch (IOException e) {

			Handlers.LOGGER.sendError("Un error fatal al guardar un archivo de texto!");
			e.printStackTrace();

		}

	}

	public FileConfiguration getConfigFile() {
		return config;
	}

	public File getFile() {
		return file;
	}

}
