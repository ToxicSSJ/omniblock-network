package net.omniblock.network.library.utils;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class InstantFireworkUtil {

	public static void spawn(Location loc) {

		final Random random = new Random();

		final Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);

		final FireworkMeta fireworkMeta = firework.getFireworkMeta();
		fireworkMeta.addEffect(
				FireworkEffect.builder().flicker(random.nextBoolean()).withColor(getColor(random.nextInt(17) + 1))
						.withFade(getColor(random.nextInt(17) + 1)).trail(random.nextBoolean()).build());

		fireworkMeta.setPower(0);
		firework.setFireworkMeta(fireworkMeta);
		firework.detonate();

	}

	private static Color getColor(final int i) {
		switch (i) {
		case 1:
			return Color.AQUA;
		case 2:
			return Color.BLACK;
		case 3:
			return Color.BLUE;
		case 4:
			return Color.FUCHSIA;
		case 5:
			return Color.GRAY;
		case 6:
			return Color.GREEN;
		case 7:
			return Color.LIME;
		case 8:
			return Color.MAROON;
		case 9:
			return Color.NAVY;
		case 10:
			return Color.OLIVE;
		case 11:
			return Color.ORANGE;
		case 12:
			return Color.PURPLE;
		case 13:
			return Color.RED;
		case 14:
			return Color.SILVER;
		case 15:
			return Color.TEAL;
		case 16:
			return Color.WHITE;
		case 17:
			return Color.YELLOW;
		}
		return null;
	}

}
