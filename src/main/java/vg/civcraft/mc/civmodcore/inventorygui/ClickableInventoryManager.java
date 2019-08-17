package vg.civcraft.mc.civmodcore.inventorygui;

import io.protonull.utilities.libs.javax.validation.constraints.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ClickableInventoryManager implements Listener {
    private ClickableInventoryManager() {}

    public static final ClickableInventoryManager instance = new ClickableInventoryManager();
    private static final Map<UUID, ClickableInventory> clickables = new HashMap<>();

    public static ClickableInventory getOpenClickable(Player player) {
		if (player == null) {
			return null;
		}
        return getOpenClickable(player.getUniqueId());
    }

	public static ClickableInventory getOpenClickable(UUID uuid) {
		if (uuid == null) {
			return null;
		}
		return clickables.get(uuid);
	}

    public static boolean showInventory(Player player, ClickableInventory inventory) {
        if (player == null || inventory == null) {
            return false;
        }
        closeInventory(player);
        player.openInventory(inventory.getInventory());
        clickables.put(player.getUniqueId(), inventory);
        return true;
    }

    public static boolean updateInventory(@NotNull ClickableInventory inventory) {
        for (Map.Entry<UUID, ClickableInventory> entry : clickables.entrySet()) {
            if (entry.getValue() != inventory) {
                continue;
            }
            Player player = Bukkit.getPlayer(entry.getKey());
            if (player == null) {
                continue;
            }
            player.updateInventory();
            showInventory(player, inventory);
        }
        return true;
    }

    public static void inventoryClosed(Player player) {
        if (player == null) {
            return;
        }
        ClickableInventory inventory = clickables.get(player.getUniqueId());
        if (inventory == null) {
        	return;
		}
		clickables.remove(player.getUniqueId());
		if (!clickables.containsValue(inventory)) {
			inventory.prepareForClose();
		}
    }

    public static void closeInventory(Player player) {
        if (player == null) {
            return;
        }
		inventoryClosed(player);
        player.closeInventory();
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        ClickableInventory inventory = getOpenClickable(player);
        if (inventory == null) {
        	return;
		}
		event.setCancelled(true);
        inventory.clickButtonSlot(player, event.getSlot());
    }

    @EventHandler
    public void inventoryClose(InventoryCloseEvent event) {
        // for some reason getPlayer apparently isn't always a player here, but just a LivingEntity
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }
		inventoryClosed((Player) event.getPlayer());
    }

    @EventHandler
    public void playerLogoff(PlayerQuitEvent event) {
		inventoryClosed(event.getPlayer());
    }

}
