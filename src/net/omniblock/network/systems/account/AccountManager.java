package net.omniblock.network.systems.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.handlers.base.bases.type.AccountBase;
import net.omniblock.network.handlers.base.bases.type.BoosterBase;
import net.omniblock.network.handlers.base.bases.type.RankBase;
import net.omniblock.network.library.helpers.ItemBuilder;
import net.omniblock.network.library.helpers.RandomFirework;
import net.omniblock.network.library.helpers.inventory.InventoryBuilder;
import net.omniblock.network.library.helpers.inventory.InventoryBuilder.Action;
import net.omniblock.network.library.helpers.inventory.InventoryBuilder.RowsIntegers;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.account.items.BoosterType;
import net.omniblock.network.systems.account.items.MembershipType;
import net.omniblock.network.systems.account.items.NetworkBoosterType;
import net.omniblock.network.systems.rank.type.RankType;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.data.PacketStructure.DataType;
import net.omniblock.packets.network.structure.packet.RequestPlayerStartNetworkBoosterPacket;
import net.omniblock.packets.network.structure.packet.ResposePlayerNetworkBoosterPacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.network.tool.object.PacketResponder;

/**
 * 
 * Esta clase se encarga del manejo total de GUIS y configuraciones de la cuenta
 * de usuario, así como el botín con todos sus subsistemas y también, esta clase
 * se encarga del manejo básico de los comandos de configuración que se manejan
 * para el cambio del perfíl o cuenta de un usuario.
 * 
 * @author zlToxicNetherlz
 *
 */
public class AccountManager {

	/**
	 * Esta variable tiene como valor los segundos que tiene un dia.
	 */
	public static final int SECONDS_IN_A_DAY = 24 * 60 * 60;

	/**
	 * 
	 * Este metodo se encarga de mostrar una GUI a un usuario con toda la
	 * configuración disponible e interactiva que el usuario puede hacer, de tál
	 * modo que pueda desactivar o activar distintas opciones.
	 * 
	 * @param player
	 *            El jugador al cual se le abrirá la GUI.
	 * @param backinv
	 *            El inventario al cual se debe volver cuando se presione el
	 *            item "volver".
	 */
	public static void displayAccountGUI(Player player, InventoryBuilder backinv) {

		InventoryBuilder ib = new InventoryBuilder(TextUtil.format("&6&lConfiguración de Cuenta"), 6 * 9, false);

		ib.addItem(
				new ItemBuilder(Material.PAPER).amount(1).name(TextUtil.format("&8&lInformación:")).lore("")
						.lore(TextUtil.format("&9&m-&r &7Este sistema se basa en el clasico"))
						.lore(TextUtil.format("&7activar o desactivar, donde podrás elegir"))
						.lore(TextUtil.format("&7que aspectos de tu perfíl como jugador deseas"))
						.lore(TextUtil.format("&7utilizar o simplemente no utilizarlos.")).lore("").build(),
				19);

		ib.addItem(new ItemBuilder(Material.ARROW).amount(1).name(TextUtil.format("&7Volver")).build(), 20,
				new Action() {

					@Override
					public void click(ClickType click, Player player) {

						backinv.open(player);
						return;

					}

				});

		ib.addItem(new ItemBuilder(Material.NAME_TAG).amount(1).name(TextUtil.format("&9&l(!) &3Login por IP")).lore("")
				.lore(TextUtil.format("&f&lNota:&r &7Este sistema no aplica si eres"))
				.lore(TextUtil.format("&7un usuario premium.")).lore("")
				.lore(TextUtil.format("&8&nDeshabilitado:&r &7Es necesario colocar la clave"))
				.lore(TextUtil.format("&7cada vez que se entre al servidor.")).lore("")
				.lore(TextUtil.format("&2&nHabilitado:&r &7Se logea automaticamente"))
				.lore(TextUtil.format("&7al jugador con la ultima IP conocida.")).lore("").build(), 15);

		ib.addItem(new ItemBuilder(Material.SIGN).amount(1).name(TextUtil.format("&9&l(!) &3Mensajes Privados"))
				.lore("").lore(TextUtil.format("&8&nDeshabilitado:&r &7No permite que los demás"))
				.lore(TextUtil.format("&7te envien mensajes privados, excepto tus amigos.")).lore("")
				.lore(TextUtil.format("&2&nHabilitado:&r &7Permite el envio de mensajes"))
				.lore(TextUtil.format("&7privados de todo el mundo hacia ti.")).lore("").build(), 24);

		ib.addItem(new ItemBuilder(Material.JUKEBOX).amount(1).name(TextUtil.format("&9&l(!) &3Sonidos de Texturas"))
				.lore("").lore(TextUtil.format("&8&nDeshabilitado:&r &7Evita reproducir cualquier"))
				.lore(TextUtil.format("&7sonido de un minijuego que use texturas hacia"))
				.lore(TextUtil.format("&7ti, reduciendo todo el ruido de dichos sonidos.")).lore("")
				.lore(TextUtil.format("&2&nHabilitado:&r &7Escucharás todos los sonidos"))
				.lore(TextUtil.format("&7de un minijuego que utilice texturas.")).lore("").build(), 33);

		ib.addItem(new ItemBuilder(Material.TNT).amount(1).name(TextUtil.format("&9&l(!) &3Peticiones de Amistad"))
				.lore("").lore(TextUtil.format("&8&nDeshabilitado:&r &7No permite que las personas"))
				.lore(TextUtil.format("&7te puedan enviar solicitud de amistad para ser tus"))
				.lore(TextUtil.format("&7amigos.")).lore("")
				.lore(TextUtil.format("&2&nHabilitado:&r &7Cualquier persona podrá enviarte"))
				.lore(TextUtil.format("&7solicitudes de amistad.")).lore("").build(), 42);

		String tags = AccountBase.getTags(player);

		boolean iplogin = hasTag(AccountTagType.IP_LOGIN, tags), privatemsg = hasTag(AccountTagType.PRIVATE_MSG, tags),
				texturesound = hasTag(AccountTagType.TEXTURE_SOUND, tags),
				friendrequest = hasTag(AccountTagType.FRIEND_REQUEST, tags);

		ib.addItem(new ItemBuilder(Material.WOOL).durability(iplogin ? (short) 5 : (short) 7).amount(1)
				.name(TextUtil.format("&7Estado Actual: &" + (iplogin ? "aHabilitado" : "7Deshabilitado"))).build(), 16,
				new Action() {

					@Override
					public void click(ClickType click, Player player) {

						if (iplogin) {
							AccountBase.removeTag(player, "iplogin");
						} else {
							AccountBase.addTag(player, "iplogin");
						}

						displayAccountGUI(player, backinv);
						return;

					}

				});

		ib.addItem(new ItemBuilder(Material.WOOL).durability(privatemsg ? (short) 5 : (short) 7).amount(1)
				.name(TextUtil.format("&7Estado Actual: &" + (privatemsg ? "aHabilitado" : "7Deshabilitado"))).build(),
				25, new Action() {

					@Override
					public void click(ClickType click, Player player) {

						if (privatemsg) {
							AccountBase.removeTag(player, "privatemsg");
						} else {
							AccountBase.addTag(player, "privatemsg");
						}

						displayAccountGUI(player, backinv);
						return;

					}

				});

		ib.addItem(new ItemBuilder(Material.WOOL).durability(texturesound ? (short) 5 : (short) 7).amount(1)
				.name(TextUtil.format("&7Estado Actual: &" + (texturesound ? "aHabilitado" : "7Deshabilitado")))
				.build(), 34, new Action() {

					@Override
					public void click(ClickType click, Player player) {

						if (texturesound) {
							AccountBase.removeTag(player, "texturesound");
						} else {
							AccountBase.addTag(player, "texturesound");
						}

						displayAccountGUI(player, backinv);
						return;

					}

				});

		ib.addItem(new ItemBuilder(Material.WOOL).durability(friendrequest ? (short) 5 : (short) 7).amount(1)
				.name(TextUtil.format("&7Estado Actual: &" + (friendrequest ? "aHabilitado" : "7Deshabilitado")))
				.build(), 43, new Action() {

					@Override
					public void click(ClickType click, Player player) {

						if (friendrequest) {
							AccountBase.removeTag(player, "friendrequest");
						} else {
							AccountBase.addTag(player, "friendrequest");
						}

						displayAccountGUI(player, backinv);
						return;

					}

				});

		ib.open(player);
		return;

	}

