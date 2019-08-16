package vg.civcraft.mc.civmodcore.itemHandling;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.api.ItemAPI;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class NiceNames {

	private static final Map<Enchantment, String> enchants = new HashMap<>();
	private static final Map<Enchantment, String> enchantAcronyms = new HashMap<>();

	/**
	 * @deprecated Replaced with ItemAPI.getItemName();
	 * @see ItemAPI .getItemName();
	 * */
	@Deprecated
	public static String getName(ItemStack item) {
		return ItemAPI.getItemName(item);
	}

	public static String getName(Enchantment enchant) {
		return enchants.get(enchant);
	}

	public static String getAcronym(Enchantment enchant) {
		return enchantAcronyms.get(enchant);
	}

	public void loadNames() {
		// enchantment aliases
		enchants.clear();
		enchantAcronyms.clear();
		try {
			InputStream in = getClass().getResourceAsStream("/enchantments.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			while (line != null) {
				String[] content = line.split(",");
				Enchantment enchant = Enchantment.getByName(content[1]);
				enchants.put(enchant, content[2]);
				enchantAcronyms.put(enchant, content[0]);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
