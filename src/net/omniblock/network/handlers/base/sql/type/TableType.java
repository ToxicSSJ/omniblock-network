package net.omniblock.network.handlers.base.sql.type;

import java.sql.SQLException;
import java.sql.Statement;

import net.omniblock.network.handlers.base.sql.Database;
import net.omniblock.network.handlers.base.sql.util.VariableUtils;

public enum TableType {

	PLAYER_SETTINGS("player_settings", "CREATE TABLE IF NOT EXISTS player_settings (id INT PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_pass TEXT(20) NOT NULL, p_email TEXT(20) NOT NULL, p_settings TEXT(20) NOT NULL )", "INSERT INTO player_settings (p_id, p_pass, p_email, p_settings) SELECT * FROM (SELECT VAR_P_ID a, VAR_P_PASS b, VAR_P_EMAIL c, VAR_P_SETTINGS d) AS tmp WHERE NOT EXISTS (SELECT 1 FROM player_settings WHERE p_id = VAR_P_ID );", true, false),
	BOOSTERS_DATA("boosters_data", "CREATE TABLE IF NOT EXISTS boosters_data (id INT PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_network_booster TEXT(20) NOT NULL, p_network_booster_gametype TEXT(20) NOT NULL, p_network_booster_expire TEXT(20) NOT NULL, p_personal_booster TEXT(20) NOT NULL, p_personal_booster_expire TEXT(20) NOT NULL )", "INSERT INTO boosters_data (p_id, p_network_booster, p_network_booster_gametype, p_network_booster_expire, p_personal_booster, p_personal_booster_expire) SELECT * FROM (SELECT VAR_P_ID a, VAR_P_COMMONBOOSTER b, VAR_P_COMMONBOOSTER c, VAR_P_COMMONBOOSTER d, VAR_P_COMMONBOOSTER e, VAR_P_COMMONBOOSTER f) AS tmp WHERE NOT EXISTS (SELECT 1 FROM boosters_data WHERE p_id = VAR_P_ID );", true, false),
	RANK_DATA("rank_data", "CREATE TABLE IF NOT EXISTS rank_data (id INT PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_rank TEXT(20) NOT NULL, p_temp_rank TEXT(20) NOT NULL, p_temp_rank_expire TEXT(20) NOT NULL, p_loot TEXT(20) NOT NULL )", "INSERT INTO rank_data (p_id, p_rank, p_temp_rank, p_temp_rank_expire, p_loot) SELECT * FROM (SELECT VAR_P_ID a, VAR_P_RANK b, VAR_P_TEMP_RANK c, VAR_P_TEMP_EXPIRE d, VAR_P_LOOT e) AS tmp WHERE NOT EXISTS (SELECT 1 FROM rank_data WHERE p_id = VAR_P_ID );", true, false),
	BANK_DATA("bank_data", "CREATE TABLE IF NOT EXISTS bank_data (id INT PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_money INT NOT NULL, p_exp INT NOT NULL, p_vip_points INT NOT NULL, p_items TEXT(20) NOT NULL )", "INSERT INTO bank_data (p_id, p_money, p_exp, p_vip_points, p_items) SELECT * FROM (SELECT VAR_P_ID a, VAR_P_MONEY b, VAR_P_EXP c, VAR_P_VIP_POINTS d, VAR_P_BANK_ITEMS e) AS tmp WHERE NOT EXISTS (SELECT 1 FROM bank_data WHERE p_id = VAR_P_ID );", true, false),
	SKYWARS_DATA("skywars_data", "CREATE TABLE IF NOT EXISTS skywars_data (id INT PRIMARY KEY AUTO_INCREMENT,  p_id TEXT(20) NOT NULL, p_items TEXT(20) NOT NULL, p_extra_points TEXT(20) NOT NULL, p_selected TEXT(20) NOT NULL, p_last_games_average TEXT(20) NOT NULL )", "INSERT INTO skywars_data (p_id, p_items, p_extra_points, p_selected, p_last_games_average) SELECT * FROM (SELECT VAR_P_ID a, VAR_P_SKYWARS_ITEMS b, VAR_P_SKYWARS_EXTRA_POINTS d, VAR_P_SKYWARS_SELECTED e, VAR_P_SKYWARS_LAST_GAMES_AVERAGE f) AS tmp WHERE NOT EXISTS (SELECT 1 FROM skywars_data WHERE p_id = VAR_P_ID );", true, false),
	TOP_STATS_SKYWARS("top_stats_skywars", "CREATE TABLE IF NOT EXISTS top_stats_skywars (id INTEGER PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, kills INTEGER NOT NULL, deaths INTEGER NOT NULL, assists INTEGER NOT NULL, games INTEGER NOT NULL, wins INTEGER NOT NULL, average DOUBLE NOT NULL) ", "INSERT INTO top_stats_skywars (p_id, kills, deaths, assists, games, wins, average)  SELECT * FROM (SELECT VAR_P_ID a, VAR_P_SKYWARS_STATS_KILLS b, VAR_P_SKYWARS_STATS_DEATHS c, VAR_P_SKYWARS_STATS_ASSISTS d, VAR_P_SKYWARS_STATS_GAMES e, VAR_P_SKYWARS_STATS_WINS f, VAR_P_SKYWARS_STATS_AVERAGE g) AS tmp WHERE NOT EXISTS (SELECT 1 FROM top_stats_skywars WHERE p_id = VAR_P_ID );", true, false),
	SURVIVAL_BANK_DATA("survival_bank_data", "CREATE TABLE IF NOT EXISTS survival_bank_data (id INT PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_money INT NOT NULL)", "INSERT INTO survival_bank_data (p_id, p_money) SELECT * FROM (SELECT VAR_P_ID a, 0 b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM survival_bank_data WHERE p_id = VAR_P_ID );", true, false),
	TOP_MAPS_SKYWARS("top_maps_skywars", "CREATE TABLE IF NOT EXISTS top_maps_skywars (id INTEGER PRIMARY KEY AUTO_INCREMENT, map_name TEXT(20) NOT NULL, votes INT NOT NULL)", "INSERT INTO top_maps_skywars (map_name, votes) SELECT * FROM (SELECT VAR_P_SKYWARS_MAP a, VAR_P_SKYWARS_MAP_INITIAL b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM top_maps_skywars WHERE map_name = VAR_P_SKYWARS_MAP );", false, false),
	TOP_STATS_WEEKPRIZE_SKYWARS("skywars_weekprize", "CREATE TABLE IF NOT EXISTS skywars_weekprize (id INTEGER PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_points INT NOT NULL)", "INSERT INTO skywars_weekprize (p_id, p_points) SELECT * FROM (SELECT VAR_P_ID a, VAR_P_SKYWARS_WEEKPRIZE b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM skywars_weekprize WHERE p_id = VAR_P_ID );", true, false),
	BETAKEYS("betakeys", "CREATE TABLE IF NOT EXISTS betakeys (id INTEGER PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_key TEXT(10) NOT NULL )", "INSERT INTO betakeys (p_id, p_key) SELECT * FROM (SELECT VAR_P_ID a, VAR_P_KEY b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM betakeys WHERE p_id = p_id);", false, false),
	PLAYERWARPS("playerwarps", "CREATE TABLE IF NOT EXISTS playerwarps (id INTEGER PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_warps TEXT NOT NULL )", "INSERT INTO playerwarps (p_id, p_warps) SELECT * FROM (SELECT VAR_P_ID a, VAR_P_WARPS b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM playerwarps WHERE p_id = p_id);", false, false),
	SHOP("shop", "CREATE TABLE IF NOT EXISTS shop (id INTEGER PRIMARY KEY AUTO_INCREMENT, s_id TEXT(20) NOT NULL, s_shop TEXT NOT NULL )", "INSERT INTO shop (s_id, s_shop) SELECT * FROM (SELECT VAR_S_ID a, VAR_S_SHOP b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM shop WHERE s_id = s_id);", false, false),
	GADGETS_DATA("gadgets_data", "CREATE TABLE IF NOT EXISTS gadgets_data (id INTEGER PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_gadgets TEXT(20) NOT NULL )", "INSERT INTO gadgets_data (p_id, p_gadgets) SELECT * FROM (SELECT VAR_P_ID a, VAR_P_GADGETS b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM gadgets_data WHERE p_id = VAR_P_ID );", true, false),
	JOBS_DATA("jobs_data", "CREATE TABLE IF NOT EXISTS jobs_data (id INTEGER PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_jobs TEXT(20) NOT NULL )", "INSERT INTO jobs_data (p_id, p_jobs) SELECT * FROM (SELECT VAR_P_ID a, '' b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM jobs_data WHERE p_id = VAR_P_ID );", true, false),
	PROTECTIONS_DATA("protections_data", "CREATE TABLE IF NOT EXISTS protections_data (id INTEGER PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_members TEXT(20) NOT NULL )", "INSERT INTO protections_data (p_id, p_members) SELECT * FROM (SELECT VAR_P_ID a, '.' b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM protections_data WHERE p_id = VAR_P_ID );", true, false),
	BAN_DATA("ban_data", "CREATE TABLE IF NOT EXISTS ban_data (id INTEGER PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, banned BOOLEAN NOT NULL )", "INSERT INTO ban_data (p_id, banned) SELECT * FROM (SELECT VAR_P_ID a, VAR_P_BAN b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM ban_data WHERE p_id = VAR_P_ID );", true, false),
	BAN_REGISTRY("ban_registry", "CREATE TABLE IF NOT EXISTS ban_registry (id INTEGER PRIMARY KEY AUTO_INCREMENT, ban_hash TEXT(20) NOT NULL, p_mod TEXT(20) NOT NULL, p_banned TEXT(20) NOT NULL, ban_reason TEXT(20) NOT NULL, ban_time_from TEXT(20) NOT NULL, ban_time_to TEXT(20) NOT NULL )", "INSERT INTO ban_registry (ban_hash, p_mod, p_banned, ban_reason, ban_time_from, ban_time_to ) VALUES (VAR_BAN_HASH, VAR_MOD, VAR_BANNED, VAR_REASON, VAR_BAN_TIME_FROM, VAR_BAN_TIME_TO);", false, false),
	