	/**
	 * 
	 * Este metodo sirve para mostrarle al jugador su botín por medio de una GUI
	 * que le mostrará todos los subsistemas y los inventarios que dichos
	 * subsistemas poseen.
	 * 
	 * Además será por medio del botín donde cosas principales como cosmeticos,
	 * gadgets, rangos o boosters podrán ser activados.
	 * 
	 * @param player
	 *            El jugador al cual se le mostrará su botín.
	 */
	public static void displayLootGUI(Player player) {

		InventoryBuilder ib = new InventoryBuilder(TextUtil.format("&4&lBotín"), 6 * 9, false);

		ib.fill(new ItemBuilder(Material.STAINED_GLASS_PANE).durability((short) 15).name(TextUtil.format("&7-"))
				.build(), RowsIntegers.ROW_1);

		ib.fill(new ItemBuilder(Material.STAINED_GLASS_PANE).durability((short) 15).name(TextUtil.format("&7-"))
				.build(), RowsIntegers.ROW_6);

		ib.addItem(new ItemBuilder(Material.CHEST).amount(1).name(TextUtil.format("&aGadgets &c&l(Proximamente)"))
				.lore("").lore(TextUtil.format("&9&m-&r &7Disfruta eligiendo y luciendo diferentes"))
				.lore(TextUtil.format("&7gadgets, animaciones y cosméticos para tu estadía"))
				.lore(TextUtil.format("&7durante las lobbies y el tiempo de carga de una"))
				.lore(TextUtil.format("&7partida en cualquiera de nuestras modalidades.")).lore("").build(), 10);

		ib.addItem(new ItemBuilder(Material.ENDER_PORTAL_FRAME).amount(1).name(TextUtil.format("&6Boosters")).lore("")
				.lore(TextUtil.format("&9&m-&r &7¡Revisa los boosters que tienes disponibles"))
				.lore(TextUtil.format("&7y usalos para adquirir omnicoins mucho más rápido,"))
				.lore(TextUtil.format("&7Además existen dos tipos de Boosters en Omniblock"))
				.lore(TextUtil.format("&7uno de ellos es personal y el otro tipo es global!")).lore(TextUtil.format(""))
				.lore(TextUtil.format("&e(!) &fSi necesitas información o comprar un booster"))
				.lore(TextUtil.format("&fvisita nuestra tienda: &btienda.omniblock.net")).lore("").build(), 13,
				new Action() {

					@Override
					public void click(ClickType click, Player player) {

						displayBoostersGUI(player, ib);
						return;

					}

				});

		ib.addItem(new ItemBuilder(Material.FIREBALL).amount(1).name(TextUtil.format("&dpartículas &c&l(Proximamente)"))
				.lore("").lore(TextUtil.format("&9&m-&r &7Encuentra tu partículas preferida y disfruta"))
				.lore(TextUtil.format("&7¡teniendola activada durante los lobbies de toda la"))
				.lore(TextUtil.format("&7network!")).lore("")
				.lore(TextUtil.format("&e(!) &fPara obtener particulas compra un rango"))
				.lore(TextUtil.format("&fvisitando nuestra tienda: &btienda.omniblock.net")).lore("").build(), 16);

		ib.addItem(
				new ItemBuilder(Material.SADDLE).amount(1).name(TextUtil.format("&2Omni-Mascotas &c&l(Proximamente)"))
						.lore("").lore(TextUtil.format("&9&m-&r &7Mascotas únicas con acciones muy interesantes!"))
						.lore(TextUtil.format("&7¡Elige tu mascota y te seguirá a todas partes incluso"))
						.lore(TextUtil.format("&7en todas las modalidades de la network!")).lore("")
						.lore(TextUtil.format("&e(!) &fPara obtener mascotas compra un rango"))
						.lore(TextUtil.format("&fvisitando nuestra tienda: &btienda.omniblock.net")).lore("").build(),
				28);

		ib.addItem(new ItemBuilder(Material.GOLD_HELMET).amount(1).name(TextUtil.format("&eMembresias &6&lRANGOS!"))
				.lore("").lore(TextUtil.format("&9&m-&r &7En este apartado se guardarán todos tus"))
				.lore(TextUtil.format("&7rangos comprados que aún no tienen uso. Al comprar"))
				.lore(TextUtil.format("&7un rango desde la web, Dicho rango estará en este"))
				.lore(TextUtil.format("&7lugar a la espera de ser usado por tí. Si ya tienes"))
				.lore(TextUtil.format("&7un rango puedes usar otro rango del botín pero el"))
				.lore(TextUtil.format("&7el que tenías se remplazará por el nuevo.")).lore("")
				.lore(TextUtil.format("&f&lNota: &7Los rangos guardados expiran la cantidad"))
				.lore(TextUtil.format("&7de tiempo que tiene tu actual rango &a&l+&7 3 meses,"))
				.lore(TextUtil.format("&7si activas un rango, todos los que tengas en el botín"))
				.lore(TextUtil.format("&7se les sumará el tiempo total del rango que has activado"))
				.lore(TextUtil.format("&7para que no expiren durante el periodo de tu rango"))
				.lore(TextUtil.format("&7activado.")).lore("")
				.lore(TextUtil.format("&7¡Es decir, si no lo usas en dicho lapso de tiempo el"))
				.lore(TextUtil.format("&7rango se consumirá y desaparecerá de tu botín, Debes"))
				.lore(TextUtil.format("&7estar muy pendiente y usarlo antes de que expire!")).lore("").build(), 31,
				new Action() {

					@Override
					public void click(ClickType click, Player player) {

						displayMembershipGUI(player, ib);
						return;

					}

				});

		ib.addItem(new ItemBuilder(Material.SKULL_ITEM).amount(1).durability((short) 3).setSkullOwner("thresh3")
				.name(TextUtil.format("&9Regalos &c&l(Proximamente)")).lore("")
				.lore(TextUtil.format("&9&m-&r &7Revisa los items que te han regalado los"))
				.lore(TextUtil.format("&7demás jugadores. Si aceptas estos regalos, serán"))
				.lore(TextUtil.format("&7enviados directamente al apartado del botín donde"))
				.lore(TextUtil.format("&7pertenecen para que luego tu puedas utilizarlos.")).lore("").build(), 34);

		ib.addItem(new ItemBuilder(Material.ANVIL).amount(1).name(TextUtil.format("&7Fabricador &c&l(Proximamente)"))
				.lore("").lore(TextUtil.format("&9&m-&r &7Crea particulas, gadgets o rangos que podrás"))
				.lore(TextUtil.format("&7ir fabricando conforme vayas añadiéndoles puntos de"))
				.lore(TextUtil.format("&7torneo.")).lore("")
				.lore(TextUtil.format("&f&lNota: &7Para obtener una mayor información acerca de"))
				.lore(TextUtil.format("&7los puntos de torneo visita: &btorneos.omniblock.net")).lore("").build(), 49);

		ib.open(player);
		return;

	}

