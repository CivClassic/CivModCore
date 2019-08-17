package vg.civcraft.mc.civmodcore.inventorygui;

import java.util.List;

import io.protonull.utilities.libs.javax.validation.constraints.Min;
import io.protonull.utilities.libs.javax.validation.constraints.NotEmpty;
import io.protonull.utilities.libs.javax.validation.constraints.NotNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vg.civcraft.mc.civmodcore.CivModCorePlugin;

public class AnimatedClickable extends ClickableButton {

	private List<ItemStack> items;
	private long timing;
	private int currentPos = 0;
	private BukkitTask task = null;

	public AnimatedClickable(@NotNull @NotEmpty List<ItemStack> stacks, @Min(1) long timing) {
		this.items = stacks;
		this.timing = timing;
	}

	public ItemStack getNext() {
		if (++this.currentPos >= this.items.size()) {
			this.currentPos = 0;
		}
		return this.items.get(this.currentPos);
	}

	/**
	 * @return How often this instance will switch its item representation, think ticks per frame
	 */
	public long getTiming() {
		return this.timing;
	}

	/**
	 * Gets the item that will represent this button within the GUI.
	 */
	@Override
	public ItemStack getButtonItem() {
		return this.items.get(this.currentPos);
	}

	/**
	 * Called when this button is clicked within a GUI.
	 *
	 * @param inventory The GUI this button was called in.
	 * @param player    The player that clicked the button.
	 */
	@Override
	public void onClick(ClickableInventory inventory, Player player) {}

	/**
	 * Called when this button is added to a GUI.
	 *
	 * @param inventory The GUI this button was added to.
	 * @param slot      The slot of the inventory of the GUI.
	 */
	@Override
	public void onAddedToInventory(ClickableInventory inventory, int slot) {
		if (this.task != null) {
			onRemovedFromInventory(inventory, slot);
		}
		this.task = new BukkitRunnable() {
			@Override
			public void run() {
				inventory.getInventory().setItem(slot, getNext());
				ClickableInventoryManager.updateInventory(inventory);
			}
		}.runTaskTimer(CivModCorePlugin.getInstance(), this.timing, this.timing);
	}

	/**
	 * Called when this button is removed from a GUI.
	 *
	 * @param inventory The GUI this button was removed from.
	 * @param slot      The slot of the inventory of the GUI.
	 */
	@Override
	public void onRemovedFromInventory(ClickableInventory inventory, int slot) {
		if (this.task != null) {
			this.task.cancel();
			this.task = null;
		}
	}

}