	;

	private String table;

	private TableCreator creator;
	private TableInserter inserter;

	private boolean general_table;
	private boolean premium_security;

	TableType(String table, String creator, String inserter, boolean general_table, boolean premium_security) {

		this.table = table;

		this.creator = new TableCreator(table, creator);
		this.inserter = new TableInserter(this, table, inserter, premium_security);

		this.general_table = general_table;
		this.premium_security = premium_security;

	}

	public String getTableName() {
		return table;
	}

	public TableCreator getCreator() {
		return creator;
	}

	public boolean hasPremiumSecurity() {
		return premium_security;
	}

	public TableInserter getInserter() {
		return inserter;
	}

	public boolean isGeneralTable() {
		return general_table;
	}

	public class TableInserter {

		private String table;
		private String inserter_sql;
		private TableType type;

		private boolean sure = false;

		public TableInserter(TableType type, String table, String inserter, boolean sure) {
			this.type = type;
			this.table = table;
			this.inserter_sql = inserter;
			this.sure = sure;
		}

		public void insert(String player_name) throws SQLException {
			Statement stm = null;
			try {
				stm = Database.getConnection().createStatement();
				stm.executeUpdate(VariableUtils.formatVariables(inserter_sql, player_name, true));
			} finally {
				if (stm != null) {
					stm.close();
				}
			}
		}

		public boolean exists() {

			try {
				if (Database.getConnection().getMetaData().getTables(null, null, table, null).next()) {
					return true;
				}
			} catch(SQLException e) {
				return false;
			}

			return false;

		}

		public String getInserterSQL() {
			return inserter_sql;
		}

		public String getTable() {
			return table;
		}

		public boolean isSure() {
			return sure;
		}

		public TableType getType() {
			return type;
		}

	}

	public class TableCreator {

		private String table;
		private String creator_sql;

		public TableCreator(String table, String creator) {
			this.table = table;
			this.creator_sql = creator;
		}

		public void make(Statement stm) throws SQLException {

			try {
				stm.executeUpdate(creator_sql);
			} catch(Exception e) {
				e.printStackTrace();
			}

		}

		public boolean exists() {

			try {
				if (Database.getConnection().getMetaData().getTables(null, null, table, null).next()) {
					return true;
				}
			} catch(SQLException e) {
				return false;
			}

			return false;

		}

		public String getCreatorSQL() {
			return creator_sql;
		}

		public String getTable() {
			return table;
		}

	}

}