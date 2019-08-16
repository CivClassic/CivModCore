package vg.civcraft.mc.civmodcore;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import vg.civcraft.mc.civmodcore.api.ItemAPI;
import vg.civcraft.mc.civmodcore.chatDialog.ChatListener;
import vg.civcraft.mc.civmodcore.chatDialog.DialogManager;
import vg.civcraft.mc.civmodcore.dao.ManagedDatasource;
import vg.civcraft.mc.civmodcore.inventorygui.ClickableInventoryListener;
import vg.civcraft.mc.civmodcore.itemHandling.NiceNames;

/**
 * The sole purpose of this class is to make Spigot recognize this library as a plugin and automatically load the
 * classes onto the classpath for us.
 * <p>
 * Replaces Dummy class.
 */
public final class CivModCorePlugin extends ACivMod {

	private static CivModCorePlugin instance;

	@Override
	public synchronized void onEnable() {
		super.onEnable();
		instance = this;
		// Save default resource
		saveResource("/enchantments.csv", false);
		saveResource("/materials.csv", false);
		saveResource("/potions.csv", false);
		// Register listeners
		registerListener(new ClickableInventoryListener());
		registerListener(new ChatListener());
		// Load APIs
		ItemAPI.loadItemNames();
		new NiceNames().loadNames();
		new DialogManager();
		ConfigurationSerialization.registerClass(ManagedDatasource.class);
	}

	@Override
	public synchronized void onDisable() {
		super.onDisable();
		instance = null;
		HandlerList.unregisterAll(this);
		ItemAPI.resetItemNames();
	}

	public static CivModCorePlugin getInstance() {
		return instance;
	}

}
