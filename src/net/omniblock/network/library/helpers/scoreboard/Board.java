/*
 *  TheXTeam - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.network.library.helpers.scoreboard;

import java.util.Collection;

import org.bukkit.entity.Player;

public abstract class Board {

	public abstract void startDisplay(Player p);

	public abstract void stopDisplay(Player p);

	public void startDisplay(Collection<Player> players) {
		for (Player p : players)
			this.startDisplay(p);
	}
}
