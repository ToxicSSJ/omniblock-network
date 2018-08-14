package net.omniblock.network.systems;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.library.helpers.inventory.InventoryBuilderListener;

@Deprecated
public class EventPatcher {

	public static void setup() {

		OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(new InventoryBuilderListener(),
				OmniNetwork.getInstance());

		// Listener SCREENS_LINSTENER = new ScreensEvents();
		// OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(SCREENS_LINSTENER,
		// OmniNetwork.getInstance());

	}

}
