package net.omniblock.network.handlers.updater.type;

/**
 * 
 * Este enumerador contiene la id, el nombre y el nombre del artefacto (jar) de
 * los diferentes plugins de Omniblock que se pueden actualizar.
 * 
 * @author zlToxicNetherlz
 *
 */
public enum PluginType {

	OMNINETWORK(0, "OmniNetwork", "OmniNetwork.jar"), SKYWARS(1, "SkyWars", "SkyWars.jar"),

	;

	private int id;

	private String name;
	private String jarfile;

	PluginType(int id, String name, String jarfile) {

		this.id = id;

		this.name = name;
		this.jarfile = jarfile;

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getJarfile() {
		return jarfile;
	}

	public void setJarfile(String jarfile) {
		this.jarfile = jarfile;
	}

}
