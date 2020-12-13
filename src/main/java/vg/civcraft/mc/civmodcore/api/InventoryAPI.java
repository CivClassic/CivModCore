package vg.civcraft.mc.civmodcore.api;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.inventory.ClonedInventory;
import vg.civcraft.mc.civmodcore.inventory.InventoryUtils;

/**
 * @deprecated Use {@link InventoryUtils} instead.
 */
@Deprecated
public final class InventoryAPI {

	/**
	 * @deprecated Use {@link InventoryUtils#isValidInventory(Inventory)} instead.
	 */
	@Deprecated
	public static boolean isValidInventory(Inventory inventory) {
		return InventoryUtils.isValidInventory(inventory);
	}

	/**
	 * @deprecated Use {@link InventoryUtils#isValidInventory(Inventory)} instead.
	 */
	@Deprecated
	public static List<Player> getViewingPlayers(Inventory inventory) {
		return InventoryUtils.getViewingPlayers(inventory);
	}

	/**
	 * @deprecated Use {@link InventoryUtils#firstEmpty(Inventory, ItemStack)} instead.
	 */
	@Deprecated
	public static int firstEmpty(Inventory inventory, ItemStack item) {
		return InventoryUtils.firstEmpty(inventory, item);
	}

	/**
	 * @deprecated Use {@link InventoryUtils#clearInventory(Inventory)} instead.
	 */
	@Deprecated
	public static void clearInventory(Inventory inventory) {
		InventoryUtils.clearInventory(inventory);
	}

	/**
	 * @deprecated Use {@link InventoryUtils#hasOtherViewers(Inventory)} instead.
	 */
	@Deprecated
	public static boolean hasOtherViewers(Inventory inventory) {
		return InventoryUtils.hasOtherViewers(inventory);
	}

	/**
	 * @deprecated Use {@link InventoryUtils#isValidChestSize(int)} instead.
	 */
	@Deprecated
	public static boolean isValidChestSize(int slots) {
		return InventoryUtils.isValidChestSize(slots);
	}

	/**
	 * @deprecated Use {@link InventoryUtils#safelyAddItemsToInventory(Inventory, ItemStack[])} instead.
	 */
	@Deprecated
	public static boolean safelyAddItemsToInventory(Inventory inventory, ItemStack[] items) {
		return InventoryUtils.safelyAddItemsToInventory(inventory, items);
	}

	/**
	 * @deprecated Use {@link InventoryUtils#safelyRemoveItemsFromInventory(Inventory, ItemStack[])} instead.
	 */
	@Deprecated
	public static boolean safelyRemoveItemsFromInventory(Inventory inventory, ItemStack[] items) {
		return InventoryUtils.safelyRemoveItemsFromInventory(inventory, items);
	}

	/**
	 * @deprecated Use {@link InventoryUtils#safelyTransactBetweenInventories(Inventory, Inventory, ItemStack[])}
	 *     instead.
	 */
	@Deprecated
	public static boolean safelyTransactBetweenInventories(Inventory from, ItemStack[] items, Inventory to) {
		return InventoryUtils.safelyTransactBetweenInventories(from, to, items);
	}

	/**
	 * @deprecated Use
	 *     {@link InventoryUtils#safelyTradeBetweenInventories(Inventory, Inventory, ItemStack[], ItemStack[])} instead.
	 */
	@Deprecated
	public static boolean safelyTradeBetweenInventories(Inventory formerInventory, ItemStack[] formerItems,
														Inventory latterInventory, ItemStack[] latterItems) {
		return InventoryUtils.safelyTradeBetweenInventories(formerInventory, latterInventory, formerItems, latterItems);
	}

	/**
	 * @deprecated Use {@link ClonedInventory#cloneInventory(Inventory)} instead.
	 */
	@Deprecated
	public static Inventory cloneInventory(Inventory inventory) {
		return ClonedInventory.cloneInventory(inventory);
	}

}
