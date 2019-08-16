package vg.civcraft.mc.civmodcore.chatDialog;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public final class DialogManager implements Listener {
	private DialogManager() {} // Make the class effectively static

	public static final DialogManager instance = new DialogManager();
	private static final Map<UUID, Dialog> dialogs = new TreeMap<>();

	public static Dialog get(Player player) {
		if (player == null) {
			return null;
		}
		return get(player.getUniqueId());
	}

	public static Dialog get(UUID uuid) {
		if (uuid == null) {
			return null;
		}
		return dialogs.get(uuid);
	}

	public static void register(Player player, Dialog dialog) {
		if (player == null || dialog == null) {
			return;
		}
		register(player.getUniqueId(), dialog);
	}

	public static void register(UUID uuid, Dialog dialog) {
		if (uuid == null || dialog == null) {
			return;
		}
		forceEnd(uuid);
		dialogs.put(uuid, dialog);
	}

	public static void forceEnd(Player player) {
		if (player == null) {
			return;
		}
		forceEnd(player.getUniqueId());
	}

	public static void forceEnd(UUID uuid) {
		if (uuid == null) {
			return;
		}
		Dialog current = dialogs.get(uuid);
		if (current != null) {
			current.end();
			dialogs.remove(uuid);
		}
	}

	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event) {
		forceEnd(event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onTabComplete(PlayerChatTabCompleteEvent event) {
		Dialog dialog = dialogs.get(event.getPlayer().getUniqueId());
		if (dialog != null) {
			Collection<String> completes = event.getTabCompletions();
			completes.clear();
			completes.addAll(dialog.onTabComplete(event.getLastToken(), event.getChatMessage()));
		}
	}

	// Deprecated functions that are accessed through DialogManager.instance

	@Deprecated
	public Dialog getDialog(Player player) {
		return get(player);
	}

	@Deprecated
	public Dialog getDialog(UUID uuid) {
		return get(uuid);
	}

	@Deprecated
	public void registerDialog(Player player, Dialog dialog) {
		register(player, dialog);
	}

	@Deprecated
	public void forceEndDialog(Player player) {
		forceEnd(player);
	}

	@Deprecated
	public void forceEndDialog(UUID uuid) {
		forceEnd(uuid);
	}

}
