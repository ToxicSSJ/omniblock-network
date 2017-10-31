package net.omniblock.network.handlers.updater;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import net.omniblock.network.handlers.Handlers;
import net.omniblock.network.handlers.updater.type.PluginType;
import net.omniblock.network.library.addons.sshaddon.InternalAdapter;
import net.omniblock.network.library.addons.xmladdon.XMLReader;
import net.omniblock.network.library.addons.xmladdon.XMLType;

/**
 * 
 * Esta clase es la encargada de manejar los metodos que utilizan las
 * actualizaciones de los plugins.
 * 
 * @author zlToxicNetherlz
 *
 */
public class UpdateHandler {

	/**
	 * 
	 * Este metodo procesa una actualización en base a diferentes argumentos que
	 * son los que definen si dicho plugin se debe o no actualizar. En todo caso
	 * dicho sistema actualiza automaticamente el plugin en cuestión y también
	 * es de facil manejo.
	 * 
	 * @param xml
	 *            El XMLType donde se almacena los tags del artefacto para ser
	 *            leido y procesado partiendo de la version del plugin y la
	 *            version encontrada en el xml remoto.
	 * @param type
	 *            El tipo de plugin que se quiere actualizar.
	 * @param plugin
	 *            La instancia del plugin que se quiere actualizar.
	 * @return <strong>true</strong> si actualizó el plugin.
	 */
	public static boolean promptUpdate(XMLType xml, PluginType type, Plugin plugin) {

		final XMLReader reader = xml.getReader();

		final Server server = Bukkit.getServer();
		final String version = reader.get("Updater//Name[.=\"" + type.getName() + "\"]/../Version");
		final String artifact = reader.get("Updater//Name[.=\"" + type.getName() + "\"]/../Artifact");

		if (!version.equalsIgnoreCase(plugin.getDescription().getVersion())) {

			Handlers.LOGGER.sendWarning("El siguiente plugin no esta actualizado: " + type.getName());
			Handlers.LOGGER.sendWarning("Actualizando el plugin automaticamente! Esto puede tomar unos segundos...");

			new Thread(new Runnable() {

				File dir = new File(UpdateHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath()
						.replaceAll("%20", " "));
				File plugins = new File(dir.getParentFile().getPath());

				public void run() {

					Date aftertime = new Date();

					sleep(3);

					if (!plugins.exists())
						plugins.mkdir();

					InternalAdapter.DOWNLOADER.downloadUpdate(type.getName(), artifact,
							plugins + File.separator + type.getJarfile());

					Plugin plugin = enablePlugin(server, type.getJarfile(), type.getName(), plugins);

					if (plugin == null) {

						System.out.println(
								"CRITICO: No ha sido posible actualizar el plugin porque ha ocurrido un error!");
						return;

					}

					Date beforetime = new Date();
					long difference = aftertime.getTime() - beforetime.getTime();

					System.out.println("Se ha actualizado el plugin: " + type.getName()
							+ " correctamente! (tiempo de actualización -> "
							+ TimeUnit.MILLISECONDS.toSeconds(difference) + ")");
					return;

				}

				public void sleep(int seconds) {

					try {

						TimeUnit.SECONDS.sleep(seconds);

					} catch (InterruptedException e) {

						e.printStackTrace();

					}

				}

			}).start();

			return true;

		}

		return false;

	}

	/**
	 * 
	 * Este metodo se encarga de iniciar un plugin por medio de diferentes
	 * funciones sin accesar directamente con el plugin como tál. Esto es
	 * fundamental para el caso de que el plugin el cual se va a actualizar sea
	 * quien posee el auto-actualizador. En este caso sería el OmniNetwork.
	 * 
	 * @param server
	 *            La instancia de Server. (Se puede crear por reflection)
	 * @param jarfile
	 *            El archivo .jar para inicializarlo.
	 * @param name
	 *            El nombre del plugin.
	 * @param dir
	 *            El directorio donde se encuentra (/plugins/)
	 * @return Plugin, Que es la instancia del plugin ya inicializado.
	 */
	private static Plugin enablePlugin(Server server, String jarfile, String name, File dir) {

		try {

			File file = getPluginFile(dir, jarfile);

			server.getPluginManager().loadPlugin(file);
			server.getPluginManager().enablePlugin(Bukkit.getServer().getPluginManager().getPlugin(name));

			return server.getPluginManager().getPlugin(name);

		} catch (Throwable e) {

			new RuntimeException("Se ha producido un error al actualizar el plugin: " + name);

		}

		return null;

	}

	/**
	 * 
	 * Este metodo ayuda a obtener el archivo de un plugin por medio de su
	 * directorio y del nombre del jar.
	 * 
	 * @param dir
	 *            El directorio donde se encuentra el jar.
	 * @param artifactjar
	 *            El nombre del jar.
	 * @return
	 */
	private static File getPluginFile(File dir, String artifactjar) {

		File pluginFile = new File(dir, artifactjar);
		return pluginFile;

	}

}