	public static void displayBoostersGUI(Player player, InventoryBuilder backinv) {

		InventoryBuilder ib = new InventoryBuilder(TextUtil.format("&6&lBoosters"), 4 * 9, false);

		ib.addItem(
				new ItemBuilder(Material.EXP_BOTTLE).amount(1).name(TextUtil.format("&aBoosters Personales")).lore("")
						.lore(TextUtil.format("&9&m-&r &7Estos boosters son los que puedes usar"))
						.lore(TextUtil.format("&7y tendrán validez unicamente en tu cuenta. La"))
						.lore(TextUtil.format("&7unica manera de conseguir estos boosters es por"))
						.lore(TextUtil.format("&7medio de logros o eventos, también pueden ser"))
						.lore(TextUtil.format("&7fabricados con puntos de torneo.")).lore("").build(),
				11, new Action() {

					@Override
					public void click(ClickType click, Player player) {

						displayBoosterSelectorGUI(player, AccountBoosterType.PERSONAL_BOOSTER, ib);
						return;

					}

				});

		ib.addItem(new ItemBuilder(Material.ENDER_PORTAL_FRAME).amount(1).name(TextUtil.format("&eNetwork Boosters"))
				.lore("").lore(TextUtil.format("&9&m-&r &7Estos boosters son globales, por lo tanto"))
				.lore(TextUtil.format("&7cuando los actives, todos los usuarios serán"))
				.lore(TextUtil.format("&7notificados que has activado un booster global para"))
				.lore(TextUtil.format("&7cierta modalidad. Además tu nombre aparecerá en la"))
				.lore(TextUtil.format("&7selección de la modalidad para darte más credito.")).lore("").build(), 15,
				new Action() {

					@Override
					public void click(ClickType click, Player player) {

						displayBoosterSelectorGUI(player, AccountBoosterType.NETWORK_BOOSTER, ib);
						return;

					}

				});

		ib.addItem(new ItemBuilder(Material.ARROW).amount(1).name(TextUtil.format("&7Volver")).build(), 31,
				new Action() {

					@Override
					public void click(ClickType click, Player player) {

						backinv.open(player);
						return;

					}

				});

		ib.open(player);
		return;

	}

