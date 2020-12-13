package vg.civcraft.mc.civmodcore;

import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.HumanEntity;
import vg.civcraft.mc.civmodcore.api.PotionNames;
import vg.civcraft.mc.civmodcore.chat.dialog.DialogManager;
import vg.civcraft.mc.civmodcore.chatDialog.ChatListener;
import vg.civcraft.mc.civmodcore.command.AikarCommandManager;
import vg.civcraft.mc.civmodcore.custom.items.CustomItems;
import vg.civcraft.mc.civmodcore.dao.ManagedDatasource;
import vg.civcraft.mc.civmodcore.events.CustomEventMapper;
import vg.civcraft.mc.civmodcore.inventory.items.EnchantUtils;
import vg.civcraft.mc.civmodcore.inventory.items.ItemUtils;
import vg.civcraft.mc.civmodcore.inventorygui.ClickableInventoryListener;
import vg.civcraft.mc.civmodcore.inventorygui.paged.PagedGUIManager;
import vg.civcraft.mc.civmodcore.locations.chunkmeta.GlobalChunkMetaManager;
import vg.civcraft.mc.civmodcore.locations.chunkmeta.api.ChunkMetaAPI;
import vg.civcraft.mc.civmodcore.locations.global.CMCWorldDAO;
import vg.civcraft.mc.civmodcore.locations.global.WorldIDManager;
import vg.civcraft.mc.civmodcore.playersettings.PlayerSettingAPI;
import vg.civcraft.mc.civmodcore.playersettings.gui.ConfigCommand;
import vg.civcraft.mc.civmodcore.playersettings.gui.ConfigGetAnyCommand;
import vg.civcraft.mc.civmodcore.playersettings.gui.ConfigSetAnyCommand;
import vg.civcraft.mc.civmodcore.scoreboard.bottom.BottomLineAPI;
import vg.civcraft.mc.civmodcore.scoreboard.side.ScoreBoardListener;
import vg.civcraft.mc.civmodcore.serialization.NBTSerialization;
import vg.civcraft.mc.civmodcore.world.WorldTracker;
import vg.civcraft.mc.civmodcore.world.operations.ChunkOperationManager;

public final class CivModCorePlugin extends ACivMod {

	private static CivModCorePlugin instance;

	private GlobalChunkMetaManager chunkMetaManager;

	private ManagedDatasource database;

	private WorldIDManager worldIdManager;

	private AikarCommandManager manager;

	@Override
	public void onEnable() {
		instance = this;
		this.useNewCommandHandler = true;
		ConfigurationSerialization.registerClass(ManagedDatasource.class);
		// Save default resources
		saveDefaultResource("enchants.yml");
		saveDefaultResource("materials.yml");
		saveDefaultResource("potions.csv");
		saveDefaultConfig();
		super.onEnable();
		// Load Database
		try {
			this.database = (ManagedDatasource) getConfig().get("database");
			if (database != null) {
				CMCWorldDAO dao = new CMCWorldDAO(this.database, this);
				if (dao.updateDatabase()) {
					this.worldIdManager = new WorldIDManager(dao);
					this.chunkMetaManager = new GlobalChunkMetaManager(dao, this.worldIdManager);
					info("Setup database successfully");
				}
				else {
					warning("Could not setup database");
				}
			}
		}
		catch (Exception error) {
			warning("Cannot get database from config.", error);
			this.database = null;
		}
		// Register listeners
		registerListener(new ClickableInventoryListener());
		registerListener(new PagedGUIManager());
		registerListener(DialogManager.INSTANCE);
		registerListener(new ChatListener()); // Remove later
		registerListener(new ScoreBoardListener());
		registerListener(new CustomEventMapper());
		registerListener(new WorldTracker());
		registerListener(ChunkOperationManager.INSTANCE);
		// Register commands
		this.manager = new AikarCommandManager(this) {
			@Override
			public void registerCommands() {
				registerCommand(new ConfigCommand());
				registerCommand(ChunkOperationManager.INSTANCE);
			}
		};
		// Load APIs
		ItemUtils.loadItemNames(this);
		EnchantUtils.loadEnchantAbbreviations(this);
		BottomLineAPI.init();
		newCommandHandler.registerCommand(new ConfigSetAnyCommand());
		newCommandHandler.registerCommand(new ConfigGetAnyCommand());
		// Deprecated
		PotionNames.loadPotionNames();
	}

	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
		CustomItems.clearRegistrations();
		ChunkMetaAPI.saveAll();
		this.chunkMetaManager = null;
		// Disconnect database
		if (this.database != null) {
			try {
				this.database.close();
			}
			catch (SQLException error) {
				warning("Was unable to close the database.", error);
			}
			this.database = null;
		}
		WorldTracker.reset();
		PlayerSettingAPI.saveAll();
		DialogManager.resetDialogs();
		ConfigurationSerialization.unregisterClass(ManagedDatasource.class);
		NBTSerialization.clearAllRegistrations();
		if (this.manager != null) {
			this.manager.reset();
			this.manager = null;
		}
		super.onDisable();
		instance = null;
	}

	public static CivModCorePlugin getInstance() {
		return instance;
	}
	
	public GlobalChunkMetaManager getChunkMetaManager() {
		return this.chunkMetaManager;
	}
	
	public WorldIDManager getWorldIdManager() {
		return this.worldIdManager;
	}
	
	public ManagedDatasource getDatabase() {
		return database;
	}

}
