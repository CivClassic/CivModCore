package vg.civcraft.mc.civmodcore.itemHandling;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.api.EnchantingAPI;
import vg.civcraft.mc.civmodcore.api.ItemAPI;

@Deprecated
public class NiceNames {

	/**
	 * @deprecated Replaced with ItemAPI.getItemName();
	 * @see ItemAPI .getItemName();
	 * */
	@Deprecated
	public static String getName(ItemStack item) {
		return ItemAPI.getItemName(item);
	}

	/**
	 * @deprecated Replaced with EnchantingAPI.getEnchantmentName();
	 * @see EnchantingAPI .getEnchantmentName();
	 * */
	@Deprecated
	public static String getName(Enchantment enchant) {
		return EnchantingAPI.getEnchantmentName(enchant);
	}

	/**
	 * @deprecated Replaced with EnchantingAPI.getEnchantmentInitials();
	 * @see EnchantingAPI .getEnchantmentInitials();
	 * */
	@Deprecated
	public static String getAcronym(Enchantment enchant) {
		return EnchantingAPI.getEnchantmentInitials(enchant);
	}

	/**
	 * @deprecated Replaced with ItemAPI.addCustomItemName();
	 * @see ItemAPI .addCustomItemName();
	 * */
	@Deprecated
	public static void registerItem(ItemStack item, String name) {
		ItemAPI.addCustomItemName(item, name);
	}

}