	public static void displayMembershipGUI(Player player, InventoryBuilder backinv) {

		RankBase.updateMembershipLoot(player.getName());

		InventoryBuilder ib = new InventoryBuilder(TextUtil.format("&e&lTus Membresias"), 6 * 9, false);

		List<Entry<MembershipType, Date>> memberships = RankBase.getMembershipLoot(player.getName());
		RankType rank = RankBase.getRank(player);

		ib.addItem(
				new ItemBuilder(Material.PAPER).amount(1).name(TextUtil.format("&8&lInformación:")).lore("")
						.lore(TextUtil.format("&9&m-&r &7Las membresias almacenadas en el botín"))
						.lore(TextUtil.format("&7tienen un tiempo de expiración el cual define"))
						.lore(TextUtil.format("&7cuando el item será removido. Por ende se debe"))
						.lore(TextUtil.format("&7utilizar antes de que el tiempo termine, En todo"))
						.lore(TextUtil.format("&7caso al hacer uso de una membresia todas las demás"))
						.lore(TextUtil.format("&7se les sumará el tiempo que esta durará, con el fin"))
						.lore(TextUtil.format("&7de que no expiren mientras tienes activada la"))
						.lore(TextUtil.format("&7membresia. Cada vez que compres una membresia desde"))
						.lore(TextUtil.format("&7la tienda está tendrá un tiempo de expiración igual"))
						.lore(TextUtil.format("&7al restante de tu membresia activada &a&l+ &73 meses"))
						.lore(TextUtil.format("&7para que puedas utilizarla una vez finalice la que"))
						.lore(TextUtil.format("&7tienes activada.")).lore("")
						.lore(TextUtil.format("&f&lNota: &7Si activas una membresia mientras tienes una"))
						.lore(TextUtil.format("&7ya activada, perderás totalmente la membresia que tenias"))
						.lore(TextUtil.format("&7activada y la elegida se iniciará normalmente.")).lore("")
						.lore(TextUtil.format("&7Si deseas adquirir una membresia visita"))
						.lore(TextUtil.format("&7nuestra tienda: &btienda.omniblock.net")).lore("").build(),
				50);

		if (rank.isStaff()) {

			ib.addItem(new ItemBuilder(Material.STAINED_GLASS_PANE).amount(1).durability((short) 1)
					.name(TextUtil.format("&6&lBLOQUEADO")).lore("")
					.lore(TextUtil.format("&9- &7No puedes hacer uso de las"))
					.lore(TextUtil.format("&7membresias mientras pertenezcas"))
					.lore(TextUtil.format("&7al Staff de Omniblock Network.")).lore("").build(), 22);

		} else if (memberships.isEmpty()) {

			ib.addItem(new ItemBuilder(Material.STAINED_GLASS_PANE).amount(1).durability((short) 14)
					.name(TextUtil.format("&cVacío")).lore("").lore(TextUtil.format("&9- &7No tienes membresias para"))
					.lore(TextUtil.format("&7utilizar :c")).lore("").build(), 22);

		} else {

			for (Entry<MembershipType, Date> membership : memberships) {

				int slot = ib.getBukkitInventory().firstEmpty();
				Date end = membership.getValue();

				ib.addItem(new ItemBuilder(Material.STORAGE_MINECART).name(TextUtil.format("&aCargando...")).build(),
						slot);

				new BukkitRunnable() {

					Date now;

					long difference, diffSec;
					long days, secondsDay, minutes, hours;

					@Override
					public void run() {

						if (ib.getBukkitInventory() == null) {
							cancel();
							return;
						}
						if (!ib.getBukkitInventory().getViewers().contains(player)) {
							cancel();
							return;
						}

						now = new Date();

						difference = end.getTime() - now.getTime();
						diffSec = difference / 1000;

						days = diffSec / SECONDS_IN_A_DAY;
						secondsDay = diffSec % SECONDS_IN_A_DAY;
						minutes = (secondsDay / 60) % 60;
						hours = (secondsDay / 3600);

						String format = String.format((days > 0 ? days + " dias " : "")
								+ (hours > 0 ? hours + " horas " : "") + minutes + " minutos");

						ib.addItem(new ItemBuilder(Material.MAP).amount(1).hideAtributes()
								.name(TextUtil.format("&9&l" + membership.getKey().getName())).lore("")
								.lore(TextUtil.format("&9- &7La membresía durará el tiempo especificado"))
								.lore(TextUtil.format("&7en su nombre y debes utilizarla antes que su"))
								.lore(TextUtil.format("&7tiempo de expiración termine o se borrará"))
								.lore(TextUtil.format("&7de forma automatica, Si ya has activado una"))
								.lore(TextUtil.format("&7membresia y durante su periodo activas"))
								.lore(TextUtil.format("&7esta membresia perderás la otra membresia"))
								.lore(TextUtil.format("&7con todo su tiempo restante y se activará esta"))
								.lore(TextUtil.format("&7con el tiempo especificado como su remplazo."))
								.lore(TextUtil.format("")).lore(TextUtil.format("&8Tiempo de Expiración: &c" + format))
								.lore(TextUtil.format("        &8&l(&aClick para utilizarlo&8&l)")).lore("").build(),
								slot, new Action() {

									@Override
									public void click(ClickType click, Player player) {

										consumeMembership(player, membership.getKey());
										return;

									}

								});

					}
				}.runTaskTimer(OmniNetwork.getInstance(), 0L, 19L);

			}

		}

		ItemStack disabled_membership = new ItemBuilder(Material.GLASS_BOTTLE).amount(1)
				.name(TextUtil.format("&8Desactivado")).lore("")
				.lore(TextUtil.format("&9&m-&r &7En este momento no estás usando ningún"))
				.lore(TextUtil.format("&7booster de este tipo.")).build();

		RankType temporal_membership = RankBase.getTempRank(player.getName());

		if (temporal_membership != null) {

			Date end = RankBase.getTempRankExpireDate(player.getName());

			new BukkitRunnable() {

				Date now;

				long difference, diffSec;
				long days, secondsDay, minutes, hours;

				@Override
				public void run() {

					if (ib.getBukkitInventory() == null) {
						cancel();
						return;
					}
					if (!ib.getBukkitInventory().getViewers().contains(player)) {
						cancel();
						return;
					}

					now = new Date();

					difference = end.getTime() - now.getTime();
					diffSec = difference / 1000;

					days = diffSec / SECONDS_IN_A_DAY;
					secondsDay = diffSec % SECONDS_IN_A_DAY;
					minutes = (secondsDay / 60) % 60;
					hours = (secondsDay / 3600);

					String format = String.format((days > 0 ? days + " dias " : "")
							+ (hours > 0 ? hours + " horas " : "") + minutes + " minutos ");

					ib.addItem(new ItemBuilder(Material.POTION).durability((short) 8261).amount(1).hideAtributes()
							.name(TextUtil.format("&7Membresia &a&lACTIVADA!")).lore("")
							.lore(TextUtil.format("&8&m-&r &7Tu membresia te dará"))
							.lore(TextUtil.format("&7un rango temporal con el cual"))
							.lore(TextUtil.format("&7podrás disfrutar de opciones"))
							.lore(TextUtil.format("&7unicas para tu cuenta!")).lore("")
							.lore(TextUtil.format("&6✯ &7Rango: &7" + temporal_membership.getName()))
							.lore(TextUtil.format("&b◴ &7Tiempo: &c" + format)).build(), 49);

				}
			}.runTaskTimer(OmniNetwork.getInstance(), 0L, 19L);

		} else {

			ib.addItem(disabled_membership, 49);

		}

		ib.addItem(new ItemBuilder(Material.ARROW).amount(1).name(TextUtil.format("&7Volver")).build(), 48,
				new Action() {

					@Override
					public void click(ClickType click, Player player) {

						backinv.open(player);
						return;

					}

				});

		ib.open(player);
		return;

	}

