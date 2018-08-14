/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.network.library.utils;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ListsUtils {

	public static List<String> getStringList(String... strings) {
		List<String> k = new ArrayList<String>();
		for (String s : strings) {
			k.add(s);
		}
		return k;

	}

}
