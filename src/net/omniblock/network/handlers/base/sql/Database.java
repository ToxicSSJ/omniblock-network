/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.network.handlers.base.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

import net.omniblock.network.handlers.Handlers;
import net.omniblock.network.handlers.base.sql.type.TableType;
import net.omniblock.network.library.addons.configaddon.Factory.ConfigType;
import net.omniblock.network.library.utils.TextUtil;

public class Database {

	public static Connection conn = null;

	public final static int UUIDHELPER_DATA_BY_NAME = 0x0;
	public final static int UUIDHELPER_DATA_BY_ONLINE_UUID = 0x1;
	public final static int UUIDHELPER_DATA_BY_OFFLINE_UUID = 0x2;

	public static boolean makeConnection() {

		Bukkit.getConsoleSender().sendMessage(TextUtil.format("&8------------------[MySQL]--------------------"));

		String host = ConfigType.DATABASE.getConfig().getString("database.mysql.host");
		String port = ConfigType.DATABASE.getConfig().getString("database.mysql.port");
		String user = ConfigType.DATABASE.getConfig().getString("database.mysql.user");
		String pass = ConfigType.DATABASE.getConfig().getString("database.mysql.pass");
		String database = ConfigType.DATABASE.getConfig().getString("database.mysql.database");

		if (host == null) {
			Handlers.LOGGER.sendError("&c[!] &fEl host no pudo ser obtenido (null)");
		}

		if (port == null) {
			Handlers.LOGGER.sendError("&c[!] &fEl puerto no pudo ser obtenido (null)");
		}

		if (user == null) {
			Handlers.LOGGER.sendError("&c[!] &fEl usuario no pudo ser obtenido (null)");
		}

		if (pass == null) {
			Handlers.LOGGER.sendError("&c[!] &fLa contrasena no pudo ser obtenida (null)");
		}

		if (database == null) {
			Handlers.LOGGER.sendError("&c[!] &fLa base de datos no pudo ser obtenida (null)");
		}

		if (host != null && port != null && user != null && pass != null && database != null) {

			Handlers.LOGGER.sendMessageToConsole("Host: " + host, "Puerto: " + port, "Base de Datos: " + database,
					"Usuario: " + user, "Pass: " + TextUtil.replaceAllTextWith(pass, '*'));
			Handlers.LOGGER.sendMessageToConsole("&eEstableciendo conexion...");

			String URL = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true";

			try {

				conn = (Connection) DriverManager.getConnection(URL, user, pass);
				Handlers.LOGGER.sendMessageToConsole("&aConexion establecida!",
						"&eCreando tablas, en caso de ser necesario...",
						"&8---------------------------------------------");

				Statement stm = conn.createStatement();

				for (TableType table : TableType.values()) {
					table.getCreator().make(stm);
				}

				stm.close();
				return true;

			} catch (SQLException e) {
				Handlers.LOGGER.sendMessageToConsole("&c[!] &fError al establecer conexion:");
				e.printStackTrace();
				return false;
			}

		} else {

			Handlers.LOGGER.sendError(
					"Ha ocurrido un problema con los datos para conectar a la base de datos. Por favor revisa que todo este correctamente configurado.");
			return false;

		}
	}

	public static Connection getConnection() {
		return conn;
	}
}
