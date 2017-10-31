package net.omniblock.network.handlers.packets.readers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.handlers.base.bases.type.AccountBase;
import net.omniblock.network.handlers.base.bases.type.AuthBase;
import net.omniblock.network.library.helpers.actions.SimpleEventListener;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.account.AccountManager;
import net.omniblock.network.systems.account.AccountManager.AccountTagType;
import net.omniblock.packets.network.Packets;
import net.omniblock.packets.network.structure.data.PacketSocketData;
import net.omniblock.packets.network.structure.data.PacketStructure;
import net.omniblock.packets.network.structure.data.PacketStructure.DataType;
import net.omniblock.packets.network.structure.packet.PlayerSendToServerPacket;
import net.omniblock.packets.network.structure.packet.ResposeAuthEvaluatePacket;
import net.omniblock.packets.network.structure.type.PacketSenderType;
import net.omniblock.packets.network.tool.object.PacketReader;
import net.omniblock.packets.object.external.ServerType;

public class AuthReaders {

	public static void start() {

		Packets.READER.registerReader(new PacketReader<ResposeAuthEvaluatePacket>() {

			@Override
			public void readPacket(PacketSocketData<ResposeAuthEvaluatePacket> packetsocketdata) {

				PacketStructure data = packetsocketdata.getStructure();

				String name = data.get(DataType.STRINGS, "playername");
				String status = data.get(DataType.STRINGS, "status");

				if (Bukkit.getPlayer(name) == null)
					return;

				if (status.equalsIgnoreCase("LOGIN")) {

					Player player = Bukkit.getPlayer(name);

					if (!AuthBase.isRegister(player)) {

						player.sendMessage(TextUtil.format(
								"&8&lC&8uentas &9&l» &fAún no te has registrado, A continuación escribe la contraseña con la cual te registrarás:"));

						new SimpleEventListener<AsyncPlayerChatEvent>(AsyncPlayerChatEvent.class, true) {

							@Override
							public boolean incomingEvent(AsyncPlayerChatEvent e) {

								if (e.getPlayer() == player) {

									e.setCancelled(true);

									if (e.getMessage().startsWith("/")) {

										player.sendMessage(TextUtil.format(
												"&8&lC&8uentas &c&l» &4ERROR &fNo debes escribir nungún comando en el proceso de registro, solo escribe la contraseña:"));
										return false;

									}

									if (e.getMessage().length() >= 20) {

										player.sendMessage(TextUtil.format(
												"&8&lC&8uentas &c&l» &4ERROR &fTu contraseña es demasiado corta, escribe una contraseña corta y fácil de aprender:"));
										return false;

									}

									if (e.getMessage().length() >= 20) {

										player.sendMessage(TextUtil.format(
												"&8&lC&8uentas &c&l» &4ERROR &fTu contraseña es demasiado larga, escribe una contraseña corta y fácil de aprender:"));
										return false;

									}

									String pass = e.getMessage();

									AuthBase.setPassword(player, pass);

									player.sendMessage(TextUtil.format(
											"&8&lC&8uentas &a&l» &aTe has registrado correctamente!" + (AccountManager
													.hasTag(AccountTagType.IP_LOGIN, AccountBase.getTags(player))
															? " &7También se ha registrado tu &9&lIP &7de acceso!"
															: "")));
									player.sendMessage(TextUtil.format(
											"&8&lC&8uentas &9&l» &fSe te enviará al Lobby principal en 3 segundos..."));

									AuthBase.sucess(player);

									new BukkitRunnable() {

										@Override
										public void run() {

											cancel();
											if (!player.isOnline())
												return;

											Packets.STREAMER.streamPacket(new PlayerSendToServerPacket()
													.setServertype(ServerType.MAIN_LOBBY_SERVER).setPlayername(name)
													.build().setReceiver(PacketSenderType.OMNICORE));
											return;

										}

									}.runTaskLater(OmniNetwork.getInstance(), 20 * 3);

									return true;

								}

								return false;
							}

						};

						return;

					}

					player.sendMessage(TextUtil.format(
							"&8&lC&8uentas &9&l» &fParece que no te has logeado, A continuación &7escribe la contraseña &fcon la cual te has registrado:"));

					new SimpleEventListener<AsyncPlayerChatEvent>(AsyncPlayerChatEvent.class, true) {

						@Override
						public boolean incomingEvent(AsyncPlayerChatEvent e) {

							if (e.getPlayer() == player) {

								e.setCancelled(true);

								if (e.getMessage().startsWith("/")) {

									player.sendMessage(TextUtil.format(
											"&8&lC&8uentas &c&l» &4ERROR &fNo debes escribir nungún comando en el proceso de logeo, solo escribe tu contraseña:"));
									return false;

								}

								String pass = AuthBase.getPassword(player);

								if (e.getMessage().equals(pass)) {

									player.sendMessage(TextUtil.format(
											"&8&lC&8uentas &a&l» &aTe has logeado correctamente!" + (AccountManager
													.hasTag(AccountTagType.IP_LOGIN, AccountBase.getTags(player))
															? " &7También se ha registrado tu &9&lIP &7de acceso!"
															: "")));
									player.sendMessage(TextUtil.format(
											"&8&lC&8uentas &9&l» &7Se te enviará al servidor en &e&l3 &7segundos..."));

								}

								AuthBase.sucess(player);

								new BukkitRunnable() {

									@Override
									public void run() {

										cancel();
										if (!player.isOnline())
											return;

										Packets.STREAMER.streamPacket(new PlayerSendToServerPacket()
												.setServertype(ServerType.MAIN_LOBBY_SERVER).setPlayername(name).build()
												.setReceiver(PacketSenderType.OMNICORE));
										return;

									}

								}.runTaskLater(OmniNetwork.getInstance(), 20 * 3);

								return true;

							}

							return false;
						}

					};

					return;

				} else if (status.equalsIgnoreCase("SUCESS")) {

					Player player = Bukkit.getPlayer(name);

					player.sendMessage(TextUtil.format("&8&lC&8uentas &a&l» &aTe has logeado correctamente!"));
					player.sendMessage(
							TextUtil.format("&8&lC&8uentas &9&l» &7Se te enviará al servidor en &e&l3 &7segundos..."));

					new BukkitRunnable() {

						@Override
						public void run() {

							cancel();
							if (!player.isOnline())
								return;

							Packets.STREAMER.streamPacket(
									new PlayerSendToServerPacket().setServertype(ServerType.MAIN_LOBBY_SERVER)
											.setPlayername(name).build().setReceiver(PacketSenderType.OMNICORE));
							return;

						}

					}.runTaskLater(OmniNetwork.getInstance(), 20 * 3);

				}

			}

			@Override
			public Class<ResposeAuthEvaluatePacket> getAttachedPacketClass() {
				return ResposeAuthEvaluatePacket.class;
			}

		});

	}

}
