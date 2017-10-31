package net.omniblock.network.library.helpers.effectlib.effect;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import net.omniblock.network.library.helpers.effectlib.Effect;
import net.omniblock.network.library.helpers.effectlib.EffectManager;
import net.omniblock.network.library.helpers.effectlib.EffectType;
import net.omniblock.network.library.helpers.effectlib.util.ParticleEffect;

import java.util.ArrayList;
import java.util.List;

public class TraceEffect extends Effect {

	/**
	 * Particle to spawn
	 */
	public ParticleEffect particle = ParticleEffect.FLAME;

	/**
	 * Interations to wait before refreshing particles
	 */
	public int refresh = 5;

	/**
	 * Maximum amount of way points
	 */
	public int maxWayPoints = 30;

	/**
	 * Waypoints of the trace
	 */
	protected List<Vector> wayPoints;

	/**
	 * Internal counter
	 */
	protected int step = 0;

	/**
	 * World of the trace
	 */
	protected World world;

	public TraceEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
		period = 1;
		iterations = 600;
		wayPoints = new ArrayList<Vector>();
	}

	@Override
	public void onRun() {
		Location location = getLocation();
		if (world == null) {
			world = location.getWorld();
		} else if (!location.getWorld().equals(world)) {
			cancel(true);
			return;
		}

		if (wayPoints.size() >= maxWayPoints)
			wayPoints.remove(0);

		wayPoints.add(location.toVector());
		step++;
		if (step % refresh != 0)
			return;

		for (Vector position : wayPoints) {
			Location particleLocation = new Location(world, position.getX(), position.getY(), position.getZ());
			display(particle, particleLocation);
		}
	}

}
