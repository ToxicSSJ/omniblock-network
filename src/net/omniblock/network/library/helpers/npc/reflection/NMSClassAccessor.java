package net.omniblock.network.library.helpers.npc.reflection;

import net.omniblock.network.library.helpers.npc.util.ReflectionUtil;

public class NMSClassAccessor extends ClassAccessor {

	public NMSClassAccessor(String name) {
		super(ReflectionUtil.getNMSClass(name));
	}

}