	public static void displayBoosterSelectorGUI(Player player, AccountBoosterType type, InventoryBuilder backinv) {

		InventoryBuilder ib = new InventoryBuilder(TextUtil.format(type == AccountBoosterType.NETWORK_BOOSTER
				? "&e&lTus Network Boosters" : "&a&lTus Boosters Personales"), 6 * 9, false);

		ib.addItem(new ItemBuilder(Material.ARROW).amount(1).name(TextUtil.format("&7Volver")).build(), 48,
				new Action() {

					@Override
					public void click(ClickType click, Player player) {

						backinv.open(player);
						return;

					}

				});

		String boosterid = BoosterBase.getEnabledBooster(player, type);

		ItemStack disabled_booster = new ItemBuilder(Material.GLASS_BOTTLE).amount(1)
				.name(TextUtil.format("&8Desactivado")).lore("")
				.lore(TextUtil.format("&9&m-&r &7En este momento no estás usando ningún"))
				.lore(TextUtil.format("&7booster de este tipo.")).build();

		if (type == AccountBoosterType.PERSONAL_BOOSTER) {

			List<BoosterType> boosters = getPersonalBoosters(getItems(player));

			if (boosters.isEmpty()) {

				ib.addItem(new ItemBuilder(Material.STAINED_GLASS_PANE).amount(1).durability((short) 14)
						.name(TextUtil.format("&cVacío")).lore("")
						.lore(TextUtil.format("&9- &7No tienes boosters personales para"))
						.lore(TextUtil.format("&7utilizar :c")).lore("").build(), 22);

			} else {

				for (BoosterType bt : boosters) {

					ib.addItem(new ItemBuilder(Material.EXP_BOTTLE).amount(1).name(TextUtil.format("&a" + bt.getName()))
							.lore("").lore(TextUtil.format("&9&m-&r&7 Al utilizar este booster durará"))
							.lore(TextUtil.format("&7el tiempo especificado en su nombre.")).lore("")
							.lore(TextUtil.format("&7Si tienes otro booster activado lo"))
							.lore(TextUtil.format("&7perderás.")).lore("")
							.lore(TextUtil.format("&3Utilizar este Booster &8(Click derecho)")).lore("").build(),
							ib.getBukkitInventory().firstEmpty(), new Action() {

								@Override
								public void click(ClickType click, Player player) {

									consumeBooster(player, bt.getKey(), type, "");
									return;

								}

							});
					continue;

				}

			}

			ib.addItem(new ItemBuilder(Material.PAPER).amount(1).name(TextUtil.format("&8&lInformación:")).lore("")
					.lore(TextUtil.format("&9&m-&r &7Los boosters personales no expiran pero"))
					.lore(TextUtil.format("&7si activas otro mientras ya tienes uno activado"))
					.lore(TextUtil.format("&7el booster que estaba activado se removerá y"))
					.lore(TextUtil.format("&7lo perderás. Los boosters personales funcionan"))
					.lore(TextUtil.format("&7durante el lapso de tiempo que especifica cada"))
					.lore(TextUtil.format("&7booster y con ellos podrás obtener más omnicoins"))
					.lore(TextUtil.format("&7y experiencia en las partidas de cualquier modalidad.")).lore("").build(),
					50);

			if (boosterid.equalsIgnoreCase("NONE")) {

				ib.addItem(disabled_booster, 49);

			} else {

				BoosterType booster = BoosterType.fromKey(boosterid);
				String expiredate = BoosterBase.getExpireDate(player, type);

				if (expiredate.equalsIgnoreCase("NONE")) {

					BoosterBase.removeEnabledBooster(player, type);
					ib.addItem(disabled_booster, 49);

				} else {

					Date now = new Date();
					Date end = BoosterBase.parseExpireDate(expiredate);

					if (now.after(end)) {

						BoosterBase.removeEnabledBooster(player, type);
						ib.addItem(disabled_booster, 49);

					} else {

						new BukkitRunnable() {

							Date now;

							long difference, diffSec;
							long days, secondsDay, seconds, minutes, hours;

							@Override
							public void run() {

								if (ib.getBukkitInventory() == null) {
									cancel();
									return;
								}
								if (!ib.getBukkitInventory().getViewers().contains(player)) {
									cancel();
									return;
								}

								now = new Date();

								difference = end.getTime() - now.getTime();
								diffSec = difference / 1000;

								days = diffSec / SECONDS_IN_A_DAY;
								secondsDay = diffSec % SECONDS_IN_A_DAY;
								seconds = secondsDay % 60;
								minutes = (secondsDay / 60) % 60;
								hours = (secondsDay / 3600);

								String format = String
										.format((days > 0 ? days + " dias " : "") + (hours > 0 ? hours + " horas " : "")
												+ (minutes > 0 ? minutes + " minutos y " : "") + seconds + " segundos");

								ib.addItem(
										new ItemBuilder(Material.POTION).durability((short) 8261).amount(1)
												.hideAtributes()
												.name(TextUtil.format("&7Booster Personal &a&lACTIVADO!")).lore("")
												.lore(TextUtil.format("&8&m-&r &7Tu booster personal te dará"))
												.lore(TextUtil.format("&7omnicoins y experiencia extra mientras"))
												.lore(TextUtil.format("&7se encuentre activado!")).lore("")
												.lore(TextUtil.format("&6✯ &7Nombre: &7" + booster.getName()))
												.lore(TextUtil.format("&b◴ &7Tiempo: &c" + format)).build(),
										49);

							}
						}.runTaskTimer(OmniNetwork.getInstance(), 0L, 19L);

					}

				}

			}

		} else {

			List<NetworkBoosterType> boosters = getNetworkBoosters(getItems(player));

			if (boosters.isEmpty()) {

				ib.addItem(new ItemBuilder(Material.STAINED_GLASS_PANE).amount(1).durability((short) 14)
						.name(TextUtil.format("&cVacío")).lore("")
						.lore(TextUtil.format("&9- &7No tienes boosters globales para"))
						.lore(TextUtil.format("&7utilizar :c")).lore("")
						.lore(TextUtil.format("&7No olvides que puedes comprar más en"))
						.lore(TextUtil.format("&7nuestra tienda: &btienda.omniblock.net")).lore("").build(), 22);

			} else {

				for (NetworkBoosterType bt : boosters) {

					ib.addItem(new ItemBuilder(Material.MAGMA_CREAM).amount(1)
							.name(TextUtil.format("&a" + bt.getName())).lore("")
							.lore(TextUtil.format("&9&m-&r&7 Al utilizar este booster durará"))
							.lore(TextUtil.format("&7el tiempo especificado en su nombre.")).lore("")
							.lore(TextUtil.format("&7Deberás seleccionar la modalidad en"))
							.lore(TextUtil.format("&7donde quieres activarlo.")).lore("")
							.lore(TextUtil.format("&3Utilizar este Booster &8(Click derecho)")).lore("").build(),
							ib.getBukkitInventory().firstEmpty(), new Action() {

								@Override
								public void click(ClickType click, Player player) {

									displayBoosterGameSelectorGUI(player, bt.getKey(), backinv);
									return;

								}

							});
					continue;

				}

			}

			ib.addItem(
					new ItemBuilder(Material.PAPER).amount(1).name(TextUtil.format("&8&lInformación:")).lore("")
							.lore(TextUtil.format("&9&m-&r &7Los boosters globales no expiran y"))
							.lore(TextUtil.format("&7no puedes activar un booster global mientras"))
							.lore(TextUtil.format("&7se encuentre otro booster tuyo o de algún jugador"))
							.lore(TextUtil.format("&7activado sobre la misma modalidad. Al activar uno"))
							.lore(TextUtil.format("&7de estos boosters tendrás que elegir la modalidad"))
							.lore(TextUtil.format("&7a la cual deseas otorgarle el booster y todos los"))
							.lore(TextUtil.format("&7jugadores recibirán mayor cantidad de omnicoins y"))
							.lore(TextUtil.format("&7experiencia al jugar una partida.")).lore("")
							.lore(TextUtil.format("&f&lNota: &7Por cada partida que se juegue en la"))
							.lore(TextUtil.format("&7modalidad de la cual activastes el booster, se te"))
							.lore(TextUtil.format("&7otrogará 20 omnicoins adicionales sin importar si"))
							.lore(TextUtil.format("&7estás online o offline.")).lore("")
							.lore(TextUtil.format("&7Si deseas adquirir un booster global visita"))
							.lore(TextUtil.format("&7nuestra tienda: &btienda.omniblock.net")).lore("").build(),
					50);

			if (boosterid.equalsIgnoreCase("NONE")) {

				ib.addItem(disabled_booster, 49);

			} else {

				NetworkBoosterType booster = NetworkBoosterType.fromKey(boosterid);
				String expiredate = BoosterBase.getExpireDate(player, type);

				if (expiredate.equalsIgnoreCase("NONE")) {

					BoosterBase.removeEnabledBooster(player, type);
					ib.addItem(disabled_booster, 49);

				} else {

					Date now = new Date();
					Date end = BoosterBase.parseExpireDate(expiredate);

					if (now.after(end)) {

						BoosterBase.removeEnabledBooster(player, type);
						ib.addItem(disabled_booster, 49);

					} else {

						new BukkitRunnable() {

							Date now;

							long difference, diffSec;
							long days, secondsDay, seconds, minutes, hours;

							@Override
							public void run() {

								if (ib.getBukkitInventory() == null) {
									cancel();
									return;
								}
								if (!ib.getBukkitInventory().getViewers().contains(player)) {
									cancel();
									return;
								}

								now = new Date();

								difference = end.getTime() - now.getTime();
								diffSec = difference / 1000;

								days = diffSec / SECONDS_IN_A_DAY;
								secondsDay = diffSec % SECONDS_IN_A_DAY;
								seconds = secondsDay % 60;
								minutes = (secondsDay / 60) % 60;
								hours = (secondsDay / 3600);

								String format = String
										.format((days > 0 ? days + " dias " : "") + (hours > 0 ? hours + " horas " : "")
												+ (minutes > 0 ? minutes + " minutos y " : "") + seconds + " segundos");

								ib.addItem(new ItemBuilder(Material.POTION).durability((short) 8261).amount(1)
										.hideAtributes().name(TextUtil.format("&7Booster Global &a&lACTIVADO!"))
										.lore("").lore(TextUtil.format("&8&m-&r &7Tu booster global te dará"))
										.lore(TextUtil.format("&7omnicoins y experiencia extra mientras"))
										.lore(TextUtil.format("&7se encuentre activado y también al"))
										.lore(TextUtil.format("&7resto de jugadores donde se a activado.")).lore("")
										.lore(TextUtil.format("&6✯ &7Nombre: &7" + booster.getName()))
										.lore(TextUtil.format("&b◴ &7Tiempo: &c" + format))
										.lore(TextUtil.format(
												"&8➟ &7Modalidad: &e" + BoosterBase.getNetworkBoosterGameType(player)))
										.build(), 49);

							}
						}.runTaskTimer(OmniNetwork.getInstance(), 0L, 19L);

					}

				}

			}

		}

		ib.open(player);
		return;

	}

