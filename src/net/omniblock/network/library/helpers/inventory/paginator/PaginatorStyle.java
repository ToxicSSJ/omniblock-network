package net.omniblock.network.library.helpers.inventory.paginator;

import net.omniblock.network.library.helpers.ItemBuilder;
import net.omniblock.network.library.utils.TextUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public enum PaginatorStyle {

	DEFAULT_ARROWS(
			new ItemBuilder(Material.ARROW)
					.name(TextUtil.format("&b« &7Volver"))
					.build(),
			new ItemBuilder(Material.ARROW)
					.name(TextUtil.format("&b» &7Siguiente"))
					.build(),
			new ItemBuilder(Material.ARROW)
					.name(TextUtil.format("&c✖ &8Volver"))
					.build(),
			new ItemBuilder(Material.ARROW)
					.name(TextUtil.format("&c✖ &8Siguiente"))
					.build()),

	COLOURED_ARROWS(
			new ItemBuilder(Material.TIPPED_ARROW)
					.setPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1, 1))
					.name(TextUtil.format("&b« &7Volver"))
					.hideAtributes()
					.build(),
			new ItemBuilder(Material.TIPPED_ARROW)
					.setPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1, 1))
					.name(TextUtil.format("&b» &7Siguiente"))
					.hideAtributes()
					.build(),
			new ItemBuilder(Material.TIPPED_ARROW)
					.setPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1, 1))
					.name(TextUtil.format("&c✖ &8Volver"))
					.hideAtributes()
					.build(),
			new ItemBuilder(Material.TIPPED_ARROW)
					.setPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1, 1))
					.name(TextUtil.format("&c✖ &8Siguiente"))
					.hideAtributes()
					.build()),


	;

	private ItemStack back, next, cantBack, cantNext;

	PaginatorStyle(ItemStack back, ItemStack next, ItemStack cantBack, ItemStack cantNext){

		this.back = back;
		this.next = next;

		this.cantBack = cantBack;
		this.cantNext = cantNext;

	}

	public ItemStack getBack(boolean cant) {
		return cant ? cantBack : back;
	}

	public ItemStack getNext(boolean cant) {
		return cant ? cantNext : next;
	}

}
