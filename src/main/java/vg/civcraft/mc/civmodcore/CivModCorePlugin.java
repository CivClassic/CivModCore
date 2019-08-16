package vg.civcraft.mc.civmodcore;

import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import vg.civcraft.mc.civmodcore.api.EnchantingAPI;
import vg.civcraft.mc.civmodcore.api.ItemAPI;
import vg.civcraft.mc.civmodcore.dialog.DialogManager;
import vg.civcraft.mc.civmodcore.database.ManagedDatasource;
import vg.civcraft.mc.civmodcore.inventorygui.ClickableInventoryListener;

/**
 * The sole purpose of this class is to make Spigot recognize this library as a plugin and automatically load the
 * classes onto the classpath for us.
 * <p>
 * Replaces Dummy class.
 */
public final class CivModCorePlugin extends ACivMod {

	@Getter
	private static CivModCorePlugin instance;

	@Override
	public synchronized void onEnable() {
		super.onEnable();
		instance = this;
		// Save default resource
		saveResource("enchantments.csv", false);
		saveResource("materials.csv", false);
		saveResource("potions.csv", false);
		// Register listeners
		registerListener(new ClickableInventoryListener());
		registerListener(DialogManager.instance);
		// Load APIs
		ItemAPI.loadItemNames();
		EnchantingAPI.loadEnchantmentNames();
		ConfigurationSerialization.registerClass(ManagedDatasource.class);
	}

	@Override
	public synchronized void onDisable() {
		super.onDisable();
		instance = null;
		HandlerList.unregisterAll(this);
		ItemAPI.resetItemNames();
		EnchantingAPI.resetEnchantmentNames();
		ConfigurationSerialization.unregisterClass(ManagedDatasource.class);
	}

}
