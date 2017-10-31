package net.omniblock.network.handlers.packets;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * Esta clase tiene funciones fundamentales para el manejo de paquetes.
 * 
 * @author zlToxicNetherlz & Wirlie
 *
 */
public class PacketsTools {

	/**
	 * 
	 * Este metodo ejecuta el juego Skywars por medio de los datos propiciados y
	 * usa reflection con el fin de no utilizar como dependencia el Skywars y
	 * evitar el manejo de tantas dependencias.
	 * 
	 * @param data
	 *            Los argumentos con el cual se iniciará el Skywars.
	 */
	public static void reflectionSkywarsExecute(String data) {
		try {
			Class<?> networkDataClass = Class.forName("net.omniblock.skywars.network.NetworkData");
			Field broadcasterField = networkDataClass.getField("broadcaster");
			Object broadcasterObject = broadcasterField.get(null);
			Class<?> networkBroadcasterClass = broadcasterObject.getClass();
			Method networkBroadcasterExecuteMethod = networkBroadcasterClass.getMethod("execute", String.class);
			networkBroadcasterExecuteMethod.setAccessible(true);
			networkBroadcasterExecuteMethod.invoke(broadcasterObject, data);
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException
				| SecurityException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
