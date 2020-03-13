package vg.civcraft.mc.civmodcore;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import vg.civcraft.mc.civmodcore.api.EnchantNames;
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
public class CivModCorePlugin extends ACivMod {

    private static CivModCorePlugin instance;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        // Save default resources
        saveDefaultResource("enchantments.csv");
        saveDefaultResource("materials.csv");
        // Register event listeners
        registerEvent(new ClickableInventoryListener());
        registerEvent(new ChatListener());
        // Load APIs
        EnchantNames.loadEnchantmentNames();
        new NiceNames().loadNames();
        new DialogManager();
        // Register serializations
        ConfigurationSerialization.registerClass(ManagedDatasource.class);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        instance = null;
        // Unload APIs
        EnchantNames.resetEnchantmentNames();
        // Deregister serializations
        ConfigurationSerialization.unregisterClass(ManagedDatasource.class);
    }

    public static CivModCorePlugin getInstance() {
        return instance;
    }

}
