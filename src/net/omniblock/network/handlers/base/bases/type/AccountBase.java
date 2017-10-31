package net.omniblock.network.handlers.base.bases.type;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import net.omniblock.network.handlers.base.sql.make.MakeSQLQuery;
import net.omniblock.network.handlers.base.sql.make.MakeSQLUpdate;
import net.omniblock.network.handlers.base.sql.make.MakeSQLUpdate.TableOperation;
import net.omniblock.network.handlers.base.sql.type.TableType;
import net.omniblock.network.handlers.base.sql.util.Resolver;
import net.omniblock.network.handlers.base.sql.util.SQLResultSet;
import net.omniblock.network.handlers.base.sql.util.VariableUtils.StartVariables;

public class AccountBase {

	public static String getTags(Player player) {

		return getTags(player.getName());
	}

	public static String getTags(String player) {

		MakeSQLQuery msq = new MakeSQLQuery(TableType.PLAYER_SETTINGS).select("p_settings").where("p_id",
				Resolver.getNetworkIDByName(player));

		try {
			SQLResultSet sqr = msq.execute();
			if (sqr.next()) {
				return sqr.get("p_settings");
			}
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return (String) StartVariables.P_SETTINGS.getInitial();
	}

	public static void setTags(String player, String tags) {

		MakeSQLUpdate msu = new MakeSQLUpdate(TableType.PLAYER_SETTINGS, TableOperation.UPDATE);

		msu.rowOperation("p_settings", tags);
		msu.whereOperation("p_id", Resolver.getNetworkIDByName(player));

		try {

			msu.execute();
			return;

		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return;

	}

	public static void setTags(Player player, String tags) {

		setTags(player.getName(), tags);
		return;

	}

	public static void addTag(Player player, String tag) {

		addTag(player.getName(), tag);
		return;

	}

	public static void addTag(String player, String tag) {

		List<String> tags = new ArrayList<String>(Arrays.asList(getTags(player).split(",")));

		if (!tags.contains(tag.toLowerCase())) {
			tags.add(tag.toLowerCase());
		}

		String newtags = StringUtils.join(tags, ',');
		setTags(player, newtags);
		return;

	}

	public static void removeTag(Player player, String tag) {

		removeTag(player.getName(), tag);
		return;

	}

	public static void removeTag(String player, String tag) {

		List<String> tags = new ArrayList<String>(Arrays.asList(getTags(player).split(",")));

		if (tags.contains(tag.toLowerCase())) {
			tags.remove(tag.toLowerCase());
		}

		String newtags = StringUtils.join(tags, ',');
		setTags(player, newtags);
		return;

	}

}
