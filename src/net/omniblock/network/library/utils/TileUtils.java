package net.omniblock.network.library.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.EnchantingTable;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;

/**
 * 
 * Clase encargada de ofrecer utilidades
 * para las TileEntities.
 * 
 * @author zlToxicNetherlz
 *
 */
public class TileUtils {

	/**
	 * 
	 * Buscar un cofre detr�s de un cartel
	 * y devolverlo. En caso de que no exista
	 * este metodo devolver� null.
	 * 
	 * @param sign El cartel.
	 * @return El Cofre detr�s de un cartel con
	 * el tipo de objeto 'Chest' o 'Trapped Chest'.
	 * @see Chest
	 */
	public static Chest getChestBehindSign(Sign sign) {
		
		org.bukkit.material.Sign signMaterial = (org.bukkit.material.Sign) sign.getData();
		BlockFace attachedFace = signMaterial.getAttachedFace();
		
		Block chestBlock = sign.getBlock().getRelative(attachedFace);
		
		if(chestBlock.getType() == Material.CHEST || chestBlock.getType() == Material.TRAPPED_CHEST)
			if(chestBlock.getState() instanceof Chest)
				return (Chest) chestBlock.getState();
		
		return null;
		
	}
	
	/**
	 * 
	 * Buscar un cofre detr�s de un cartel
	 * y devolverlo. En caso de que no exista
	 * este metodo devolver� null.
	 * 
	 * @param sign El cartel.
	 * @return La Tile Entity detr�s de un cartel con
	 * el tipo de objeto 'Chest, Furnace, EnchantingTable'.
	 * @see BlockState
	 */
	public static BlockState getTileBehindSign(Sign sign) {
		
		org.bukkit.material.Sign signMaterial = (org.bukkit.material.Sign) sign.getData();
		BlockFace attachedFace = signMaterial.getAttachedFace();
		
		Block tileBlock = sign.getBlock().getRelative(attachedFace);
		BlockState stateBlock = tileBlock.getState();
		
		if(		stateBlock instanceof Chest ||
				stateBlock instanceof Furnace ||
				stateBlock instanceof EnchantingTable)
			return stateBlock;
		
		return null;
		
	}
	
	/**
	 * 
	 * Buscar un cofre atado a un hopper
	 * y devolverlo. En caso de que no exista
	 * este metodo devolver� null.
	 * 
	 * @param hopper La tolva.
	 * @return El Cofre atado a un hopper con
	 * el tipo de objeto 'Chest'.
	 * @see Chest
	 */
	public static Chest getChestByHopper(Hopper hopper) {
		
		org.bukkit.material.Hopper hopperMaterial = (org.bukkit.material.Hopper) hopper.getData();
		BlockFace attachedFace = hopperMaterial.getFacing();
		
		Block chestBlock = hopper.getBlock().getRelative(attachedFace);
		
		if(chestBlock.getType() == Material.CHEST)
			if(chestBlock.getState() instanceof Chest)
				return (Chest) chestBlock.getState();
		
		return null;
		
	}
	
	/**
	 * Buscar un bloque atado a un hopper
	 */
	public static Block getBlockByHopper(Hopper hopper){
		
		org.bukkit.material.Hopper hopperMaterial = (org.bukkit.material.Hopper) hopper.getData();
		BlockFace attachedFace = hopperMaterial.getFacing();
		
		Block block = hopper.getBlock().getRelative(attachedFace);
		
		return block;
	}
}
