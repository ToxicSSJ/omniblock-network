package net.omniblock.network.library.helpers.effectlib.effect;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import net.omniblock.network.library.helpers.effectlib.Effect;
import net.omniblock.network.library.helpers.effectlib.EffectManager;
import net.omniblock.network.library.helpers.effectlib.EffectType;
import net.omniblock.network.library.helpers.effectlib.util.ParticleEffect;
import net.omniblock.network.library.helpers.effectlib.util.RandomUtils;

public class ShieldEffect extends Effect {
	/**
	 * ParticleType of spawned particle
	 */
	public ParticleEffect particle = ParticleEffect.FLAME;

	/**
	 * Radius of the shield
	 */
	public int radius = 3;

	/**
	 * Particles to display
	 */
	public int particles = 50;

	/**
	 * Set to false for a half-sphere and true for a complete sphere
	 */
	public boolean sphere = false;

	public ShieldEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
		iterations = 500;
		period = 1;
	}

	@Override
	public void onRun() {
		Location location = getLocation();
		for (int i = 0; i < particles; i++) {
			Vector vector = RandomUtils.getRandomVector().multiply(radius);
			if (!sphere)
				vector.setY(Math.abs(vector.getY()));
			location.add(vector);
			display(particle, location);
			location.subtract(vector);
		}
	}

}
