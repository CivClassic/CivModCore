package vg.civcraft.mc.civmodcore.itemHandling;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.api.EnchantNames;
import vg.civcraft.mc.civmodcore.util.NullCoalescing;

public class NiceNames {

    private static final Map<NameSearchObject, String> ITEMS = new HashMap<>();

    private static class NameSearchObject {

        private String data;

        private NameSearchObject(Material material, short durability, List<String> lore) {
            StringBuilder builder = new StringBuilder();
            builder.append(material.name());
            builder.append("#");
            builder.append(durability);
            NullCoalescing.exists(lore, (list) -> list.forEach(builder::append));
            this.data = builder.toString();
        }

        private NameSearchObject(ItemStack item) {
            this(item.getType(), item.getDurability(), NullCoalescing.chain(() -> item.getItemMeta().getLore()));
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.data);
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof NameSearchObject && Objects.equals(((NameSearchObject) other).data, this.data);
        }

    }

    public static String getName(ItemStack item) {
        return ITEMS.get(new NameSearchObject(item));
    }

    /**
     * Gets the nice name of an enchantment.
     *
     * @param enchant The enchantment to get the nice name of.
     * @return Returns the abbreviation or null.
     *
     * @deprecated Use {@link EnchantNames#findByEnchantment(Enchantment)} instead.
     */
    @Deprecated
    public static String getName(Enchantment enchant) {
        return NullCoalescing.chain(() -> EnchantNames.findByEnchantment(enchant).getDisplayName());
    }

    /**
     * Gets the abbreviation of an enchantment.
     *
     * @param enchant The enchantment to get the abbreviation of.
     * @return Returns the abbreviation or null.
     *
     * @deprecated Use {@link EnchantNames#findByEnchantment(Enchantment)} instead.
     */
    @Deprecated
    public static String getAcronym(Enchantment enchant) {
        return NullCoalescing.chain(() -> EnchantNames.findByEnchantment(enchant).getAbbreviation());
    }

    public void loadNames() {
        // item aliases
        ITEMS.clear();
        try {
            InputStream in = getClass().getResourceAsStream("/materials.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            while (line != null) {
                String[] content = line.split(",");
                NameSearchObject nso = new NameSearchObject(
                        Material.valueOf(content[1]),
                        Short.parseShort(content[2]),
                        null);
                ITEMS.put(nso, content[0]);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void registerItem(ItemStack item, String name) {
        ITEMS.put(new NameSearchObject(item), name);
    }

}
