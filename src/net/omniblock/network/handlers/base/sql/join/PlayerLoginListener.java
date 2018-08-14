/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.network.handlers.base.sql.join;

import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import net.omniblock.network.handlers.base.sql.type.TableType;

@Deprecated
public class PlayerLoginListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogin(PlayerLoginEvent e) throws SQLException {
		
		for (TableType table : TableType.values()) {

			if (table.isGeneralTable()) {
				
				table.getInserter().insert(e.getPlayer().getName());
				
			}

		}

	}

}