	public static void displayBoosterGameSelectorGUI(Player player, String key, InventoryBuilder backinv) {

		InventoryBuilder ib = new InventoryBuilder(TextUtil.format("&8Seleccione una Modalidad"), 5 * 9, false);

		ib.addItem(new ItemBuilder(Material.BOW).amount(1).name(TextUtil.format("&7Skywars &b&lNUEVO!")).lore("")
				.lore(TextUtil.format("&9&m-&r &7Si activas el booster en skywars por cada"))
				.lore(TextUtil.format("&7partida que se juegue cualquier persona en dicha"))
				.lore(TextUtil.format("&7modalidad se te otorgará &e20 Omnicoins &7y además"))
				.lore(TextUtil.format("&7todos los jugadores de la partida recibirán una"))
				.lore(TextUtil.format("&7bonificación de omnicoins y experiencia extra!")).lore("").build(), 10,
				new Action() {

					@Override
					public void click(ClickType click, Player player) {

						consumeBooster(player, key, AccountBoosterType.NETWORK_BOOSTER, "Skywars");
						return;

					}

				});

		ib.addItem(new ItemBuilder(Material.COAL_BLOCK).amount(1).name(TextUtil.format("&c&lProximamente")).build(),
				13);

		ib.addItem(new ItemBuilder(Material.COAL_BLOCK).amount(1).name(TextUtil.format("&c&lProximamente")).build(),
				16);

		ib.addItem(new ItemBuilder(Material.ARROW).amount(1).name(TextUtil.format("&7Volver")).build(), 40,
				new Action() {

					@Override
					public void click(ClickType click, Player player) {

						backinv.open(player);
						return;

					}

				});

		ib.open(player);
		return;

	}

