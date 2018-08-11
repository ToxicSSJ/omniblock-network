/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.network.library.helpers.inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import net.omniblock.network.library.helpers.ItemBuilder;
import net.omniblock.network.library.utils.TextUtil;

public class InventoryBuilder {

	private UUID id = UUID.randomUUID();
	private Inventory bukkitInventory;
	private String name;
	private int size;
	private boolean deleteOnClose;
	private Map<Integer, Action> actions;
	private Set<Integer> editableSlots;

	public enum RowsIntegers {
		ROW_1(0, 1, 2, 3, 4, 5, 6, 7, 8), ROW_2(9, 10, 11, 12, 13, 14, 15, 16, 17), ROW_3(18, 19, 20, 21, 22, 23, 24,
				25, 26), ROW_4(27, 28, 29, 30, 31, 32, 33, 34, 35), ROW_5(36, 37, 38, 39, 40, 41, 42, 43, 44), ROW_6(45,
						46, 47, 48, 49, 50, 51, 52,
						53), AYS_YES(9, 10, 11, 18, 19, 20, 27, 28, 29), AYS_NO(15, 16, 17, 24, 25, 26, 33, 34, 35);

		private Integer[] integers;

		RowsIntegers(Integer... integers) {
			this.setIntegers(integers);
		}

		public Integer[] getIntegers() {
			return integers;
		}

		public void setIntegers(Integer[] integers) {
			this.integers = integers;
		}

	}

	public InventoryBuilder(String name, int size, boolean deleteOnClose) {
		this.name = TextUtil.format(name);
		this.size = size;
		this.actions = new HashMap<Integer, Action>();
		this.deleteOnClose = deleteOnClose;
		this.bukkitInventory = Bukkit.createInventory((InventoryHolder) null, (int) this.size, (String) this.name);
		this.editableSlots = new HashSet<Integer>();
		InventoryBuilderListener.inventoryByUUID.put(this.id, this);
	}

	public void setSlotsEditable(int... slots) {
		for (int i : slots) {
			this.editableSlots.add(i);
		}
	}

	public void delete() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			UUID u = p.getUniqueId();
			UUID cU = InventoryBuilderListener.currentInventory.get(u);
			if (cU == null || !cU.equals(this.id))
				continue;
			p.closeInventory();
		}
		InventoryBuilderListener.inventoryByUUID.remove(this.id);
	}

	public void close(Player p) {
		UUID pU = p.getUniqueId();
		UUID u = InventoryBuilderListener.currentInventory.get(pU);
		if (u != null) {
			InventoryBuilderListener.kappa.add(pU);
			InventoryBuilderListener.currentInventory.remove(pU);
			InventoryBuilder inv = InventoryBuilderListener.inventoryByUUID.get(u);
			if (inv.isDeleteOnClose()) {
				inv.delete();
			}
		}
	}

	public void open(Player p) {
		this.close(p);
		InventoryBuilderListener.currentInventory.put(p.getUniqueId(), this.id);
		p.openInventory(this.bukkitInventory);
	}

	public void fill(ItemStack stack, RowsIntegers rowintegers) {
		for (int i : rowintegers.getIntegers()) {
			addItem(stack, i);
		}
	}

	public void fill(ItemStack stack, RowsIntegers rowintegers, Action action) {
		for (int i : rowintegers.getIntegers()) {
			addItem(stack, i, action);
		}
	}

	public void addItem(ItemStack stack, int slot) {
		this.bukkitInventory.setItem(slot, stack);
	}

	public void addItem(ItemStack stack, int slot, Action action) {
		this.addItem(stack, slot);
		this.actions.put(slot, action);
	}

	public void placeholder(int minSlot, int maxSlot) {
		for (int i = minSlot; i <= maxSlot; ++i) {
			this.bukkitInventory.setItem(i,
					new ItemBuilder(Material.STAINED_GLASS_PANE).replaceAndSymbol(true).data(15).name(" ").build());
		}
	}

	public boolean isDeleteOnClose() {
		return this.deleteOnClose;
	}

	public UUID getId() {
		return this.id;
	}

	public Inventory getBukkitInventory() {
		return this.bukkitInventory;
	}

	public String getName() {
		return this.name;
	}

	public int getSize() {
		return this.size;
	}

	public Map<Integer, Action> getActions() {
		return this.actions;
	}

	public static interface Action {
		public void click(ClickType click, Player player);
	}

}
