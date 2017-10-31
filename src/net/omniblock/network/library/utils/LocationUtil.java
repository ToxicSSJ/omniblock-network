package net.omniblock.network.library.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {

	public static Location fromString(String string) {
		String[] parts = string.split(",");

		if (parts.length == 6) {
			String world = parts[0];
			World w = Bukkit.getWorld(world);

			if (w == null) {
				throw new IllegalArgumentException("El mundo de la localizacion no existe: " + world);
			}

			try {
				double x = Double.parseDouble(parts[1]);
				double y = Double.parseDouble(parts[2]);
				double z = Double.parseDouble(parts[3]);
				float yaw = Float.parseFloat(parts[4]);
				float pitch = Float.parseFloat(parts[5]);

				return new Location(w, x, y, z, yaw, pitch);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Number format exception: " + e.getMessage());
			}
		}

		throw new IllegalArgumentException("El string proporcionado debe tener 6 partes: " + string);
	}

	public static String toString(Location location) {
		return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ()
				+ "," + location.getYaw() + "," + location.getPitch();
	}

	/**
	 * Centra la localización en el centro del bloque.
	 * 
	 * @param location
	 *            Localización a centrar.
	 * @return Localización centrada.
	 */
	public static Location centerLocation(Location location) {
		return new Location(location.getWorld(), location.getBlockX() + 0.5D, location.getBlockY() + 0.5D,
				location.getBlockZ() + 0.5D);
	}

}
