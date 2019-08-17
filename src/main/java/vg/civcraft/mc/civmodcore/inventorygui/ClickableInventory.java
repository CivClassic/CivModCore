package vg.civcraft.mc.civmodcore.inventorygui;

import java.util.UUID;

import io.protonull.utilities.libs.javax.validation.constraints.Max;
import io.protonull.utilities.libs.javax.validation.constraints.Min;
import io.protonull.utilities.libs.javax.validation.constraints.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.inventorygui.legacy.IClickable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ClickableInventory {

	public enum Occupation {
		NONE,
		BUTTON,
		ITEM
	}

	private Inventory inventory;
	private IClickable[] buttons;
	private int size;

	public ClickableInventory(@NotNull InventoryType type, @NotNull String name) {
		if (type == InventoryType.CREATIVE) {
			throw new IllegalArgumentException("Cannot use CREATIVE as a clickable inventory.");
		}
		if (name.length() > 32) {
			name = name.substring(0, 32);
		}
		this.inventory = Bukkit.createInventory(null, type, name);
		this.buttons = new IClickable[this.inventory.getSize()];
		this.size = this.buttons.length;
	}

	public ClickableInventory(@Min(1) @Max(54) int size, @NotNull String name) {
		if ((size % 9) != 0) {
			throw new IllegalArgumentException("ClickableInventory's size must be divisible by 9");
		}
		if (name.length() > 32) {
			name = name.substring(0, 32);
		}
		this.inventory = Bukkit.createInventory(null, size, name);
		this.buttons = new IClickable[size];
		this.size = size;
	}

	public ClickableInventory(@NotNull Inventory inventory) {
		this.inventory = inventory;
		this.buttons = new IClickable[this.inventory.getSize()];
		this.size = this.buttons.length;
	}

	public final @Nonnull Occupation isSlotOccupied(int slot) {
		if (slot < 0 || slot >= this.size) {
			return Occupation.NONE;
		}
		if (this.buttons[slot] != null) {
			return Occupation.BUTTON;
		}
		if (this.inventory.getItem(slot) != null) {
			return Occupation.ITEM;
		}
		return Occupation.NONE;
	}

	/**
	 * Shutdown the inventory
	 * */
	public final void prepareForClose() {
		for (int i = 0; i < this.size; i++) {
			ClickableButton button = getButtonSlot(i);
			if (button == null) {
				continue;
			}
			button.onInventoryClose(this, i);
		}
	}

	/**
	 * Empties a slot of buttons and items.
	 * @throws IndexOutOfBoundsException if the slot is out of bounds
	 * */
	public final void emptyOutSlot(int slot) {
		if (slot < 0 || slot >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		ClickableButton button = getButtonSlot(slot);
		if (button != null) {
			button.onRemovedFromInventory(this, slot);
		}
		this.buttons[slot] = null;
		this.inventory.setItem(slot, null);
	}

	/**
	 * Sets a clickable button to a slot.
	 * WARNING: This will overwrite any item set through the inventory or through .setItemSlot(); You should check with
	 * if the slot is occupied first before setting anything. This will also not cause an inventory update.
	 * @see ClickableInventory#isSlotOccupied(int)
	 * @see ClickableInventoryManager#updateInventory(ClickableInventory)
	 * @throws IndexOutOfBoundsException if the slot is out of bounds
	 * */
	public final void setButtonSlot(ClickableButton button, int slot) {
		if (slot < 0 || slot >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		emptyOutSlot(slot);
		if (button != null) {
			button.onAddedToInventory(this, slot);
			this.inventory.setItem(slot, button.getButtonItem());
			this.buttons[slot] = button;
		}
	}

	/**
	 * Gets a clickable button from a slot.
	 * WARNING: The inventory's items are not considered at all, only ClickableInventory's internal buttons array. Don't
	 * assume that just because a button isn't present in this slot, that this slot is objectively empty.
	 * @throws IndexOutOfBoundsException if the slot is out of bounds
	 * */
	public final @Nullable ClickableButton getButtonSlot(int slot) {
		if (slot < 0 || slot >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		IClickable clickable = this.buttons[slot];
		if (clickable instanceof ClickableButton) {
			return (ClickableButton) clickable;
		}
		return null;
	}

	/**
	 * Sets an item to a slot.
	 * WARNING: This will nullify any button set to this slot. You should check with if the slot is occupied first
	 * before setting anything. This will also not cause an inventory update.
	 * @see ClickableInventory#isSlotOccupied(int)
	 * @see ClickableInventoryManager#updateInventory(ClickableInventory)
	 * @throws IndexOutOfBoundsException if the slot is out of bounds
	 * */
	public final void setItemSlot(ItemStack item, int slot) {
		if (slot < 0 || slot >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		emptyOutSlot(slot);
		this.inventory.setItem(slot, item);
		this.buttons[slot] = null;
	}

	/**
	 * Gets an item from a slot.
	 * WARNING: If a button exists in this slot, null will be returned. Don't assume that because no item was returned
	 * that the slot is objectively empty.
	 * @throws IndexOutOfBoundsException if the slot is out of bounds
	 * */
	public final ItemStack getItemSlot(int slot) {
		if (slot < 0 || slot >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		if (this.buttons[slot] != null) {
			return null;
		}
		return this.inventory.getItem(slot);
	}

	/**
	 * Click a button if a button exists in that slot.
	 * @throws IndexOutOfBoundsException if the slot is out of bounds
	 * */
	public final void clickButtonSlot(Player player, int slot) {
		if (slot < 0 || slot >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		IClickable clickable = this.buttons[slot];
		if (clickable instanceof ClickableButton) {
			((ClickableButton) clickable).onClick(this, player);
			return;
		}
		clickable.clicked(player);
	}

	public final Inventory getInventory() {
		return this.inventory;
	}

	public final int getSize() {
		return this.size;
	}

	// Deprecated functions

	/**
	 * Sets a specific slot to use the given Clickable and also updates its item in the inventory. This will overwrite
	 * any existing clickable for this slot, but wont update player inventories on it's own, which means if this applied
	 * while a player has an inventory open, the visual for him isn't changed, but the functionality behind the scenes
	 * is, possibly resulting in something the player did not want to do. Either only use this if you are sure noone has
	 * this inventory open currently or update the inventories right away
	 * @see ClickableInventory#setButtonSlot(ClickableButton, int)
	 */
	@Deprecated
	public final void setSlot(IClickable clickable, int slot) {
		if (slot >= 0 || slot < this.size) {
			clickable.addedToInventory(this, slot);
			this.inventory.setItem(slot, clickable.getItemStack());
			this.buttons[slot] = clickable;
		}
	}

	/**
	 * Gets which Clickable currently represents the given slot in this instance.
	 * @see ClickableInventory#getButtonSlot(int)
	 */
	@Deprecated
	public final IClickable getSlot(int slot) {
		if (slot < 0 || slot >= this.size) {
			return null;
		}
		emptyOutSlot(slot);
		return this.buttons[slot];
	}

	/**
	 * This puts the given clickable in the first empty slot of this instance. If there are no empty slots, this will
	 * fail quietly. Make sure to not add clickables, whichs item representation is identical to another already in this
	 * inventory existing clickable, because their item representation would stack in the inventory
	 */
	public final void addSlot(IClickable clickable) {
		for (int i = 0; i < this.size; i++) {
			if (isSlotOccupied(i) == Occupation.NONE) {
				setSlot(clickable, i);
				break;
			}
		}
	}

	/**
	 * Called whenever a player clicks an item in a clickable inventory. This executes the clicked items clickable and
	 * also closes the clickable inventory, unless the clicked object was a decoration stack
	 * @see ClickableInventory#clickButtonSlot(Player, int)
	 */
	@Deprecated
	public final void itemClick(Player player, int slot) {
		try {
			clickButtonSlot(player, slot);
		}
		catch (IndexOutOfBoundsException ignored) {}
	}

	/**
	 * Shows a player the inventory of this instance with all of its clickables
	 * @see ClickableInventoryManager#showInventory(Player, ClickableInventory)
	 */
	@Deprecated
	public final void showInventory(Player player) {
		ClickableInventoryManager.showInventory(player, this);
	}

	/**
	 * Updates the inventories of this instance for all players who have it currently open and syncs it with the
	 * internal representation.
	 * @see ClickableInventoryManager#updateInventory(ClickableInventory)
	 */
	@Deprecated
	public final void updateInventory() {
		ClickableInventoryManager.updateInventory(this);
	}

	/**
	 * Gets the index of any given Clickable in this instance
	 */
	public final int indexOf(IClickable clickable) {
		for (int i = 0; i < this.size; i++) {
			if (this.buttons[i] == clickable) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Sets a certain item slot, while bypassing the clickable structure
	 * @see ClickableInventory#setItemSlot(ItemStack, int) 
	 */
	@Deprecated
	public final void setItem(ItemStack item, int slot) {
		setItemSlot(item, slot);
	}

	/**
	 * Closes a players clickable inventory if he has one open. This will also close any other inventory the player has
	 * possibly open, but no problems will occur if this is called while no clickable inventory was actually open
	 * @see ClickableInventoryManager#closeInventory(Player)
	 */
	@Deprecated
	public static void forceCloseInventory(Player player) {
		ClickableInventoryManager.closeInventory(player);
	}

	/**
	 * Called whenever a player closes a clickable inventory to update this in the internal tracking
	 * @see ClickableInventoryManager#inventoryClosed(Player)
	 */
	@Deprecated
	public static void inventoryWasClosed(Player player) {
		ClickableInventoryManager.inventoryClosed(player);
	}

	/**
	 * Checks whether a player has a clickable inventory open currently
	 * @see ClickableInventoryManager#getOpenClickable(Player)
	 */
	@Deprecated
	public static boolean hasClickableInventoryOpen(Player player) {
		return ClickableInventoryManager.getOpenClickable(player) != null;
	}

	/**
	 * Checks whether the player with this uuid has a clickable inventory open
	 * @see ClickableInventoryManager#getOpenClickable(UUID)
	 */
	@Deprecated
	public static boolean hasClickableInventoryOpen(UUID uuid) {
		return ClickableInventoryManager.getOpenClickable(uuid) != null;
	}

	/**
	 * Gets which clickable inventory the given player has currently open
	 * @see ClickableInventoryManager#getOpenClickable(Player)
	 */
	@Deprecated
	public static ClickableInventory getOpenInventory(Player player) {
		return ClickableInventoryManager.getOpenClickable(player);
	}

	/**
	 * Gets which clickable inventory the player with the given uuid has currently open
	 * @see ClickableInventoryManager#getOpenClickable(UUID)
	 */
	@Deprecated
	public static ClickableInventory getOpenInventory(UUID uuid) {
		return ClickableInventoryManager.getOpenClickable(uuid);
	}

}
