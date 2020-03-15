package vg.civcraft.mc.civmodcore.itemHandling;

import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vg.civcraft.mc.civmodcore.api.ItemAPI;
import vg.civcraft.mc.civmodcore.util.NullCoalescing;

@Deprecated
public class ISUtils {

    public static void addLore(ItemStack item, String... lore) {
        if (lore == null || lore.length < 1) {
            return;
        }
        ItemAPI.handleItemMeta((item), (ItemMeta meta) -> {
            if (meta.hasLore()) {
                List<String> current = meta.getLore();
                current.addAll(Arrays.asList(lore));
                return true;
            }
            meta.setLore(Arrays.asList(lore));
            return true;
        });
    }

    public static void setLore(ItemStack item, String... lore) {
        ItemAPI.handleItemMeta((item), (ItemMeta meta) -> {
            if (lore == null || lore.length < 1) {
                meta.setLore(null);
            }
            else {
                meta.setLore(Arrays.asList(lore));
            }
            return true;
        });
    }

    public static void setName(ItemStack item, String name) {
        ItemAPI.handleItemMeta((item), (ItemMeta meta) -> {
            meta.setDisplayName(name);
            return true;
        });
    }

    public static String getName(ItemStack item) {
        return NullCoalescing.chain(() -> item.getItemMeta().getDisplayName());
    }

}
