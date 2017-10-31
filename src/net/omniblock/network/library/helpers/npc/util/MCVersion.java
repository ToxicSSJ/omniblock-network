package net.omniblock.network.library.helpers.npc.util;

public class MCVersion {

	@SuppressWarnings("unused")
	private static int COMPATIBLE_VERSION = 1000;
	public static final MCVersion UNKOWN = new MCVersion("", 1000);
	public static final MCVersion v1_7 = new MCVersion("1_7_R1", 0);
	public static final MCVersion v1_8_HACK = new MCVersion("1_7_R4", 1);
	public static final MCVersion v1_8 = new MCVersion("1_8_R1", 2);

	@SuppressWarnings("unused")
	private final String name;
	private final int version;
	private boolean compatible;

	private MCVersion(String name, int version) {
		this.name = name;
		this.version = version;
		try {
			if (!name.isEmpty()) {
				Class.forName("net.minecraft.server." + name + ".World");
			}

			this.setCompatible(true);
			COMPATIBLE_VERSION = version;
		} catch (Exception e) {
			this.setCompatible(false);
		}
	}

	public static boolean isCompatible(MCVersion version) {
		return version == MCVersion.v1_8 || version == MCVersion.v1_8_HACK;
	}

	public int getVersion() {
		return version;
	}

	public boolean isCompatible() {
		return compatible;
	}

	public void setCompatible(boolean compatible) {
		this.compatible = compatible;
	}

}
