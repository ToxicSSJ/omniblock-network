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
	      STRIKE_WORDS.add("cabrón");
	      STRIKE_WORDS.add("cábron");
	      STRIKE_WORDS.add("cábrón");
	      STRIKE_WORDS.add("cabrn");
	      STRIKE_WORDS.add("cábrn");
	      STRIKE_WORDS.add("cbron");
	      STRIKE_WORDS.add("cbrón");
	      STRIKE_WORDS.add("puto");
	      STRIKE_WORDS.add("púto");
	      STRIKE_WORDS.add("püto");
	      STRIKE_WORDS.add("pútó");
	      STRIKE_WORDS.add("pütó");
	      STRIKE_WORDS.add("putó");
	      STRIKE_WORDS.add("puta");
	      STRIKE_WORDS.add("púta");
	      STRIKE_WORDS.add("püta");
	      STRIKE_WORDS.add("pútá");
	      STRIKE_WORDS.add("pütá");
	      STRIKE_WORDS.add("putá");
	      STRIKE_WORDS.add("pta");
	      STRIKE_WORDS.add("ptá");
	      STRIKE_WORDS.add("hpt");
	      STRIKE_WORDS.add("mamahuevo");
	      STRIKE_WORDS.add("mamahuebo");
	      STRIKE_WORDS.add("mamauevo");
	      STRIKE_WORDS.add("coño");
	      STRIKE_WORDS.add("cóño");
	      STRIKE_WORDS.add("coñó");
	      STRIKE_WORDS.add("cóñó");
	      STRIKE_WORDS.add("mmg");
	      STRIKE_WORDS.add("hijodp");
	      STRIKE_WORDS.add("hijadp");
	      STRIKE_WORDS.add("fuck");
	      STRIKE_WORDS.add("fúck");
	      STRIKE_WORDS.add("fuc");
	      STRIKE_WORDS.add("fuk");
	      STRIKE_WORDS.add("suck");
	      STRIKE_WORDS.add("súck");
	      STRIKE_WORDS.add("sück");
	      STRIKE_WORDS.add("suc");
	      STRIKE_WORDS.add("suk");
	      STRIKE_WORDS.add("verga");
	      STRIKE_WORDS.add("berga");
	      STRIKE_WORDS.add("vérga");
	      STRIKE_WORDS.add("vergá");
	      STRIKE_WORDS.add("vérgá");
	      STRIKE_WORDS.add("chupala");
	      STRIKE_WORDS.add("chúpala");
	      STRIKE_WORDS.add("chúpála");
	      STRIKE_WORDS.add("chúpálá");
	      STRIKE_WORDS.add("chüpala");
	      STRIKE_WORDS.add("chüpála");
	      STRIKE_WORDS.add("chüpálá");
	      STRIKE_WORDS.add("chupála");
	      STRIKE_WORDS.add("chupálá");
	      STRIKE_WORDS.add("chupalá");
	      STRIKE_WORDS.add("chúpalá");
	      STRIKE_WORDS.add("chüpalá");
	      STRIKE_WORDS.add("zorra");
	      STRIKE_WORDS.add("zórra");
	      STRIKE_WORDS.add("zorrá");
	      STRIKE_WORDS.add("zórrá");
	      STRIKE_WORDS.add("bitch");
	      STRIKE_WORDS.add("bítch");
	      STRIKE_WORDS.add("lmao");
	      STRIKE_WORDS.add("nazi");
	      STRIKE_WORDS.add("názi");
	      STRIKE_WORDS.add("nazí");
	      STRIKE_WORDS.add("nází");
	      STRIKE_WORDS.add("yihad");
	      STRIKE_WORDS.add("yíhad");
	      STRIKE_WORDS.add("yihád");
	      STRIKE_WORDS.add("yíhád");
	      STRIKE_WORDS.add("isis");
	      STRIKE_WORDS.add("cojon");
	      STRIKE_WORDS.add("cojón");
	      STRIKE_WORDS.add("cójon");
	      STRIKE_WORDS.add("cójón");
	      STRIKE_WORDS.add("picha");
	      STRIKE_WORDS.add("pícha");
	      STRIKE_WORDS.add("pichá");
	      STRIKE_WORDS.add("píchá");
	      STRIKE_WORDS.add("poronga");
	      STRIKE_WORDS.add("póronga");
	      STRIKE_WORDS.add("pórónga");
	      STRIKE_WORDS.add("póróngá");
	      STRIKE_WORDS.add("pórongá");
	      STRIKE_WORDS.add("porónga");
	      STRIKE_WORDS.add("poróngá");
	      STRIKE_WORDS.add("maricon");
	      STRIKE_WORDS.add("maricón");
	      STRIKE_WORDS.add("marícón");
	      STRIKE_WORDS.add("márícón");
	      STRIKE_WORDS.add("máricon");
	      STRIKE_WORDS.add("márícon");
	      STRIKE_WORDS.add("marícon");
	      STRIKE_WORDS.add("bujarra");
	      STRIKE_WORDS.add("bújarra");
	      STRIKE_WORDS.add("bújárra");
	      STRIKE_WORDS.add("bújárrá");
	      STRIKE_WORDS.add("bujarrá");
	      STRIKE_WORDS.add("bujárrá");
	      STRIKE_WORDS.add("bujárra");
	      STRIKE_WORDS.add("incesto");
	      STRIKE_WORDS.add("íncesto");
	      STRIKE_WORDS.add("íncésto");
	      STRIKE_WORDS.add("íncéstó");
	      STRIKE_WORDS.add("incestó");
	      STRIKE_WORDS.add("incéstó");
	      STRIKE_WORDS.add("incésto");
	      STRIKE_WORDS.add("parida");
	      STRIKE_WORDS.add("parido");
	      STRIKE_WORDS.add("aborto");
          STRIKE_WORDS.add("sida");
          STRIKE_WORDS.add("dick");
	      STRIKE_WORDS.add("polla");
	      STRIKE_WORDS.add("Truchón");
	      STRIKE_WORDS.add("Trúchón");
	      STRIKE_WORDS.add("Trúchon");
	      STRIKE_WORDS.add("lameculos");
	      STRIKE_WORDS.add("panocha");
	      STRIKE_WORDS.add("cipote");
	      STRIKE_WORDS.add("pollón");

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
