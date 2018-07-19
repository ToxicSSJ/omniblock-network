package net.omniblock.network.library.helpers.inventory.paginator;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import com.google.common.collect.Lists;
import net.omniblock.network.library.helpers.inventory.InventoryBuilder;
import net.omniblock.network.library.helpers.inventory.InventoryBuilder.Action;

public class InventoryPaginator {

	protected List<InventoryBuilder> inventoryPages = Lists.newArrayList();
	
	protected PaginatorStyle style;
	
	public InventoryPaginator(PaginatorStyle style) {
		
		this.style = style;
		return;
		
	}
	
	public InventoryPaginator addPage(InventoryBuilder inventoryBuilder) {
		
		if(inventoryBuilder.getSize() < 6 * 9)
			throw new UnsupportedOperationException("El inventario debe tener un tamaño mayor o igual a 56.");
		
		if(inventoryBuilder.isDeleteOnClose())
			throw new UnsupportedOperationException("El inventario no debe ser borrado al cerrarse.");
		
		inventoryBuilder.addItem(style.getNext(true), 51);
		
		if(inventoryPages.size() != 0)
			getLastPage().addItem(style.getNext(false), 51, new Action() {

				@Override
				public void click(ClickType click, Player player) {
					
					inventoryBuilder.open(player);
					return;
					
				}
				
			});
		
		if(inventoryPages.size() == 0)
			inventoryBuilder.addItem(style.getBack(true), 47);
		
		if(inventoryPages.size() != 0)
			inventoryBuilder.addItem(style.getBack(false), 47, new Action() {

				InventoryBuilder backPage = getLastPage();
				
				@Override
				public void click(ClickType click, Player player) {
					
					backPage.open(player);
					return;
					
				}
				
			});
		
		
		
		inventoryPages.add(inventoryBuilder);
		return this;
		
	}
	
	public InventoryBuilder getLastPage() {
		
		if(inventoryPages.size() == 0)
			return null;
		
		return inventoryPages.get(inventoryPages.size() - 1);
		
	}
	
	public boolean contains(InventoryBuilder inventoryBuilder) {
		
		return inventoryPages.contains(inventoryBuilder);
			
		
	}
	
	public void openInventory(Player player) {
		
		if(inventoryPages.size() < 1)
			return;
		
		inventoryPages.get(0).open(player);
		return;
		
	}
	
	public PaginatorStyle getStyle() {
		return style;
	}

}
