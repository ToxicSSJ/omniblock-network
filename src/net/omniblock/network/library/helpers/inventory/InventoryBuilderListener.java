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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InventoryBuilderListener implements Listener {

	static Map<UUID, UUID> currentInventory = new HashMap<UUID, UUID>();
	static Map<UUID, InventoryBuilder> inventoryByUUID = new HashMap<UUID, InventoryBuilder>();
	static List<UUID> kappa = new ArrayList<UUID>();

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		if (kappa.contains(u)) {
			kappa.remove(u);
		}
		if (currentInventory.containsKey(u)) {
			InventoryBuilder inv;
			UUID invU = currentInventory.get(u);
			currentInventory.remove(u);
			if (inventoryByUUID.containsKey(invU) && (inv = inventoryByUUID.get(invU)).isDeleteOnClose()) {
				inv.delete();
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		UUID pU = p.getUniqueId();
		if (kappa.contains(pU)) {
			kappa.remove(pU);
			return;
		}
		UUID u = currentInventory.get(pU);
		currentInventory.remove(pU);
		if (u != null) {
			InventoryBuilder inv = inventoryByUUID.get(u);
			inv.close(p);
		}
	}

	@EventHandler
	public void onDragg(InventoryDragEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}

		Player p = (Player) e.getWhoClicked();
		UUID u = currentInventory.get(p.getUniqueId());
		if (u != null) {
			InventoryBuilder inventory = inventoryByUUID.get(u);

			boolean insideProtectedInventory = false;
			for(int rawSlot : e.getRawSlots()) {
				if(rawSlot < inventory.getSize()) {
					insideProtectedInventory = true;
					break;
				}
			}

			if(insideProtectedInventory) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}

		Player p = (Player) e.getWhoClicked();
		UUID u = currentInventory.get(p.getUniqueId());
		if (u != null) {
			InventoryBuilder inventory = inventoryByUUID.get(u);

			if(e.isShiftClick() || e.getRawSlot() < inventory.getSize()) {
				e.setCancelled(true);

				InventoryBuilder.Action action = inventory.getActions().get(e.getRawSlot());
				if (action != null) {
					action.click(e.getClick(), p);
				}
			}
		}
	}
}