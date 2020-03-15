package vg.civcraft.mc.civmodcore.api;

import java.util.function.Function;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * This is a static class of generally useful APIs regarding items.
 */
public final class ItemAPI {

    private ItemAPI() {
    }

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

    /**
     * Determines whether two item stacks are functionally identical.
     *
     * @param former The first item.
     * @param latter The second item.
     * @return Returns true if both items are equal and not null.
     *
     * @see ItemStack#equals(Object)
     */
    public static boolean areItemsEqual(ItemStack former, ItemStack latter) {
        return former != null && former.equals(latter);
    }

    /**
     * Determines whether two item stacks are similar.
     *
     * @param former The first item.
     * @param latter The second item.
     * @return Returns true if both items are similar and not null.
     *
     * @see ItemStack#isSimilar(ItemStack)
     */
    public static boolean areItemsSimilar(ItemStack former, ItemStack latter) {
        return former != null && former.isSimilar(latter);
    }

    /**
     * Handles an item's metadata.
     *
     * @param item The item to handle the metadata of.
     * @param handler The item metadata handler, which should return true if modifications were made.
     * @return Returns true if the metadata was successfully handled.
     *
     * @see ItemStack#getItemMeta()
     */
    @SuppressWarnings("unchecked")
    public static <T> boolean handleItemMeta(ItemStack item, Function<T, Boolean> handler) {
        if (item == null || handler == null) {
            return false;
        }
        T meta;
        try {
            meta = (T) item.getItemMeta();
        }
        catch (ClassCastException ignored) {
            return false;
        }
        if (meta == null) {
            return false;
        }
        if (handler.apply(meta)) {
            return item.setItemMeta((ItemMeta) meta);
        }
        return true;
    }

}
