package net.omniblock.network.systems;

public class AdapterPatcher {

	public static void setup() {

		CommandPatcher.setup();
		EventPatcher.setup();
		ActionsPatcher.setup();

	}

}
