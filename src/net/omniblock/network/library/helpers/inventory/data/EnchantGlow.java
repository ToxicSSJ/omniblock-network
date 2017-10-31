/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.network.library.helpers.inventory.data;

import java.lang.reflect.Field;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class EnchantGlow extends EnchantmentWrapper {

	private static Enchantment glow;

	public EnchantGlow(int id) {
		super(id);
	}

	public boolean canEnchantItem(ItemStack item) {
		return true;
	}

	public boolean conflictsWith(Enchantment other) {
		return false;
	}

	public EnchantmentTarget getItemTarget() {
		return null;
	}

	public int getMaxLevel() {
		return 10;
	}

	public String getName() {
		return "Glow";
	}

	public int getStartLevel() {
		return 1;
	}

	public static Enchantment getGlow() {
		if (glow != null) {
			return glow;
		}
		if (Enchantment.getById((int) 255) != null) {
			glow = Enchantment.getById((int) 255);
			return glow;
		}
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		glow = new EnchantGlow(255);
		Enchantment.registerEnchantment((Enchantment) glow);
		return glow;
	}

	public static ItemStack addGlow(ItemStack item) {
		Enchantment glow = EnchantGlow.getGlow();
		item.addEnchantment(glow, 1);
		return item;
	}

}
