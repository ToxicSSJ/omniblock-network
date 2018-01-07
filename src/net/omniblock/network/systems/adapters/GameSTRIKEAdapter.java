package net.omniblock.network.systems.adapters;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GameSTRIKEAdapter implements Listener {

	public static final String DEFAULT_STRIKE = "*****";

	public static Set<String> STRIKE_WORDS = new HashSet<String>();

	static {

		STRIKE_WORDS.add("hijodeputa");
		STRIKE_WORDS.add("cabron");
		STRIKE_WORDS.add("puto");
		STRIKE_WORDS.add("puta");
		STRIKE_WORDS.add("hpt");
		STRIKE_WORDS.add("mamahuevo");
		STRIKE_WORDS.add("coño");
		STRIKE_WORDS.add("mmg");
		STRIKE_WORDS.add("joputa");
		STRIKE_WORDS.add("fuck");
		STRIKE_WORDS.add("suck");
		STRIKE_WORDS.add("verga");
		STRIKE_WORDS.add("chupala");
		STRIKE_WORDS.add("chupamela");
		STRIKE_WORDS.add("carajo");
		STRIKE_WORDS.add("maldito");
		STRIKE_WORDS.add("maldita");
		STRIKE_WORDS.add("zorra");

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent e) {

		if (e.isCancelled())
			return;

		e.setMessage(strike(e.getPlayer(), e.getMessage()));

	}

	public String strike(Player player, String message) {

		String filtermsg = new String(message);

		if (!message.contains(" ")) {

			for (String m : STRIKE_WORDS) {

				if (filtermsg.equals(m) || contains(filtermsg, m) || filtermsg.contains(m)) {
					filtermsg = filtermsg.replaceAll(filtermsg, DEFAULT_STRIKE);
					break;
				}

			}

			return filtermsg;

		}

		String[] crackmsg = message.split(" ");

		for (String k : crackmsg) {

			for (String m : STRIKE_WORDS) {

				if (k.equals(m) || contains(k, m) || k.contains(m)) {
					filtermsg = filtermsg.replaceAll(k, DEFAULT_STRIKE);
					continue;
				}

			}

		}

		return filtermsg;

	}

	private boolean contains(String query, String text) {
		String[] deli = text.split("[.\\s,?;]+");
		for (int i = 0; i < deli.length; i++)
			if (query.equals(deli[i]))
				return true;

		return false;
	}

}
