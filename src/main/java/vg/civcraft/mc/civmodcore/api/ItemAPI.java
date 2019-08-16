package vg.civcraft.mc.civmodcore.api;

import io.protonull.utilities.Equals;
import io.protonull.utilities.Exists;
import io.protonull.utilities.SplitString;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vg.civcraft.mc.civmodcore.CivModCorePlugin;
import vg.civcraft.mc.civmodcore.util.MaterialDurabilityPair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class ItemAPI {
	private ItemAPI() {} // Make the class effectively static

	/**
	 * Some blocks use durability as a discriminator, to create sub
	 * block types, take Stone and Polished Granite for example.
	 * Minecraft 1.13 will make this function redundant.
	 * */
	public static boolean hasDiscriminator(Material material) {
		switch (material) {
			case STONE:
			case DIRT:
			case WOOD:
			case SAPLING:
			case SAND:
			case LOG:
			case LEAVES:
			case SPONGE:
			case SANDSTONE:
			case LONG_GRASS:
			case WOOL:
			case RED_ROSE:
			case DOUBLE_STEP:
			case STEP:
			case STAINED_GLASS:
			case MONSTER_EGGS:
			case SMOOTH_BRICK:
			case WOOD_DOUBLE_STEP:
			case WOOD_STEP:
			case COBBLE_WALL:
			case QUARTZ_BLOCK:
			case STAINED_CLAY:
			case STAINED_GLASS_PANE:
			case LEAVES_2:
			case LOG_2:
			case PRISMARINE:
			case CARPET:
			case DOUBLE_PLANT:
			case RED_SANDSTONE:
			case CONCRETE:
			case CONCRETE_POWDER:
			case COAL:
			case GOLDEN_APPLE:
			case RAW_FISH:
			case COOKED_FISH:
			case INK_SACK:
			case MONSTER_EGG:
			case SKULL_ITEM:
				return true;
		}
		return false;
	}

	/**
	 * Minecraft 1.13 moves away from using item durability.
	 * Instead it has a new meta interface, Damageable, that can be
	 * instanceof'd, so this function is to duplicate that until
	 * the update to 1.13 or above.
	 * */
	public static boolean instanceofDamageable(Material material) {
		switch (material) {
			case IRON_SPADE:
			case IRON_PICKAXE:
			case IRON_AXE:
			case FLINT_AND_STEEL:
			case BOW:
			case IRON_SWORD:
			case WOOD_SWORD:
			case WOOD_SPADE:
			case WOOD_PICKAXE:
			case WOOD_AXE:
			case STONE_SWORD:
			case STONE_SPADE:
			case STONE_PICKAXE:
			case STONE_AXE:
			case DIAMOND_SWORD:
			case DIAMOND_SPADE:
			case DIAMOND_PICKAXE:
			case DIAMOND_AXE:
			case GOLD_SWORD:
			case GOLD_SPADE:
			case GOLD_PICKAXE:
			case GOLD_AXE:
			case WOOD_HOE:
			case STONE_HOE:
			case IRON_HOE:
			case DIAMOND_HOE:
			case GOLD_HOE:
			case LEATHER_HELMET:
			case LEATHER_CHESTPLATE:
			case LEATHER_LEGGINGS:
			case LEATHER_BOOTS:
			case CHAINMAIL_HELMET:
			case CHAINMAIL_CHESTPLATE:
			case CHAINMAIL_LEGGINGS:
			case CHAINMAIL_BOOTS:
			case IRON_HELMET:
			case IRON_CHESTPLATE:
			case IRON_LEGGINGS:
			case IRON_BOOTS:
			case DIAMOND_HELMET:
			case DIAMOND_CHESTPLATE:
			case DIAMOND_LEGGINGS:
			case DIAMOND_BOOTS:
			case GOLD_HELMET:
			case GOLD_CHESTPLATE:
			case GOLD_LEGGINGS:
			case GOLD_BOOTS:
			case FISHING_ROD:
			case SHEARS:
			case CARROT_STICK:
			case SHIELD:
			case ELYTRA:
				return true;
		}
		return false;
	}

	public static String getMaterialSlug(Material material) {
		return getMaterialSlug(material, (short) 0);
	}

	public static String getMaterialSlug(Material material, short durability) {
		if (material == null) {
			return null;
		}
		if (durability < 0) {
			return null;
		}
		return material.name() + ":" + durability;
	}

	public static MaterialDurabilityPair getMaterialFromSlug(String slug) {
		if (!Exists.string(slug)) {
			return null;
		}
		String[] splitSlug = slug.split(":");
		Material material = Material.getMaterial(splitSlug[0]);
		if (material == null) {
			return null;
		}
		short durability = 0;
		if (splitSlug.length > 1) {
			try {
				durability = Short.parseShort(splitSlug[1]);
			}
			catch (NumberFormatException error) {
				Bukkit.getLogger().warning("Could not parse durability [" + splitSlug[1] + "] into a short!");
			}
		}
		if (durability < 0) {
			return null;
		}
		return new MaterialDurabilityPair(material, durability);
	}

	public static boolean areItemsEqual(ItemStack former, ItemStack latter) {
		return Equals.notNull(former, latter);
	}

	public static boolean areItemsSimilar(ItemStack former, ItemStack latter) {
		if (former == null || latter == null) {
			return false;
		}
		return former.isSimilar(latter);
	}

	public static ItemMeta getItemMeta(ItemStack item) {
		if (item == null || !item.hasItemMeta()) {
			return null;
		}
		return item.getItemMeta();
	}

	public static String getDisplayName(ItemStack item) {
		ItemMeta meta = getItemMeta(item);
		if (meta == null || !meta.hasDisplayName()) {
			return null;
		}
		return meta.getDisplayName();
	}

	public static boolean setDisplayName(ItemStack item, String name) {
		if (item == null) {
			return false;
		}
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return true;
	}

	public static List<String> getLore(ItemStack item) {
		ItemMeta meta = getItemMeta(item);
		if (meta == null || !meta.hasLore()) {
			return new ArrayList<>();
		}
		return meta.getLore();
	}

	public static boolean setLore(ItemStack item, List<String> lines) {
		if (item == null) {
			return false;
		}
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lines);
		item.setItemMeta(meta);
		return true;
	}

	public static boolean addLore(ItemStack item, List<String> lines) {
		return addLore(item, false, lines);
	}

	public static boolean addLore(ItemStack item, boolean prepend, List<String> lines) {
		if (item == null || !Exists.collection(lines)) {
			return false;
		}
		List<String> lore = getLore(item);
		if (prepend) {
			Collections.reverse(lines);
			for (String line : lines) {
				lore.add(0, line);
			}
		}
		else {
			lore.addAll(lines);
		}
		return setLore(item, lore);
	}

	/**
	 * Turns an amount of an item into a series of stacks depending
	 * on the maximum stack size of the item type. The .getAmount()
	 * method is not used, please use the amount parameter.
	 *
	 * The original item is never used, only cloned.
	 * */
	public static ItemStack[] stackifyItem(ItemStack item, int amount) {
		if (item == null || amount < 1) {
			return new ItemStack[0];
		}
		else if (item.getMaxStackSize() <= amount) {
			ItemStack clone = item.clone();
			clone.setAmount(amount);
			return new ItemStack[] {clone};
		}
		else {
			int remainder = amount;
			List<ItemStack> stacks = new ArrayList<>();
			while (remainder > 0) {
				int cloneSize = Math.min(remainder, item.getMaxStackSize());
				ItemStack clone = item.clone();
				clone.setAmount(cloneSize);
				stacks.add(clone);
				remainder -= cloneSize;
			}
			return stacks.toArray(new ItemStack[0]);
		}
	}

	// ------------------------------------------------------------
	// Item Names
	// ------------------------------------------------------------

	private static Map<Integer, String> itemNames = new HashMap<>();

	public static void resetItemNames() {
		itemNames.clear();
	}

	public static void loadItemNames() {
		resetItemNames();
		Logger logger = Bukkit.getLogger();
		try {
			InputStream in = CivModCorePlugin.class.getResourceAsStream("/materials.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			while (line != null) {
				SplitString values = new SplitString(line, ",");
				// If there's not at least three values (slug, data, name) then skip
				if (values.size() < 3) {
					logger.warning("This material row does not have enough data: " + line);
					continue;
				}
				// If a material cannot be found by the slug given, skip
				Material material = Material.getMaterial(values.getElement(0));
				if (material == null) {
					logger.warning("Could not find a material on this line: " + line);
					continue;
				}
				// If the name is empty, skip
				String name = values.getElement(2);
				if (name.isEmpty()) {
					logger.warning("This material has not been given a name: " + line);
					continue;
				}
				// If the data cannot parse, skip
				short data = 0;
				try {
					data = Short.parseShort(values.getElement(1));
				}
				catch (NumberFormatException error) {
					logger.warning("This material's data cannot be parsed: " + line);
					continue;
				}
				// If the data is an inherently illegal value, give warning
				if (data < 0) {
					logger.warning("This material's data is set to an illegal value: " + line);
				}
				// Put the material, data, and name into the system
				itemNames.put(getItemHash(material, data, null, null), name);
				logger.info(String.format("Material parsed: [%s] [%s] [%s]", material, data, name));
				line = reader.readLine();
			}
			reader.close();
		}
		catch (IOException e) {
			logger.warning("Could not load materials.");
			e.printStackTrace();
		}
	}

	private static int getItemHash(Material material, short durability, String displayName, List<String> lore) {
		int hash = durability;
		if (material != null) {
			hash += material.hashCode();
		}
		if (Exists.string(displayName)) {
			hash += displayName.hashCode();
		}
		if (Exists.collection(lore)) {
			hash += lore.hashCode();
		}
		return hash;
	}

	public static String getItemName(ItemStack item) {
		if (item == null) {
			return null;
		}
		return getItemName(item.getType(), item.getDurability(), getDisplayName(item), getLore(item));
	}

	public static String getItemName(Material material, short durability) {
		return getItemName(material, durability, null, null);
	}

	public static String getItemName(Material material, short durability, String displayName, List<String> lore) {
		return itemNames.get(getItemHash(material, durability, displayName, lore));
	}

	public static boolean addCustomItemName(ItemStack item, String name) {
		if (item == null || !Exists.string(name)) {
			return false;
		}
		itemNames.put(getItemHash(item.getType(), item.getDurability(), getDisplayName(item), getLore(item)), name);
		return true;
	}

}
