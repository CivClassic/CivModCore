package vg.civcraft.mc.civmodcore.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import vg.civcraft.mc.civmodcore.inventory.items.ItemUtils;

/**
 * @deprecated Use {@link ItemUtils} instead.
 */
@Deprecated
public final class ItemAPI {

	/**
	 * @deprecated Use {@link ItemUtils#isValidItem(ItemStack)} instead.
	 */
	@Deprecated
	public static boolean isValidItem(ItemStack item) {
		return ItemUtils.isValidItem(item);
	}

	/**
	 * @deprecated Use {@link ItemUtils#isValidItemAmount(ItemStack)} instead.
	 */
	@Deprecated
	public static boolean isValidItemAmount(ItemStack item) {
		return ItemUtils.isValidItemAmount(item);
	}

	/**
	 * @deprecated Use {@link ItemUtils#areItemsEqual(ItemStack, ItemStack)} instead.
	 */
	@Deprecated
	public static boolean areItemsEqual(ItemStack former, ItemStack latter) {
		return ItemUtils.areItemsEqual(former, latter);
	}

	/**
	 * @deprecated Use {@link ItemUtils#areItemsSimilar(ItemStack, ItemStack)} instead.
	 */
	@Deprecated
	public static boolean areItemsSimilar(ItemStack former, ItemStack latter) {
		return ItemUtils.areItemsSimilar(former, latter);
	}

	/**
	 * @deprecated Use {@link ItemUtils#decrementItem(ItemStack)} instead.
	 */
	@Deprecated
	public static ItemStack decrementItem(ItemStack item) {
		return ItemUtils.decrementItem(item);
	}

	/**
	 * @deprecated Use {@link ItemUtils#normalizeItem(ItemStack)} instead.
	 */
	@Deprecated
	public static ItemStack normalizeItem(ItemStack item) {
		return ItemUtils.normalizeItem(item);
	}

	/**
	 * @deprecated Use {@link ItemUtils#getItemMeta(ItemStack)} instead.
	 */
	@Deprecated
	public static ItemMeta getItemMeta(ItemStack item) {
		return Objects.requireNonNull(ItemUtils.getItemMeta(item));
	}

	/**
	 * @deprecated Use {@link ItemUtils#getDisplayName(ItemStack)} instead.
	 */
	@Deprecated
	public static String getDisplayName(ItemStack item) {
		ItemMeta meta = getItemMeta(item);
		String name = meta.getDisplayName();
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return name;
	}

	/**
	 * @deprecated Use {@link ItemUtils#setDisplayName(ItemStack, String)} instead.
	 */
	@Deprecated
	public static void setDisplayName(ItemStack item, String name) {
		ItemUtils.setDisplayName(item, name);
	}

	/**
	 * @deprecated Use {@link ItemUtils#getLore(ItemStack)} instead.
	 */
	@Deprecated
	public static List<String> getLore(ItemStack item) {
		ItemMeta meta = getItemMeta(item);
		List<String> lore = meta.getLore();
		if (lore == null) {
			return new ArrayList<>();
		}
		return lore;
	}

	/**
	 * @deprecated Use {@link ItemUtils#setLore(ItemStack, String...)} instead.
	 */
	@Deprecated
	public static void setLore(ItemStack item, String... lines) {
		ItemUtils.setLore(item, lines);
	}

	/**
	 * @deprecated Use {@link ItemUtils#setLore(ItemStack, List)} instead.
	 */
	@Deprecated
	public static void setLore(ItemStack item, List<String> lines) {
		ItemUtils.setLore(item, lines);
	}

	/**
	 * @deprecated Use {@link ItemUtils#addLore(ItemStack, String...)} instead.
	 */
	@Deprecated
	public static void addLore(ItemStack item, String... lines) {
		ItemUtils.addLore(item, lines);
	}

	/**
	 * @deprecated Use {@link ItemUtils#addLore(ItemStack, List)} instead.
	 */
	@Deprecated
	public static void addLore(ItemStack item, List<String> lines) {
		ItemUtils.addLore(item, lines);
	}

	/**
	 * @deprecated Use {@link ItemUtils#addLore(ItemStack, boolean, String...)} instead.
	 */
	@Deprecated
	public static void addLore(ItemStack item, boolean prepend, String... lines) {
		ItemUtils.addLore(item, prepend, lines);
	}

	/**
	 * @deprecated Use {@link ItemUtils#addLore(ItemStack, boolean, List)} instead.
	 */
	@Deprecated
	public static void addLore(ItemStack item, boolean prepend, List<String> lines) {
		ItemUtils.addLore(item, prepend, lines);
	}

	/**
	 * @deprecated Use {@link ItemUtils#clearLore(ItemStack)} instead.
	 */
	@Deprecated
	public static void clearLore(ItemStack item) {
		ItemUtils.clearLore(item);
	}

	/**
	 * @deprecated Use {@link ItemUtils#getDamageable(ItemStack)} instead.
	 */
	@Deprecated
	public static Damageable getDamageable(ItemStack item) {
		return ItemUtils.getDamageable(item);
	}

	/**
	 * @deprecated Use {@link ItemUtils#addGlow(ItemStack)} instead.
	 */
	@Deprecated
	public static void addGlow(ItemStack item) {
		ItemUtils.addGlow(item);
	}

	/**
	 * @deprecated Use {@link ItemUtils#handleItemMeta(ItemStack, Predicate)} instead.
	 */
	@Deprecated
	public static <T> boolean handleItemMeta(ItemStack item, Predicate<T> handler) {
		return ItemUtils.handleItemMeta(item, handler);
	}

}
