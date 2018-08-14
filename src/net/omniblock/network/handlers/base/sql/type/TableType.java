package net.omniblock.network.handlers.base.sql.type;

import java.sql.SQLException;
import java.sql.Statement;

import net.omniblock.network.handlers.base.sql.Database;
import net.omniblock.network.handlers.base.sql.util.VariableUtils;

@Deprecated
public enum TableType {

	BANK_DATA("bank_data", "CREATE TABLE IF NOT EXISTS bank_data (id INT PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_money INT NOT NULL, p_exp INT NOT NULL, p_vip_points INT NOT NULL, p_items TEXT(20) NOT NULL )", "INSERT INTO bank_data (p_id, p_money, p_exp, p_vip_points, p_items) SELECT * FROM (SELECT VAR_P_ID a, VAR_P_MONEY b, VAR_P_EXP c, VAR_P_VIP_POINTS d, VAR_P_BANK_ITEMS e) AS tmp WHERE NOT EXISTS (SELECT 1 FROM bank_data WHERE p_id = VAR_P_ID );", true, false),
	SURVIVAL_BANK_DATA("survival_bank_data", "CREATE TABLE IF NOT EXISTS survival_bank_data (id INT PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_money INT NOT NULL)", "INSERT INTO survival_bank_data (p_id, p_money) SELECT * FROM (SELECT VAR_P_ID a, 0 b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM survival_bank_data WHERE p_id = VAR_P_ID );", true, false),
	PLAYERWARPS("playerwarps", "CREATE TABLE IF NOT EXISTS playerwarps (id INTEGER PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_warps TEXT NOT NULL )", "INSERT INTO playerwarps (p_id, p_warps) SELECT * FROM (SELECT VAR_P_ID a, VAR_P_WARPS b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM playerwarps WHERE p_id = p_id);", false, false),
	SHOP("shop", "CREATE TABLE IF NOT EXISTS shop (id INTEGER PRIMARY KEY AUTO_INCREMENT, s_id TEXT(20) NOT NULL, s_shop TEXT NOT NULL )", "INSERT INTO shop (s_id, s_shop) SELECT * FROM (SELECT VAR_S_ID a, VAR_S_SHOP b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM shop WHERE s_id = s_id);", false, false),
	JOBS_DATA("jobs_data", "CREATE TABLE IF NOT EXISTS jobs_data (id INTEGER PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_jobs TEXT(20) NOT NULL )", "INSERT INTO jobs_data (p_id, p_jobs) SELECT * FROM (SELECT VAR_P_ID a, '' b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM jobs_data WHERE p_id = VAR_P_ID );", true, false),
	PROTECTIONS_DATA("protections_data", "CREATE TABLE IF NOT EXISTS protections_data (id INTEGER PRIMARY KEY AUTO_INCREMENT, p_id TEXT(20) NOT NULL, p_members TEXT(20) NOT NULL )", "INSERT INTO protections_data (p_id, p_members) SELECT * FROM (SELECT VAR_P_ID a, '.' b) AS tmp WHERE NOT EXISTS (SELECT 1 FROM protections_data WHERE p_id = VAR_P_ID );", true, false),
	
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