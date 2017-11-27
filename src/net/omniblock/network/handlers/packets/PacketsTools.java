package net.omniblock.network.handlers.packets;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Esta clase tiene funciones fundamentales para el manejo de paquetes.
 * 
 * @author zlToxicNetherlz & Wirlie
 *
 */
public class PacketsTools {

	protected static Map<String, String> boosters = new HashMap<String, String>();
	
	public static Set<Map.Entry<String, String>> getBoosters(){
		return boosters.entrySet();
	}
	
	public static boolean isBoosterEnabled(String gametype) {
		return boosters.containsKey(gametype);
	}
	
	public static String getPlayerOfBooster(String gametype) {
		if(isBoosterEnabled(gametype)) return boosters.get(gametype);
		return "Unknow";
	}
	
	public static void setBoosters(String gameboosted) {
		
		boosters.clear();
		
		String[] data = gameboosted.split(",");
		
		for(String k : data) {
			
			String gametype = k.split("#")[0];
			String playername = k.split("#")[1];
			
			boosters.put(gametype, playername);
			
		}
		
	}
	
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
