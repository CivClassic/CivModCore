package vg.civcraft.mc.civmodcore.api;

import com.google.common.base.Strings;
import org.bukkit.enchantments.Enchantment;
import static vg.civcraft.mc.civmodcore.api.EnchantNames.EnchantNameAbbrevTuple;

public final class EnchantAPI {

    private EnchantAPI() {
    }

    /**
     * Determines whether an enchantment is considered safe.
     *
     * @param enchantment The enchantment to validate.
     * @param level The enchantment level to validate.
     * @return Returns true if the enchantment is not null, and the level is within the acceptable bounds.
     *
     * @see Enchantment#getStartLevel() The starting level. A valid level cannot be below this.
     * @see Enchantment#getMaxLevel() The maximum level. A valid level cannot be above this.
     */
    public static boolean isSafeEnchantment(Enchantment enchantment, int level) {
        return enchantment != null && level >= enchantment.getStartLevel() && level <= enchantment.getMaxLevel();
    }

    /**
     * Attempts to retrieve an enchantment by its slug, ID, display name, and abbreviation.
     *
     * @param value The value to search for a matching enchantment by.
     * @return Returns a matched enchantment or null.
     */
    @SuppressWarnings("deprecation")
    public static Enchantment getEnchantment(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        Enchantment enchantment = Enchantment.getByName(value.toUpperCase());
        if (enchantment != null) {
            return enchantment;
        }
        try {
            enchantment = Enchantment.getById(Integer.parseInt(value));
        }
        catch (NumberFormatException ignored) {
        }
        if (enchantment != null) {
            return enchantment;
        }
        EnchantNameAbbrevTuple search = EnchantNames.findByDisplayName(value);
        if (search != null) {
            return search.getEnchantment();
        }
        search = EnchantNames.findByAbbreviation(value);
        if (search != null) {
            return search.getEnchantment();
        }
        return null;
    }

}
