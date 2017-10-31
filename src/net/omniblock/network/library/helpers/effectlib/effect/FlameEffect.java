package net.omniblock.network.library.helpers.effectlib.effect;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import net.omniblock.network.library.helpers.effectlib.Effect;
import net.omniblock.network.library.helpers.effectlib.EffectManager;
import net.omniblock.network.library.helpers.effectlib.EffectType;
import net.omniblock.network.library.helpers.effectlib.util.ParticleEffect;
import net.omniblock.network.library.helpers.effectlib.util.RandomUtils;

public class FlameEffect extends Effect {

	public ParticleEffect particle = ParticleEffect.FLAME;

	public FlameEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
		period = 1;
		iterations = 600;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRun() {
		Location location = getLocation();
		for (int i = 0; i < 10; i++) {
			Vector v = RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextDouble() * 0.6d);
			v.setY(RandomUtils.random.nextFloat() * 1.8);
			location.add(v);
			particle.display(location, visibleRange);
			location.subtract(v);
		}
	}

}
