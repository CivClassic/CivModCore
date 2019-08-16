package vg.civcraft.mc.civmodcore.api;

import io.protonull.utilities.SplitString;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vg.civcraft.mc.civmodcore.CivModCorePlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class EnchantingAPI {
	private EnchantingAPI() {} // Make the class effectively static

	public static boolean isSafeEnchantment(Enchantment enchantment, int level) {
		return enchantment != null && level >= enchantment.getStartLevel() && level <= enchantment.getMaxLevel();
	}

	public static Map<Enchantment, Integer> getItemEnchantments(ItemStack item) {
		ItemMeta meta = ItemAPI.getItemMeta(item);
		if (meta == null || !meta.hasEnchants()) {
			return new HashMap<>();
		}
		return meta.getEnchants();
	}

	public static boolean addEnchantment(ItemStack item, Enchantment enchantment, int level) {
		return addEnchantment(item, enchantment, level, true);
	}

	public static boolean addEnchantment(ItemStack item, Enchantment enchantment, int level, boolean onlyAllowSafeEnchantments) {
		ItemMeta meta = ItemAPI.getItemMeta(item);
		if (meta == null || enchantment == null) {
			return false;
		}
		if (onlyAllowSafeEnchantments && !isSafeEnchantment(enchantment, level)) {
			return false;
		}
		if (!meta.addEnchant(enchantment, level, !onlyAllowSafeEnchantments)) {
			return false;
		}
		item.setItemMeta(meta);
		return true;
	}

	public static boolean removeEnchantment(ItemStack item, Enchantment enchantment) {
		ItemMeta meta = ItemAPI.getItemMeta(item);
		if (meta == null || enchantment == null) {
			return false;
		}
		if (!meta.removeEnchant(enchantment)) {
			return false;
		}
		item.setItemMeta(meta);
		return true;
	}

	public static boolean clearEnchantments(ItemStack item) {
		ItemMeta meta = ItemAPI.getItemMeta(item);
		if (meta == null || !meta.hasEnchants()) {
			return true;
		}
		for (Enchantment enchant : meta.getEnchants().keySet()) {
			if (!meta.removeEnchant(enchant)) {
				return false;
			}
		}
		return true;
	}

	// ------------------------------------------------------------
	// Enchantment Details
	// ------------------------------------------------------------

	private static Map<Enchantment, String> enchantmentNames = new HashMap<>();
	private static Map<Enchantment, String> enchantmentInitials = new HashMap<>();

	public static void resetEnchantmentNames() {
		enchantmentNames.clear();
		enchantmentInitials.clear();
	}

	public static void loadEnchantmentNames() {
		resetEnchantmentNames();
		Logger logger = Bukkit.getLogger();
		try {
			InputStream in = CivModCorePlugin.class.getResourceAsStream("/enchantments.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			while (line != null) {
				SplitString values = new SplitString(line, ",");
				// If there's not at least three values (slug, initials, name) then skip
				if (values.size() < 3) {
					logger.warning("This enchantment row does not have enough data: " + line);
					continue;
				}
				Enchantment enchant = Enchantment.getByName(values.getElement(1));
				// If an enchantment cannot be found by the slug given, skip
				if (enchant == null) {
					logger.warning("Could not find an enchantment on this line: " + line);
					continue;
				}
				String name = values.getElement(2);
				// If the enchantment hasn't been given a name, skip
				if (name.isEmpty()) {
					logger.warning("That enchantment hasn't been given a name: " + line);
					continue;
				}
				// If the name is already in the system, give warning
				if (enchantmentNames.containsValue(name)) {
					logger.warning("That enchantment name already exists: " + line);
				}
				String initials = values.getElement(0);
				// If the enchantment hasn't been given initials, skip
				if (initials.isEmpty()) {
					logger.warning("That enchantment hasn't been given initials: " + line);
					continue;
				}
				// If the initials are already in the system, give warning
				if (enchantmentInitials.containsValue(initials)) {
					logger.warning("Those enchantment initials already exists: " + line);
					continue;
				}
				// Put the name and initials into the system
				enchantmentNames.put(enchant, name);
				enchantmentInitials.put(enchant, initials);
				logger.info(String.format("Enchantment parsed: [%s] [%s] [%s]", enchant.getName(), initials, name));
				line = reader.readLine();
			}
			reader.close();
		}
		catch (IOException e) {
			logger.warning("Could not load enchantments.");
			e.printStackTrace();
		}
	}

	public static String getEnchantmentName(Enchantment enchantment) {
		if (enchantment == null) {
			return null;
		}
		return enchantmentNames.get(enchantment);
	}

	public static String getEnchantmentInitials(Enchantment enchantment) {
		if (enchantment == null) {
			return null;
		}
		return enchantmentInitials.get(enchantment);
	}

}
