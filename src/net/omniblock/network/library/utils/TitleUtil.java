package net.omniblock.network.library.utils;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

@Deprecated
public class TitleUtil {
    public TitleUtil() {
    }

    public static void sendTitle(Player player, String message) {
        PlayerConnection connect = ((CraftPlayer)player).getHandle().playerConnection;
        IChatBaseComponent chat = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.TITLE, chat);
        connect.sendPacket(packet);
    }

    public static void sendSubTitle(Player player, String message) {
        PlayerConnection connect = ((CraftPlayer)player).getHandle().playerConnection;
        IChatBaseComponent chat = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chat);
        connect.sendPacket(packet);
    }

    public static void sendTiming(Player player, int fadein, int stay, int fadeout) {
    }

    public static void sendActionBar(Player player, String message) {
        PlayerConnection connect = ((CraftPlayer)player).getHandle().playerConnection;
        IChatBaseComponent chat = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(chat);
        connect.sendPacket(packet);
    }
}
