package net.omniblock.network.library.utils;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Deprecated
public class NMSUtils {

	public static int getPing(Player player) {

		try {

			String bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);

			Class<?> craftPlayer;

			craftPlayer = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".entity.CraftPlayer");
			Object handle = craftPlayer.getMethod("getHandle").invoke(player);

			Integer ping = (Integer) handle.getClass().getDeclaredField("ping").get(handle);

			return ping.intValue();

		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}

		return 90;

	}

}
