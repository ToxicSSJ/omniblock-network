package net.omniblock.network.library.utils;

import org.bukkit.Bukkit;

/**
 * 
 * Esta clase contiene el metodo general para garantizar un reloadeo completo
 * del servidor a base de reinicialización.
 * 
 * @author zlToxicNetherlz
 *
 */
public class RestarterUtil {

	/**
	 * 
	 * Para la funcionalidad de este sistema es necesario que se encuentre el
	 * nuevo script de reinicio de sistemas dentro del servidor con el fin de al
	 * realizar el shutdown, el servidor pueda ser inicializado correctamente.
	 * 
	 */
	public static void sendRestart() {

		Bukkit.shutdown();

	}

}
