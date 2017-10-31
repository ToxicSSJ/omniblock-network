package net.omniblock.network.library.helpers.effectlib.effect;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import net.omniblock.network.library.helpers.effectlib.Effect;
import net.omniblock.network.library.helpers.effectlib.EffectManager;
import net.omniblock.network.library.helpers.effectlib.EffectType;
import net.omniblock.network.library.helpers.effectlib.util.RandomUtils;

public class BleedEffect extends Effect {

	/**
	 * Play the Hurt Effect for the Player
	 */
	public boolean hurt = true;

	/**
	 * Duration in ticks, the blood-particles take to despawn. Not used anymore
	 */
	@Deprecated
	public int duration = 10;

	/**
	 * Color of blood. Default is red (152)
	 */
	public int color = 152;

	public BleedEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
		period = 4;
		iterations = 25;
	}

	@Override
	public void onRun() {
		// Location to spawn the blood-item.
		Location location = getLocation();
		location.add(0, RandomUtils.random.nextFloat() * 1.75f, 0);
		location.getWorld().playEffect(location, org.bukkit.Effect.STEP_SOUND, color);

		Entity entity = getEntity();
		if (hurt && entity != null) {
			entity.playEffect(org.bukkit.EntityEffect.HURT);
		}
	}
}
