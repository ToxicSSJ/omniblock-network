package net.omniblock.network.systems;

@Deprecated
public class AdapterPatcher {

	public static void setup() {

		CommandPatcher.setup();
		EventPatcher.setup();
		ActionsPatcher.setup();

	}

}
