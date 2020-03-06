package vg.civcraft.mc.civmodcore.api;

import org.bukkit.inventory.ItemStack;

/**
 * This is a static class of generally useful APIs regarding items.
 */
@SuppressWarnings("unused")
public final class ItemAPI {

    private ItemAPI() { }

    /**
     * Checks whether an item would be considered valid.
     *
     * @param item The item to check.
     * @return Returns true if the item would be considered valid.
     */
    public static boolean isValidItem(ItemStack item) {
        if (item == null) {
            return false;
        }
        if (!MaterialAPI.isValidItemMaterial(item.getType())) {
            return false;
        }
        if (item.getAmount() < 1) {
            return false;
        }
        if (item.getAmount() > item.getMaxStackSize()) {
            return false;
        }
        return true;
    }

}
