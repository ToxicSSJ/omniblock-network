package net.omniblock.network.library.utils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

/**
 * @author Wirlie
 *
 */
public class TitleUtil {

	public static class TitleFormat {

		private int fadeIn = 10;
		private int stay = 10;
		private int fadeOut = 10;

		private String title = "";
		private String subtitle = "";

		public TitleFormat(int fadeIn, int stay, int fadeOut, String title, String subtitle) {

			this.fadeIn = fadeIn;
			this.stay = stay;
			this.fadeOut = fadeOut;

			this.title = title;
			this.subtitle = subtitle;

		}

		public void send(Player player) {
			TitleUtil.sendTitleToPlayer(player, fadeIn, stay, fadeOut, title, subtitle);
		}

		public int getFadeIn() {
			return fadeIn;
		}

		public void setFadeIn(int fadeIn) {
			this.fadeIn = fadeIn;
		}

		public int getStay() {
			return stay;
		}

		public void setStay(int stay) {
			this.stay = stay;
		}

		public int getFadeOut() {
			return fadeOut;
		}

		public void setFadeOut(int fadeOut) {
			this.fadeOut = fadeOut;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getSubtitle() {
			return subtitle;
		}

		public void setSubtitle(String subtitle) {
			this.subtitle = subtitle;
		}

	}

	/**
	 * Enviar un titulo a un jugador. Este método es compatible hasta que el
	 * método o constructor NMS sea cambiado. 
	 * 
	 * @param p
	 *            Jugador a enviar el título.
	 * @param fadeIn
	 *            Tiempo de aparición en segundos.
	 * @param stay
	 *            Tiempo en el que permanecerá activo en segundos.
	 * @param fadeOut
	 *            Tiempo de desaparición en segundos.
	 * @param title
	 *            Título
	 * @param subtitle
	 *            Subtítulo
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sendTitleToPlayer(Player p, int fadeIn, int stay, int fadeOut, String title, String subtitle) {
		try {
			Class<Enum> enumTitleAction = (Class<Enum>) ReflectionUtil
					.getNMSClass("PacketPlayOutTitle$EnumTitleAction");
			Enum enumTitleActionFieldTIMES = Enum.valueOf(enumTitleAction, "TIMES");
			Enum enumTitleActionFieldSUBTITLE = Enum.valueOf(enumTitleAction, "SUBTITLE");
			Enum enumTitleActionFieldTITLE = Enum.valueOf(enumTitleAction, "TITLE");

			Class<?> packetClass = ReflectionUtil.getNMSClass("PacketPlayOutTitle");

			Constructor<?> packetConstructor1 = packetClass.getConstructor(
					ReflectionUtil.getNMSClass("PacketPlayOutTitle$EnumTitleAction"),
					ReflectionUtil.getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
			Constructor<?> packetConstructor2 = packetClass.getConstructor(
					ReflectionUtil.getNMSClass("PacketPlayOutTitle$EnumTitleAction"),
					ReflectionUtil.getNMSClass("IChatBaseComponent"));

			Object pconn = ReflectionUtil.getPlayerConnection(p);
			Method sendPacket = pconn.getClass().getDeclaredMethod("sendPacket", ReflectionUtil.getNMSClass("Packet"));

			Object packetTIMES = packetConstructor1.newInstance(enumTitleActionFieldTIMES, null, fadeIn, stay, fadeOut);
			// con.sendPacket(new
			// PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES,
			// null, fadeIn, stay, fadeOut));
			sendPacket.invoke(pconn, packetTIMES);

			Class<?> chatSerializer = ReflectionUtil.getNMSClass("IChatBaseComponent$ChatSerializer");
			Method aMethod = chatSerializer.getDeclaredMethod("a", String.class);

			Object packetSUBTITLE = packetConstructor2.newInstance(enumTitleActionFieldSUBTITLE,
					aMethod.invoke(chatSerializer, "{\"text\": \"" + subtitle + "\"}"));
			// con.sendPacket(new
			// PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
			// IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle +
			// "\"}")));
			sendPacket.invoke(pconn, packetSUBTITLE);

			Object packetTITLE = packetConstructor2.newInstance(enumTitleActionFieldTITLE,
					aMethod.invoke(chatSerializer, "{\"text\": \"" + title + "\"}"));
			// con.sendPacket(new
			// PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
			// IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title +
			// "\"}")));
			sendPacket.invoke(pconn, packetTITLE);

		} catch (SecurityException | NoSuchMethodException | NoSuchFieldException | IllegalArgumentException
				| IllegalAccessException | InvocationTargetException | ClassNotFoundException
				| InstantiationException e) {
			e.printStackTrace();
		}
	}
}