	public static void consumeMembership(Player player, MembershipType type) {

		RankBase.updateMembershipLoot(player.getName());

		List<Entry<MembershipType, Date>> memberships = RankBase.getMembershipLoot(player.getName());
		RankType rank = RankBase.getRank(player);

		if (rank.isStaff()) {

			player.closeInventory();
			player.sendMessage(
					TextUtil.format("&8&lM&8embresias &6&l» &4ERROR &7No puedes utilizar una membresia mientras "
							+ "pertenezcas al Staff!"));
			return;

		}

		memberships.stream().forEach(entry -> {

			if (entry.getKey() == type) {

				Date end = entry.getValue();

				if (end.after(new Date())) {

					for (int i = 0; i < 12; i++) {
						RandomFirework.spawnRandomFirework(player.getLocation());
					}

					player.closeInventory();
					player.sendMessage(
							TextUtil.format(TextUtil.getCenteredMessage("&6&m-----------------------------------")));
					player.sendMessage(TextUtil.format(TextUtil.getCenteredMessage("&7¡Has activado una Membresia!")));
					player.sendMessage(TextUtil.format(TextUtil.getCenteredMessage("")));
					player.sendMessage(TextUtil.format(TextUtil.getCenteredMessage("&8Nombre: &b" + type.getName())));
					player.sendMessage(TextUtil
							.format(TextUtil.getCenteredMessage("&8Tu nuevo Rango: &b" + type.getRank().getName())));
					player.sendMessage(TextUtil
							.format(TextUtil.getCenteredMessage("&8Finalizará: &b" + RankBase.parseExpireDate(end))));
					player.sendMessage(
							TextUtil.format(TextUtil.getCenteredMessage("&6&m-----------------------------------")));

					RankBase.removeTemporalMembership(player.getName());
					RankBase.startTemporalMembership(player.getName(), type);
					RankBase.addTimeToLoot(player.getName(), type);

					return;

				} else {

					player.closeInventory();
					player.sendMessage(TextUtil.format(
							"&8&lM&8embresias &c&l» &4ERROR &7La membresia que intentas usar ya ha " + "expirado!"));

					return;

				}

			}

		});

	}

