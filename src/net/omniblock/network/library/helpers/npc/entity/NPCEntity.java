package net.omniblock.network.library.helpers.npc.entity;

import com.google.common.collect.Maps;

import net.omniblock.network.library.helpers.npc.data.EquipmentSlot;
import net.omniblock.network.library.helpers.npc.data.NPCAnimation;
import net.omniblock.network.library.helpers.npc.reflection.ClassAccessor;
import net.omniblock.network.library.helpers.npc.reflection.MethodAccessor;
import net.omniblock.network.library.helpers.npc.reflection.NMSClassAccessor;
import net.omniblock.network.library.helpers.npc.util.ReflectionUtil;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.Map;

public class NPCEntity implements MethodInterceptor {

	private static final Map<String, MethodAccessor> methods = Maps.newHashMap();

	static {
		for (Method method : NPCEntity.class.getMethods()) {
			String name = method.getName();
			MethodAccessor methodAccessor = ReflectionUtil.getAccessor(method);
			if (methods.containsKey(name)) {
				continue;
			}

			methods.put(name, methodAccessor);
		}
	}

	@SuppressWarnings("unused")
	private static final ClassAccessor accessor = new NMSClassAccessor("EntityPlayer");
	@SuppressWarnings("unused")
	private final Object SUPER = new Object();

	// Proxy information (cahce)
	@SuppressWarnings("unused")
	private Object instance;
	@SuppressWarnings("unused")
	private MethodProxy proxy;

	private boolean godMode;
	private boolean gravity;
	private Entity target;

	@Override
	public Object intercept(Object instance, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		this.instance = instance;
		this.proxy = proxy;

		String name = method.getName();
		MethodAccessor methodAccessor = methods.get(name);
		if (methodAccessor != null) {
			// TODO: Invoke, but needs code in MethodAccessor
		}

		return proxy.invokeSuper(instance, args);
	}

	public Player getBukkitEntity() {
		// TODO: Everything...
		throw new UnsupportedOperationException("This method is not done yet");
	}

	public void setLying(double x, double y, double z) {
		// TODO: Everything...
		throw new UnsupportedOperationException("This method is not done yet");
	}

	public boolean isLying() {
		// TODO: Everything...
		throw new UnsupportedOperationException("This method is not done yet");
	}

	public boolean pathfindTo(Location location) {
		// TODO: Everything...
		throw new UnsupportedOperationException("This method is not done yet");
	}

	public boolean pathfindTo(Location location, double speed) {
		// TODO: Everything...
		throw new UnsupportedOperationException("This method is not done yet");
	}

	public boolean pathfindTo(Location location, double speed, double range) {
		// TODO: Everything...
		throw new UnsupportedOperationException("This method is not done yet");
	}

	public void lookAt(Location location) {
		// TODO: Everything...
		throw new UnsupportedOperationException("This method is not done yet");
	}

	public void setYaw(float yaw) {
		// TODO: Everything...
		throw new UnsupportedOperationException("This method is not done yet");
	}

	public void playAnimation(NPCAnimation animation) {
		// TODO: Everything...
		throw new UnsupportedOperationException("This method is not done yet");
	}

	public void setEquipment(EquipmentSlot slot, ItemStack item) {
		// TODO: Everything...
		throw new UnsupportedOperationException("This method is not done yet");
	}

	public boolean getEntityCollision() {
		return false;
	}

	public void setEntityCollision(boolean entityCollision) {
	}

	public boolean isCollisionEnabled() {
		// TODO: Everything...
		throw new UnsupportedOperationException("This method is not done yet");
	}

	public void setCollisionEnabled(boolean collisionEnabled) {
		// TODO: Everything...
		throw new UnsupportedOperationException("This method is not done yet");
	}

	public boolean isGodMode() {
		return godMode;
	}

	public void setGodMode(boolean godMode) {
		this.godMode = godMode;
	}

	public boolean isGravity() {
		return gravity;
	}

	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

}
