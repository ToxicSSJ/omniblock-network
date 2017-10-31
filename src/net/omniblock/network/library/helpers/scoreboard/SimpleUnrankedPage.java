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

import org.bukkit.entity.Player;

public class SimpleUnrankedPage implements BoardPage {

	private String[] content;

	public SimpleUnrankedPage(String[] content) {
		this.content = content;
	}

	@Override
	public void update(Player p) {
		ScoreboardUtil.unrankedSidebarDisplay(p, content);
	}
}
