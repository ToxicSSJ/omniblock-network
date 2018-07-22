package net.omniblock.network.systems.rank.type;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import net.omniblock.network.library.addons.xmladdon.XMLType;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.network.systems.rank.RankManager;

public enum RankType {

	USER(0, false, false, "Usuario", ""),

	GOLEM(1, true, false, "Golem", TextUtil.format("&8(&eGolem&8)")),
	TITAN(2, true, false, "Titan", TextUtil.format("&8(&6Titan&8)")),

	YOUTUBE(3, true, false, "Youtube", TextUtil.format("&8(&cY&fT&8)")),
	TWITCH(4, true, false, "Twitch", TextUtil.format("&8(&dTwitch&8)")),

	HELPER(5, true, true, "Ayudante", TextUtil.format("&8(&aAY&8)")),
	MOD(6, true, true, "Moderador", TextUtil.format("&8(&cMod&8)")),
	BNF(7, true, true, "Benefactor", TextUtil.format("&8(&9BNF&8)")),
	
	GM(8, true, true, "Game Master", TextUtil.format("&8(&bGM&8)")),
	ADMIN(9, true, true, "Administrador", TextUtil.format("&8(&4Admin&8)")),
	DIRECTOR(10, true, true, "Director", TextUtil.format("&8(&3Dir&8)"))

	;

	static {

		RankManager.updatePermissions();

	}

	public static final RankType defrank = RankType.USER;
	public static final XMLType xml = XMLType.PERMISSIONS;

	private int id;

	private String name;
	private String prefixname;

	private boolean staff = false;
	private boolean prefix = false;

	RankType(int id, boolean hasprefix, boolean isstaff, String name, String prefix) {

		this.id = id;

		this.name = name;
		this.prefixname = prefix;

		this.prefix = hasprefix;
		this.staff = isstaff;

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<String> getPermissions() {

		if (RankManager.permissions.containsKey(this)) {
			return RankManager.permissions.get(this);
		}

		return new HashSet<String>();

	}

	public String getCustomName(Player player) {
		if (getPrefix() == null)
			return TextUtil.format("&7" + player.getName());
		if (getPrefix().equals("") || getPrefix() == "")
			return TextUtil.format("&7" + player.getName());
		return TextUtil.format(getPrefix() + " &7" + player.getName());
	}

	public String getCustomName(Player player, char color) {
		if (getPrefix() == null)
			return TextUtil.format("&" + color + player.getName());
		if (getPrefix().equals("") || getPrefix() == "")
			return TextUtil.format("&" + color + player.getName());
		return TextUtil.format(getPrefix() + " &" + color + player.getName());
	}

	public String getCustomName(Player player, String prefix, char color) {
		if (getPrefix() == null)
			return TextUtil.format(prefix + " &" + color + " " + player.getName());
		if (getPrefix().equals("") || getPrefix() == "")
			return TextUtil.format(" " + prefix + " &" + color + player.getName());
		return TextUtil.format(getPrefix() + " " + prefix + " &" + color + player.getName());
	}
	
	public String getCustomName(String player) {
		if (getPrefix() == null)
			return TextUtil.format("&7" + player);
		if (getPrefix().equals("") || getPrefix() == "")
			return TextUtil.format("&7" + player);
		return TextUtil.format(getPrefix() + " &7" + player);
	}

	public String getCustomName(String player, char color) {
		if (getPrefix() == null)
			return TextUtil.format("&" + color + player);
		if (getPrefix().equals("") || getPrefix() == "")
			return TextUtil.format("&" + color + player);
		return TextUtil.format(getPrefix() + " &" + color + player);
	}

	public String getCustomName(String player, String prefix, char color) {
		if (getPrefix() == null)
			return TextUtil.format(prefix + " &" + color + " " + player);
		if (getPrefix().equals("") || getPrefix() == "")
			return TextUtil.format(" " + prefix + " &" + color + player);
		return TextUtil.format(getPrefix() + " " + prefix + " &" + color + player);
	}
	
	public static RankType getByName(String rankName) {
		
		for(RankType type : RankType.values()) {
			
			if(type.getName().equalsIgnoreCase(rankName))
				return type;
			
		}
		
		return RankType.USER;
		
	}
	
	public static boolean exists(String rankName) {
		
		for(RankType type : RankType.values()) {
			
			if(type.getName().equalsIgnoreCase(rankName))
				return true;
			
		}
		
		return false;
		
	}
	
	public String getPrefix() {
		return prefixname;
	}

	public boolean hasPrefix() {
		return prefix;
	}

	public boolean isStaff() {
		return staff;
	}

}
