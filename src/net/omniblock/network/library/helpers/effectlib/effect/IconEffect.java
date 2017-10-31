package net.omniblock.network.library.helpers.effectlib.effect;

import org.bukkit.Location;

import net.omniblock.network.library.helpers.effectlib.Effect;
import net.omniblock.network.library.helpers.effectlib.EffectManager;
import net.omniblock.network.library.helpers.effectlib.EffectType;
import net.omniblock.network.library.helpers.effectlib.util.ParticleEffect;

public class IconEffect extends Effect {

	/**
	 * ParticleType of spawned particle
	 */
	public ParticleEffect particle = ParticleEffect.VILLAGER_ANGRY;

	public int yOffset = 2;

	public IconEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
		period = 4;
		iterations = 25;
	}

	@Override
	public void onRun() {
		Location location = getLocation();
		location.add(0, yOffset, 0);
		display(particle, location);
	}
}
