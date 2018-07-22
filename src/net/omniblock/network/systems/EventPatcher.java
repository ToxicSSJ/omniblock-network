package net.omniblock.network.systems;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.library.helpers.inventory.InventoryBuilderListener;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.adapters.*;
import net.omniblock.network.systems.rank.RankManager;

public class EventPatcher {

	public static final String YOURE_BANNED = TextUtil.format(

			"\n&4&l¡Estás baneado!" + " \n" + "&7Nuestro registro ha indicado que posees un baneo general\n"
					+ "&7porque no has cumplido con las normas o politicas de Omniblock Network. \n" + " \n"
					+ "&8Información del Baneo:\n" + "&6&lCODIGO:&7 VAR_BAN_HASH\n" + "&6&lHASTA:&7 VAR_TO_DATE\n"
					+ "&6&lRAZÓN:&7 VAR_REASON\n" + " \n"
					+ "&7Cualquier duda, queja o pregunta con respecto a la politica\n"
					+ "&7o el manejo de sanciones, incluyendo la tuya. Dirigete al\n"
					+ "&7siguiente link: &bwww.omniblock.net/baneos/\n"

	);

	public static final String YOURE_BANNED_WITHOUT_VARS = TextUtil.format(

			"\n&4&l¡Estás baneado!" + " \n" + "&7Nuestro registro ha indicado que posees un baneo general\n"
					+ "&7porque no has cumplido con las normas o politicas de Omniblock Network.\n " + " \n"
					+ "&7Cualquier duda, queja o pregunta con respecto a la politica\n"
					+ "&7o el manejo de sanciones, incluyendo la tuya. Dirigete al\n"
					+ "&7siguiente link: &bwww.omniblock.net/baneos/\n"

	);

	public static void setup() {

		OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(new GameCHATAdapter(),
				OmniNetwork.getInstance());
		OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(new GameLEAVEAdapter(),
				OmniNetwork.getInstance());
		OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(new GameTABAdapter(),
				OmniNetwork.getInstance());
		OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(new GameJOINAdapter(),
				OmniNetwork.getInstance());
		OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(new GameSPAMAdapter(),
				OmniNetwork.getInstance());
		OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(new GameSTRIKEAdapter(),
				OmniNetwork.getInstance());
		OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(new GameCMDAdapter(),
				OmniNetwork.getInstance());
		OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(new RankManager(),
				OmniNetwork.getInstance());
		OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(new InventoryBuilderListener(),
				OmniNetwork.getInstance());

		// Listener SCREENS_LINSTENER = new ScreensEvents();
		// OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(SCREENS_LINSTENER,
		// OmniNetwork.getInstance());

	}

}
