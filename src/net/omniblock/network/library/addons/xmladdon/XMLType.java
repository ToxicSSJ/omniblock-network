package net.omniblock.network.library.addons.xmladdon;

import java.util.List;

import net.omniblock.network.library.addons.sshaddon.InternalAdapter;

public enum XMLType {

	PLUGINS_VERSIONS("versions.xml"), PERMISSIONS("permissions.xml"),

	;

	private String path;

	XMLType(String path) {

		this.path = path;

	}

	public XMLReader getReader() {

		return new XMLReader(getXml());

	}

	public String getXml() {

		List<String> lines = InternalAdapter.DOWNLOADER.getNetworkXML(this);
		return String.join("", lines);

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
