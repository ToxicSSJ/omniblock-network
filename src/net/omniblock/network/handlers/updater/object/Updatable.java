package net.omniblock.network.handlers.updater.object;

import org.bukkit.plugin.Plugin;

import net.omniblock.network.handlers.updater.UpdateHandler;
import net.omniblock.network.handlers.updater.type.PluginType;
import net.omniblock.network.library.addons.xmladdon.XMLType;

/**
 * 
 * Esta interface la cual es y debe ser implementada por todo plugin que se
 * puede actualizar. Es decir que esta extendida por JavaPlugin debe también la
 * clase principal implementar esta clase y en su metodo <u>onEnable</u> debe
 * llevar el metodo <u>Updatable#update(type, plugin)</u> el cual retornará el
 * boleano que debe ser utilizado o para finalizar el proceso de inicio porque
 * debe ser actualizado <strong>true</strong>, o para continuar el proceso de
 * inicio porque esta al dia <strong>false</strong>.
 * 
 * @author zlToxicNetherlz
 *
 */
public abstract interface Updatable {

	/**
	 * 
	 * Este metodo es el encargado de comprobar si un plugin necesita de una
	 * actualización por medio del uso de la clase UpdateHandler.
	 * 
	 * @param type
	 *            El tipo de plugin que se desea actualizar.
	 * @param plugin
	 *            La instancia del plugin.
	 * @return <strong>true</strong> si se actualizará el plugin.
	 * @see UpdateHandler
	 */
	public default boolean update(PluginType type, Plugin plugin) {

		boolean status = UpdateHandler.promptUpdate(XMLType.PLUGINS_VERSIONS, type, plugin);

		if (status) {

			plugin.getServer().getPluginManager().disablePlugin(plugin);
			return true;

		}

		return false;

	}

}
