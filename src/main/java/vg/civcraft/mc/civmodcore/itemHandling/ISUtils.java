package vg.civcraft.mc.civmodcore.itemHandling;

import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.api.ItemAPI;

import java.util.Arrays;

/**
 * @deprecated Replaced with ItemAPI
 * */
@Deprecated
public class ISUtils {

	/**
	 * @deprecated Replaced with ItemAPI.addLore();
	 * @see ItemAPI .addLore();
	 * */
	@Deprecated
	public static void addLore(ItemStack item, String... lines) {
		ItemAPI.addLore(item, false, Arrays.asList(lines));
	}

	/**
	 * @deprecated Replaced with ItemAPI.setLore();
	 * @see ItemAPI .setLore();
	 * */
	@Deprecated
	public static void setLore(ItemStack item, String... lines) {
		if (item == null) {
			return;
		}
		if (lines == null || lines.length < 1) {
			ItemAPI.clearLore(item);
		}
		else {
			ItemAPI.setLore(item, Arrays.asList(lines));
		}
	}

	/**
	 * @deprecated Replaced with ItemAPI.setName();
	 * @see ItemAPI .setName();
	 * */
	@Deprecated
	public static void setName(ItemStack item, String name) {
		ItemAPI.setDisplayName(item, name);
	}

	/**
	 * @deprecated Replaced with ItemAPI.getName();
	 * @see ItemAPI .getName();
	 * */
	@Deprecated
	public static String getName(ItemStack item) {
		return ItemAPI.getDisplayName(item);
	}

}
