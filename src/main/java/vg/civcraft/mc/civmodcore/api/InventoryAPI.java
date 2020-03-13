package vg.civcraft.mc.civmodcore.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.BeaconInventory;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.PlayerInventory;
import vg.civcraft.mc.civmodcore.structs.ClonedInventory;

public final class InventoryAPI {

    private InventoryAPI() {
    }

    /**
     * Tests an inventory to see if it's valid.
     *
     * @param inventory The inventory to test.
     * @return Returns true if it's more likely than not valid.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isValidInventory(Inventory inventory) {
        if (inventory == null) {
            return false;
        }
        if (inventory.getSize() <= 0) {
            return false;
        }
        return true;
    }

    /**
     * Get the players viewing an inventory.
     *
     * @param inventory The inventory to get the viewers of.
     * @return Returns a list of players. Returns an empty list if the inventory is null.
     */
    public static List<Player> getViewingPlayers(Inventory inventory) {
        if (inventory == null) {
            return new ArrayList<>();
        }
        return inventory.getViewers().stream().
                filter(EntityAPI::isPlayer).
                map((player) -> (Player) player).
                collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Attempts to find the first safe place to put an item.
     *
     * @param inventory The inventory to attempt to find a slot in.
     * @param item The item to find a place for.
     * @return Returns an index of a slot that it's safe to add to. A return value of -1 means no safe place.
     */
    public static int firstEmpty(Inventory inventory, ItemStack item) {
        if (inventory == null) {
            return -1;
        }
        // If there's a slot free, then just return that. Otherwise if
        // the item is invalid, just return whatever slot was returned.
        int index = inventory.firstEmpty();
        if (index >= 0 || !ItemAPI.isValidItem(item)) {
            return index;
        }
        // If gets here, then we're certain that there's no stacks free.
        // If the amount of the item to add is larger than a stack, then
        // it can't be merged with another stack. So just back out.
        int remainder = item.getMaxStackSize() - item.getAmount();
        if (remainder <= 0) {
            return -1;
        }
        // Find all items that match with the given item to see if there's
        // a stack that can be merged with. If none can be found, back out.
        for (Map.Entry<Integer, ? extends ItemStack> entry : inventory.all(item).entrySet()) {
            if (entry.getValue().getAmount() <= remainder) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * Clones the given inventory for the purpose of manipulating the contents.
     *
     * @param inventory The inventory to clone.
     * @return Returns a clone of the given inventory.
     *
     * @apiNote Do not type check the inventory, it's JUST a contents copy within an inventory wrapper to provide the
     *         relevant and useful methods.
     */
    public static Inventory cloneInventory(Inventory inventory) {
        return ClonedInventory.cloneInventory(inventory);
    }

    /**
     * Will safely add a set of items to an inventory. If not all items are added, it's not committed to the inventory.
     *
     * @param inventory The inventory to add the items to.
     * @param itemsToAdd The items to add to the inventory.
     * @return Returns true if the items were safely added.
     */
    public static boolean safelyAddItemsToInventory(Inventory inventory, ItemStack[] itemsToAdd) {
        String errorMessage = "Could not add item to inventory: ";
        if (!isValidInventory(inventory)) {
            throw new IllegalArgumentException(errorMessage + "the inventory is invalid.");
        }
        if (itemsToAdd == null || itemsToAdd.length <= 0) {
            throw new IllegalArgumentException(errorMessage + "no items to add.");
        }
        Inventory clone = cloneInventory(inventory);
        for (ItemStack itemToAdd : itemsToAdd) {
            if (firstEmpty(clone, itemToAdd) < 0) {
                return false;
            }
            if (!clone.addItem(itemToAdd).isEmpty()) {
                return false;
            }
        }
        inventory.setContents(clone.getContents());
        return true;
    }

    /**
     * Will safely remove a set of items from an inventory. If not all items are removed, it's not committed to the
     * inventory.
     *
     * @param inventory The inventory to remove the items from.
     * @param itemsToRemove The items to remove to the inventory.
     * @return Returns true if the items were safely removed.
     */
    public static boolean safelyRemoveItemsFromInventory(Inventory inventory, ItemStack[] itemsToRemove) {
        String errorMessage = "Could not remove item from inventory: ";
        if (!isValidInventory(inventory)) {
            throw new IllegalArgumentException(errorMessage + "the inventory is invalid.");
        }
        if (itemsToRemove == null || itemsToRemove.length <= 0) {
            throw new IllegalArgumentException(errorMessage + "no items to remove.");
        }
        Inventory clone = cloneInventory(inventory);
        for (ItemStack itemToRemove : itemsToRemove) {
            if (!clone.removeItem(itemToRemove).isEmpty()) {
                return false;
            }
        }
        inventory.setContents(clone.getContents());
        return true;
    }

    /**
     * Will safely transact a set of items from one inventory to another inventory. If not all items are transacted, the
     * transaction is not committed.
     *
     * @param from The inventory to move the given items from.
     * @param items The items to transact.
     * @param to The inventory to move the given items to.
     * @return Returns true if the items were successfully transacted.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean safelyTransactBetweenInventories(Inventory from, ItemStack[] items, Inventory to) {
        String errorMessage = "Could not transact items between inventories: ";
        if (!isValidInventory(from)) {
            throw new IllegalArgumentException(errorMessage + "the from inventory is invalid.");
        }
        if (!isValidInventory(to)) {
            throw new IllegalArgumentException(errorMessage + "the to inventory is invalid.");
        }
        if (items == null || items.length <= 0) {
            throw new IllegalArgumentException(errorMessage + "no items to transact.");
        }
        Inventory fromClone = cloneInventory(from);
        Inventory toClone = cloneInventory(to);
        if (!safelyRemoveItemsFromInventory(fromClone, items)) {
            return false;
        }
        if (!safelyAddItemsToInventory(toClone, items)) {
            return false;
        }
        from.setContents(fromClone.getContents());
        to.setContents(toClone.getContents());
        return true;
    }

    /**
     * Will safely trade items between inventories. If not all items are traded, the trade is cancelled.
     *
     * @param formerInventory The first inventory.
     * @param formerItems The items to trade from the first inventory to give to the second inventory.
     * @param latterInventory The second inventory.
     * @param latterItems The items to trade from the second inventory to give to the first inventory.
     * @return Returns true if the trade succeeded.
     */
    public static boolean safelyTradeBetweenInventories(Inventory formerInventory, ItemStack[] formerItems,
                                                        Inventory latterInventory, ItemStack[] latterItems) {
        String errorMessage = "Cannot complete inventory transaction: ";
        if (!InventoryAPI.isValidInventory(formerInventory)) {
            throw new IllegalArgumentException(errorMessage + "the former inventory is invalid.");
        }
        if (!InventoryAPI.isValidInventory(latterInventory)) {
            throw new IllegalArgumentException(errorMessage + "the latter inventory is invalid.");
        }
        if (formerItems == null || formerItems.length <= 0) {
            throw new IllegalArgumentException(errorMessage + "no items to trade from the former inventory.");
        }
        if (latterItems == null || latterItems.length <= 0) {
            throw new IllegalArgumentException(errorMessage + "no items to trade from the latter inventory.");
        }
        Inventory formerClone = InventoryAPI.cloneInventory(formerInventory);
        Inventory latterClone = InventoryAPI.cloneInventory(latterInventory);
        if (!InventoryAPI.safelyTransactBetweenInventories(formerClone, formerItems, latterClone)) {
            return false;
        }
        if (!InventoryAPI.safelyTransactBetweenInventories(latterClone, latterItems, formerClone)) {
            return false;
        }
        formerInventory.setContents(formerClone.getContents());
        latterInventory.setContents(latterClone.getContents());
        return true;
    }

    /**
     * Checks whether an inventory is that of a chest.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static Inventory getChestInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.CHEST) {
            return null;
        }
        return inventory;
    }

    /**
     * Checks whether an inventory is that of a dispenser.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static Inventory getDispenserInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.DISPENSER) {
            return null;
        }
        return inventory;
    }

    /**
     * Checks whether an inventory is that of a dropper.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static Inventory getDropperInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.DROPPER) {
            return null;
        }
        return inventory;
    }

    /**
     * Checks whether an inventory is that of a furnace.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static FurnaceInventory getFurnaceInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.FURNACE) {
            return null;
        }
        if (!(inventory instanceof FurnaceInventory)) {
            return null;
        }
        return (FurnaceInventory) inventory;
    }

    /**
     * Checks whether an inventory is that of a workbench.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static Inventory getWorkbenchInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.WORKBENCH) {
            return null;
        }
        return inventory;
    }

    /**
     * Checks whether an inventory is that of a crafting window.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static CraftingInventory getCraftingInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.CRAFTING) {
            return null;
        }
        if (!(inventory instanceof CraftingInventory)) {
            return null;
        }
        return (CraftingInventory) inventory;
    }

    /**
     * Checks whether an inventory is that of an enchanting window.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static EnchantingInventory getEnchantingInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.ENCHANTING) {
            return null;
        }
        if (!(inventory instanceof EnchantingInventory)) {
            return null;
        }
        return (EnchantingInventory) inventory;
    }

    /**
     * Checks whether an inventory is that of a brewing window.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static BrewerInventory getBrewingInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.BREWING) {
            return null;
        }
        if (!(inventory instanceof BrewerInventory)) {
            return null;
        }
        return (BrewerInventory) inventory;
    }

    /**
     * Checks whether an inventory is that of a player.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static PlayerInventory getPlayerInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.PLAYER) {
            return null;
        }
        if (!(inventory instanceof PlayerInventory)) {
            return null;
        }
        return (PlayerInventory) inventory;
    }

    /**
     * Checks whether an inventory is that of a creative window.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static Inventory getCreativeInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.CREATIVE) {
            return null;
        }
        return inventory;
    }

    /**
     * Checks whether an inventory is that of a merchant.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static MerchantInventory getMerchantInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.MERCHANT) {
            return null;
        }
        if (!(inventory instanceof MerchantInventory)) {
            return null;
        }
        return (MerchantInventory) inventory;
    }

    /**
     * Checks whether an inventory is that of an ender chest.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static Inventory getEnderChestInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.ENDER_CHEST) {
            return null;
        }
        return inventory;
    }

    /**
     * Checks whether an inventory is that of an anvil.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static AnvilInventory getAnvilInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.ANVIL) {
            return null;
        }
        if (!(inventory instanceof AnvilInventory)) {
            return null;
        }
        return (AnvilInventory) inventory;
    }

    /**
     * Checks whether an inventory is that of a beacon.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static BeaconInventory getBeaconInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.BEACON) {
            return null;
        }
        if (!(inventory instanceof BeaconInventory)) {
            return null;
        }
        return (BeaconInventory) inventory;
    }

    /**
     * Checks whether an inventory is that of a hopper.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static Inventory getHopperInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.HOPPER) {
            return null;
        }
        return inventory;
    }

    /**
     * Checks whether an inventory is that of a shulker box.
     *
     * @param inventory The inventory to check.
     * @return Returns true if the type matches.
     */
    public static Inventory getShulkerBoxInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory.getType() != InventoryType.SHULKER_BOX) {
            return null;
        }
        return inventory;
    }

}
