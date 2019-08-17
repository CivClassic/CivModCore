package vg.civcraft.mc.civmodcore.inventorygui.legacy;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.inventorygui.ClickableInventory;

/**
 * @deprecated Replaced with ClickableButton
 * @see vg.civcraft.mc.civmodcore.inventorygui.ClickableButton
 * */
@Deprecated
public interface IClickable {

	/**
	 * What is done whenever this element is clicked
	 * @param player Player who clicked
	 */
	void clicked(Player player);

	/**
	 * @return Which item stack represents this clickable when it is initially loaded into the inventory
	 */
	ItemStack getItemStack();

	/**
	 * Called when this instance is added to an inventory so it can do something if desired
	 */
	void addedToInventory(ClickableInventory inventory, int slot);

}
