package net.omniblock.network.handlers.base.sql.type;

public enum GadgetType {

	OMNI_BLOCK_HEAD("H_0001"),

	;

	private String hashid;

	GadgetType(String hashid) {

		this.hashid = hashid;

	}

	public String getHashid() {
		return hashid;
	}

}
