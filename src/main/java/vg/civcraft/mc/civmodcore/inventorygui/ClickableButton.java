package vg.civcraft.mc.civmodcore.inventorygui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.inventorygui.legacy.IClickable;

public abstract class ClickableButton implements IClickable {

	@Override
	@Deprecated
	public final ItemStack getItemStack() {
		return getButtonItem();
	}

	@Override
	@Deprecated
	public final void clicked(Player player) {
		onClick((ClickableInventory) player.getOpenInventory().getTopInventory(), player);
	}

	@Override
	@Deprecated
	public final void addedToInventory(ClickableInventory inventory, int slot) {
		onAddedToInventory(inventory, slot);
	}

	/**
	 * Gets the item that will represent this button within the GUI.
	 * */
	public abstract ItemStack getButtonItem();

	/**
	 * Called when this button is clicked within a GUI.
	 *
	 * @param inventory The GUI this button was called in.
	 * @param player    The player that clicked the button.
	 */
	public abstract void onClick(ClickableInventory inventory, Player player);

	/**
	 * Called when this button is added to a GUI.
	 *
	 * @param inventory The GUI this button was added to.
	 * @param slot      The slot of the inventory of the GUI.
	 */
	public abstract void onAddedToInventory(ClickableInventory inventory, int slot);

	/**
	 * Called when this button is removed from a GUI.
	 *
	 * @param inventory The GUI this button was removed from.
	 * @param slot      The slot of the inventory of the GUI.
	 */
	public abstract void onRemovedFromInventory(ClickableInventory inventory, int slot);

}