	public static void consumeBooster(Player player, String key, AccountBoosterType type, String... args) {

		if (type == AccountBoosterType.PERSONAL_BOOSTER) {

			List<BoosterType> boosters = getPersonalBoosters(getItems(player));

			if (boosters.contains(BoosterType.fromKey(key))) {

				AccountBase.removeTag(player, key);

				BoosterBase.removeEnabledBooster(player, type);
				BoosterBase.startBooster(player, key, type, args[0]);

				player.closeInventory();
				player.sendMessage(
						TextUtil.format(TextUtil.getCenteredMessage("&a&m--------------------------------")));
				player.sendMessage(
						TextUtil.format(TextUtil.getCenteredMessage("&7¡Has activado un booster personal!")));
				player.sendMessage(TextUtil.format(TextUtil.getCenteredMessage("")));
				player.sendMessage(TextUtil
						.format(TextUtil.getCenteredMessage("&8Nombre: &b" + BoosterType.fromKey(key).getName())));
				player.sendMessage(TextUtil.format(TextUtil.getCenteredMessage(
						"&8Finalizará: &b" + BoosterBase.parseExpireDate(BoosterType.fromKey(key).getEndDate()))));
				player.sendMessage(
						TextUtil.format(TextUtil.getCenteredMessage("&a&m--------------------------------")));
				return;

			} else {

				player.closeInventory();
				player.sendMessage(TextUtil.format("&8&lB&8oosters &c&l» &4ERROR &7Tu no posees este booster!"));
				return;

			}

		} else {

			List<NetworkBoosterType> boosters = getNetworkBoosters(getItems(player));

			if (boosters.contains(NetworkBoosterType.fromKey(key))) {

				player.closeInventory();

				Packets.STREAMER.streamPacketAndRespose(new RequestPlayerStartNetworkBoosterPacket()
						.setPlayername(player.getName()).setGametype(args[0]).setKey(key)
						.setDuration(NetworkBoosterType.fromKey(key).getSeconds()).build()
						.setReceiver(PacketSenderType.OMNICORD),

						new PacketResponder<ResposePlayerNetworkBoosterPacket>() {

							@Override
							public void readRespose(
									PacketSocketData<ResposePlayerNetworkBoosterPacket> packetsocketdata) {

								if (!packetsocketdata.getStructure().<String>get(DataType.STRINGS, "playername")
										.equalsIgnoreCase(player.getName()))
									return;
								if (!player.isOnline()) {

									packetsocketdata.setCancelled(true);
									return;

								}

								AccountBase.removeTag(player.getName(), key);

								BoosterBase.removeEnabledBooster(player.getName(), AccountBoosterType.NETWORK_BOOSTER);
								BoosterBase.startBooster(player.getName(), key, AccountBoosterType.NETWORK_BOOSTER,
										args[0]);

								player.sendMessage(TextUtil
										.format(TextUtil.getCenteredMessage("&a&m--------------------------------")));
								player.sendMessage(TextUtil
										.format(TextUtil.getCenteredMessage("&7¡Has activado un booster global!")));
								player.sendMessage(TextUtil.format(TextUtil.getCenteredMessage("")));
								player.sendMessage(TextUtil.format(TextUtil.getCenteredMessage(
										"&8Nombre: &b" + NetworkBoosterType.fromKey(key).getName())));
								player.sendMessage(
										TextUtil.format(TextUtil.getCenteredMessage("&8Modalidad: &b" + args[0])));
								player.sendMessage(TextUtil.format(TextUtil.getCenteredMessage("&8Finalizará: &b"
										+ BoosterBase.parseExpireDate(NetworkBoosterType.fromKey(key).getEndDate()))));
								player.sendMessage(TextUtil
										.format(TextUtil.getCenteredMessage("&a&m--------------------------------")));

							}

						});
				return;

			} else {

				player.closeInventory();
				player.sendMessage(TextUtil.format("&8&lB&8oosters &c&l» &4ERROR &7Tu no posees este booster!"));
				return;

			}

		}

	}

	private static List<BoosterType> getPersonalBoosters(List<String> list) {

		List<BoosterType> boosters = new ArrayList<BoosterType>();

		for (BoosterType bt : BoosterType.values()) {

			int ocurrences = Collections.frequency(list, bt.getKey());

			while (ocurrences > 0) {

				boosters.add(bt);
				ocurrences--;
				continue;

			}

		}

		return boosters;

	}

	private static List<NetworkBoosterType> getNetworkBoosters(List<String> list) {

		List<NetworkBoosterType> boosters = new ArrayList<NetworkBoosterType>();

		for (NetworkBoosterType bt : NetworkBoosterType.values()) {

			int ocurrences = Collections.frequency(list, bt.getKey());

			while (ocurrences > 0) {

				boosters.add(bt);
				ocurrences--;
				continue;

			}

		}

		return boosters;

	}

	public static List<String> getItems(Player player) {

		List<String> tags = new ArrayList<String>(Arrays.asList(AccountBase.getTags(player).split(",")));

		for (AccountTagType att : AccountTagType.values()) {
			if (tags.contains(att.getKey())) {
				tags.remove(att.getKey());
			}
		}

		return tags;

	}

	public static boolean hasTag(AccountTagType tag, String tags) {

		if (tags.contains(tag.getKey().toLowerCase()))
			return true;
		return false;

	}

	public static enum AccountBoosterType {

		NETWORK_BOOSTER, PERSONAL_BOOSTER,

		;

	}

	public static enum AccountTagType {

		IP_LOGIN("iplogin"), PRIVATE_MSG("privatemsg"), TEXTURE_SOUND("texturesound"), FRIEND_REQUEST("friendrequest"),

		;

		private String key;

		AccountTagType(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

	}

}
