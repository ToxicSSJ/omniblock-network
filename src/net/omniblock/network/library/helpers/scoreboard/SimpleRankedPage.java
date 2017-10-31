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

import java.util.HashMap;

import org.bukkit.entity.Player;

public class SimpleRankedPage implements BoardPage {

	private String title;
	private HashMap<String, Integer> content;

	public SimpleRankedPage(String title, HashMap<String, Integer> content) {
		this.title = title;
		this.content = content;
	}

	@Override
	public void update(Player p) {
		ScoreboardUtil.rankedSidebarDisplay(p, title, content);
	}
}
