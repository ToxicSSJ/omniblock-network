package net.omniblock.network.handlers.base.bases.type;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;

import net.omniblock.network.handlers.base.sql.make.MakeSQLQuery;
import net.omniblock.network.handlers.base.sql.make.MakeSQLUpdate;
import net.omniblock.network.handlers.base.sql.make.MakeSQLUpdate.TableOperation;
import net.omniblock.network.handlers.base.sql.type.TableType;
import net.omniblock.network.handlers.base.sql.util.Resolver;
import net.omniblock.network.handlers.base.sql.util.SQLResultSet;
import net.omniblock.network.handlers.base.sql.util.VariableUtils.StartVariables;
import net.omniblock.network.systems.account.AccountManager.AccountBoosterType;
import net.omniblock.network.systems.account.items.BoosterType;
import net.omniblock.network.systems.account.items.NetworkBoosterType;

public class BoosterBase {

	public static void startBooster(Player player, String key, AccountBoosterType type, String... args) {

		startBooster(player.getName(), key, type, args);
		return;

	}

	public static void startBooster(String player, String key, AccountBoosterType type, String... args) {

		String general_row = type == AccountBoosterType.PERSONAL_BOOSTER ? "p_personal_booster" : "p_network_booster";
		String general_time_row = type == AccountBoosterType.PERSONAL_BOOSTER ? "p_personal_booster_expire"
				: "p_network_booster_expire";

		MakeSQLUpdate msu = new MakeSQLUpdate(TableType.BOOSTERS_DATA, TableOperation.UPDATE);

		msu.rowOperation(general_row, key);
		msu.rowOperation(general_time_row, parseExpireDate(type == AccountBoosterType.PERSONAL_BOOSTER
				? BoosterType.fromKey(key).getEndDate() : NetworkBoosterType.fromKey(key).getEndDate()));
		if (type == AccountBoosterType.NETWORK_BOOSTER) {
			msu.rowOperation("p_network_booster_gametype", args[0]);
		}
		msu.whereOperation("p_id", Resolver.getNetworkIDByName(player));

		try {

			msu.execute();
			return;

		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return;

	}

	public static void removeEnabledBooster(Player player, AccountBoosterType type) {

		removeEnabledBooster(player.getName(), type);
		return;
	}

	public static void removeEnabledBooster(String player, AccountBoosterType type) {

		String general_row = type == AccountBoosterType.PERSONAL_BOOSTER ? "p_personal_booster" : "p_network_booster";
		String general_time_row = type == AccountBoosterType.PERSONAL_BOOSTER ? "p_personal_booster_expire"
				: "p_network_booster_expire";

		MakeSQLUpdate msu = new MakeSQLUpdate(TableType.BOOSTERS_DATA, TableOperation.UPDATE);

		msu.rowOperation(general_row, StartVariables.P_COMMON_BOOSTER.getInitial());
		msu.rowOperation(general_time_row, StartVariables.P_COMMON_BOOSTER.getInitial());
		if (type == AccountBoosterType.NETWORK_BOOSTER) {
			msu.rowOperation("p_network_booster_gametype", StartVariables.P_COMMON_BOOSTER.getInitial());
		}
		msu.whereOperation("p_id", Resolver.getNetworkIDByName(player));

		try {

			msu.execute();
			return;

		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return;
	}

	public static String getEnabledBooster(Player player, AccountBoosterType type) {

		String select = type == AccountBoosterType.PERSONAL_BOOSTER ? "p_personal_booster" : "p_network_booster";

		MakeSQLQuery msq = new MakeSQLQuery(TableType.BOOSTERS_DATA).select(select).where("p_id",
				Resolver.getNetworkID(player));

		try {

			SQLResultSet sqr = msq.execute();

			if (sqr.next()) {
				return sqr.get(select);
			}

		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return StartVariables.P_COMMON_BOOSTER.getInitial();
	}

	public static String getNetworkBoosterGameType(Player player) {

		String select = "p_network_booster_gametype";

		MakeSQLQuery msq = new MakeSQLQuery(TableType.BOOSTERS_DATA).select(select).where("p_id",
				Resolver.getNetworkID(player));

		try {
			SQLResultSet sqr = msq.execute();
			if (sqr.next()) {
				return sqr.get(select);
			}
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return StartVariables.P_COMMON_BOOSTER.getInitial();
	}

	public static String getExpireDate(Player player, AccountBoosterType type) {

		String select = type == AccountBoosterType.PERSONAL_BOOSTER ? "p_personal_booster_expire"
				: "p_network_booster_expire";

		MakeSQLQuery msq = new MakeSQLQuery(TableType.BOOSTERS_DATA).select(select).where("p_id",
				Resolver.getNetworkID(player));

		try {
			SQLResultSet sqr = msq.execute();
			if (sqr.next()) {
				return sqr.get(select);
			}
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return StartVariables.P_COMMON_BOOSTER.getInitial();
	}

	public static String parseExpireDate(Date expiredate) {

		SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return curFormater.format(expiredate);

	}

	public static Date parseExpireDate(String expiredate) {

		SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateObj = new Date();

		try {
			dateObj = curFormater.parse(expiredate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dateObj;
	}

}
