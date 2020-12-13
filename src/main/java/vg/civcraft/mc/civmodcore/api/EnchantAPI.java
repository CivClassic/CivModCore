package vg.civcraft.mc.civmodcore.api;

import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.inventory.items.EnchantUtils;

/**
 * @deprecated Use {@link EnchantUtils} instead.
 */
@Deprecated
public final class EnchantAPI {

	/**
	 * @deprecated Use {@link EnchantUtils#isSafeEnchantment(Enchantment, int)} instead.
	 */
	@Deprecated
	public static boolean isSafeEnchantment(Enchantment enchantment, int level) {
		return EnchantUtils.isSafeEnchantment(enchantment, level);
	}

	/**
	 * @deprecated Use {@link EnchantUtils#getEnchantment(String)} instead.
	 */
	@Deprecated
	public static Enchantment getEnchantment(String value) {
		return EnchantUtils.getEnchantment(value);
	}

	/**
	 * @deprecated Use {@link EnchantUtils#getEnchantments(ItemStack)} instead.
	 */
	@Deprecated
	public static Map<Enchantment, Integer> getEnchantments(ItemStack item) {
		return EnchantUtils.getEnchantments(item);
	}

	/**
	 * @deprecated Use {@link EnchantUtils#addEnchantment(ItemStack, Enchantment, int)} instead.
	 */
	@Deprecated
	public static boolean addEnchantment(ItemStack item, Enchantment enchant, int level) {
		return EnchantUtils.addEnchantment(item, enchant, level);
	}

	/**
	 * @deprecated Use {@link EnchantUtils#addEnchantment(ItemStack, Enchantment, int, boolean)} instead.
	 */
	@Deprecated
	public static boolean addEnchantment(ItemStack item, Enchantment enchant, int level, boolean safeOnly) {
		return EnchantUtils.addEnchantment(item, enchant, level, safeOnly);
	}

	/**
	 * @deprecated Use {@link EnchantUtils#removeEnchantment(ItemStack, Enchantment)} instead.
	 */
	@Deprecated
	public static boolean removeEnchantment(ItemStack item, Enchantment enchant) {
		return EnchantUtils.removeEnchantment(item, enchant);
	}

	/**
	 * @deprecated Use {@link EnchantUtils#clearEnchantments(ItemStack)} instead.
	 */
	@Deprecated
	public static void clearEnchantments(ItemStack item) {
		EnchantUtils.clearEnchantments(item);
	}

}
