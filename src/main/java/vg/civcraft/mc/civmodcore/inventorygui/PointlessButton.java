package vg.civcraft.mc.civmodcore.inventorygui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This class represents a button that does nothing.
 * Reference: https://www.youtube.com/watch?v=pyhOBrBXtMA
 * */
public class PointlessButton extends ClickableButton {

	private ItemStack item;

	public PointlessButton(ItemStack item) {
		this.item = item;
	}

	/**
	 * Gets the item that will represent this button within the GUI.
	 */
	@Override
	public final ItemStack getButtonItem() {
		return this.item;
	}

	/**
	 * Called when this button is clicked within a GUI.
	 *
	 * @param inventory The GUI this button was called in.
	 * @param player    The player that clicked the button.
	 */
	public void onClick(ClickableInventory inventory, Player player) {}

	/**
	 * Called when this button is added to a GUI.
	 *
	 * @param inventory The GUI this button was added to.
	 * @param slot      The slot of the inventory of the GUI.
	 */
	public void onAddedToInventory(ClickableInventory inventory, int slot) {}

	/**
	 * Called when this button is removed from a GUI.
	 *
	 * @param inventory The GUI this button was removed from.
	 * @param slot      The slot of the inventory of the GUI.
	 */
	public void onRemovedFromInventory(ClickableInventory inventory, int slot) {}

	/**
	 * Called when the GUI is no longer being viewed.
	 *
	 * @param inventory The GUI that is no longer being viewed.
	 * @param slot      The slot of the inventory of the GUI.
	 */
	@Override
	public void onInventoryClose(ClickableInventory inventory, int slot) {
		inventory.emptyOutSlot(slot);
	}

}
