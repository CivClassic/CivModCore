package vg.civcraft.mc.civmodcore.inventorygui.legacy;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.inventorygui.ClickableInventory;

/**
 * A possible option in a clickable inventory. Implement clicked(Player p) to do something whenever the item of this
 * clickable is clicked.
 *
 * @author Maxopoly
 *
 * @deprecated Replaced with ClickableButton
 * @see vg.civcraft.mc.civmodcore.inventorygui.ClickableButton
 */
@Deprecated
public abstract class Clickable implements IClickable {

	protected ItemStack item;

	public Clickable(ItemStack item) {
		this.item = item;
	}

	/**
	 * @return Which item stack represents this clickable when it is initially loaded into the inventory
	 */
	@Override
	public ItemStack getItemStack() {
		return item;
	}

	/**
	 * Called when this instance is added to an inventory so it can do something if desired
	 */
	@Override
	public void addedToInventory(ClickableInventory inventory, int slot) {}

}
