/*
 * Copyright 2015-2016 inventivetalent. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and contributors and should not be interpreted as representing official policies,
 *  either expressed or implied, of anybody else.
 */

package net.omniblock.network.library.addons.resourceaddon.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.omniblock.network.OmniNetwork;

public class RPApiPlugin implements Listener {

	public static IPacketPlayResourcePackStatus packet;

	public static void setup() {

		Bukkit.getPluginManager().registerEvents(new RPApiPlugin(), OmniNetwork.getInstance());

		Class<?> packet_class = null;
		try {
			packet_class = Class.forName(
					"omniblock.on.util.lib.bukkit.textures.api.version.PacketPlayResourcePackStatus_" + getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (packet_class == null) {
			Bukkit.getLogger().severe("[RPApi] Can't find compatible Packet class! (Version: " + getVersion() + ")");
			return;
		} else {
			Bukkit.getLogger().info("Using class '" + packet_class + "'.");
		}

		try {
			packet = (IPacketPlayResourcePackStatus) packet_class.newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (packet == null) {
			Bukkit.getLogger().severe("[RPApi] Error while loading Packet!");
			return;
		}

		try {
			packet.inject();
		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.getLogger().severe("[RPApi] Error while injecting Packet into Classpath!");
			return;
		}

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		packet.addChannelForPlayer(event.getPlayer());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		packet.removeChannelForPlayer(event.getPlayer());
	}

	public static void onResourcePackResult(Status status, Player player, String hash) {
		Bukkit.getPluginManager().callEvent(new ResourcePackStatusEvent(status, player, hash));
	}

	static String getVersion() {
		String name = Bukkit.getServer().getClass().getPackage().getName();
		String version = name.substring(name.lastIndexOf('.') + 1);
		return version;
	}
}
