package vg.civcraft.mc.civmodcore.api;

import com.google.common.base.Strings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.enchantments.Enchantment;
import vg.civcraft.mc.civmodcore.CivModCorePlugin;
import vg.civcraft.mc.civmodcore.util.TextUtil;

/**
 * Class that loads and store enchantments names. Replaces {@link vg.civcraft.mc.civmodcore.itemHandling.NiceNames}.
 */
public final class EnchantNames {

    private static final Logger logger = Logger.getLogger(EnchantNames.class.getSimpleName());

    private static final Set<EnchantNameAbbrevTuple> enchantmentDetails = new HashSet<>();

    private EnchantNames() {
    }

    /**
     * Resets all enchantments names and initials.
     */
    public static void resetEnchantmentNames() {
        enchantmentDetails.clear();
    }

    /**
     * Loads enchantment names and initials from the config.
     */
    public static void loadEnchantmentNames() {
        resetEnchantmentNames();
        // Load enchantment names from enchantments.csv
        InputStream enchantmentsCSV = CivModCorePlugin.class.getResourceAsStream("/enchantments.csv");
        if (enchantmentsCSV != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(enchantmentsCSV));
                String line = reader.readLine();
                lineReader:
                while (line != null) {
                    String[] values = line.split(",");
                    // If there's not at least three values (slug, abbreviation, display name) then skip
                    if (values.length != 3) {
                        logger.warning("[Config] This enchantment row is corrupted: " + line);
                        // Go to the next line
                        line = reader.readLine();
                        continue;
                    }
                    // If the Enchantment cannot be found by the slug given, then skip
                    Enchantment enchantment = Enchantment.getByName(values[0].toUpperCase());
                    if (enchantment == null) {
                        logger.warning("[Config] Could not find an enchantment on this line: " + line);
                        // Go to the next line
                        line = reader.readLine();
                        continue;
                    }
                    // If the abbreviation or display name doesn't exist, then skip
                    String abbreviation = values[1];
                    String displayName = values[2];
                    if (abbreviation.isEmpty() || displayName.isEmpty()) {
                        logger.warning("[Config] Could not find an abbreviation on this line: " + line);
                        // Go to the next line
                        line = reader.readLine();
                        continue;
                    }
                    // Check if any of these details have already been registered
                    for (EnchantNameAbbrevTuple details : enchantmentDetails) {
                        if (details.enchantment == enchantment) {
                            logger.warning("[Config] This enchantment already has a name registered: " + line);
                            // Go to the next line
                            line = reader.readLine();
                            continue lineReader;
                        }
                        if (TextUtil.stringEqualsIgnoreCase(details.abbreviation, abbreviation)) {
                            logger.warning("[Config] This abbreviation already has a name registered: " + line);
                            // Go to the next line
                            line = reader.readLine();
                            continue lineReader;
                        }
                        if (TextUtil.stringEqualsIgnoreCase(details.displayName, displayName)) {
                            logger.warning("[Config] This display name already has a name registered: " + line);
                            // Go to the next line
                            line = reader.readLine();
                            continue lineReader;
                        }
                    }
                    // Put the enchantment and name into the system
                    enchantmentDetails.add(new EnchantNameAbbrevTuple(enchantment, abbreviation, displayName));
                    logger.info(String.format("[Config] Enchantment parsed: %s = %s = %s", enchantment.getName(),
                            abbreviation, displayName));
                    line = reader.readLine();
                }
                reader.close();
            }
            catch (IOException error) {
                logger.warning("[Config] Could not load enchantments from enchantments.csv");
                error.printStackTrace();
            }
        }
        else {
            logger.warning("[Config] Could not load enchantments from enchantments.csv as the file does not exist.");
        }
    }

    /**
     * Attempts to match an enchantment with a set of details.
     *
     * @param enchantment The enchantment to search with.
     * @return The enchantment details, or null.
     *
     * @apiNote If a valid enchantment was entered but null was returned, the server needs to update its enchantment
     *         list, which is located in [server]/plugins/CivModCore/enchantments.csv
     */
    public static EnchantNameAbbrevTuple findByEnchantment(Enchantment enchantment) {
        if (enchantment == null) {
            return null;
        }
        for (EnchantNameAbbrevTuple details : enchantmentDetails) {
            if (details.enchantment == enchantment) {
                return details;
            }
        }
        return null;
    }

    /**
     * Attempts to match an abbreviation with a set of enchantment details.
     * For example, by default "E" will match with {@link Enchantment#DIG_SPEED} because "Efficiency"
     *
     * @param abbreviation The abbreviation to search with.
     * @return The enchantment details, or null.
     *
     * @apiNote The abbreviation search is not case sensitive.
     */
    public static EnchantNameAbbrevTuple findByAbbreviation(String abbreviation) {
        if (Strings.isNullOrEmpty(abbreviation)) {
            return null;
        }
        for (EnchantNameAbbrevTuple details : enchantmentDetails) {
            if (TextUtil.stringEqualsIgnoreCase(details.abbreviation, abbreviation)) {
                return details;
            }
        }
        return null;
    }

    /**
     * Attempts to match a display name with a set of enchantment details.
     * For example, by default "Efficiency" will match with {@link Enchantment#DIG_SPEED}
     *
     * @param displayName The display name to search with.
     * @return The enchantment details, or null.
     *
     * @apiNote The display name search is not case sensitive.
     */
    public static EnchantNameAbbrevTuple findByDisplayName(String displayName) {
        if (Strings.isNullOrEmpty(displayName)) {
            return null;
        }
        for (EnchantNameAbbrevTuple details : enchantmentDetails) {
            if (TextUtil.stringEqualsIgnoreCase(details.displayName, displayName)) {
                return details;
            }
        }
        return null;
    }

    /**
     * This class represents a data set for a particular enchantment.
     */
    public static final class EnchantNameAbbrevTuple {

        private final Enchantment enchantment;

        private final String abbreviation;

        private final String displayName;

        private EnchantNameAbbrevTuple(Enchantment enchantment, String abbreviation, String displayName) {
            this.enchantment = enchantment;
            this.abbreviation = abbreviation;
            this.displayName = displayName;
        }

        /**
         * @return Returns the enchantment itself.
         */
        public Enchantment getEnchantment() {
            return this.enchantment;
        }

        /**
         * @return Returns the enchantment's official abbreviation.
         */
        public String getAbbreviation() {
            return this.abbreviation;
        }

        /**
         * @return Returns the enchantment's official display name.
         */
        public String getDisplayName() {
            return this.displayName;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.enchantment, this.displayName, this.abbreviation);
        }

    }

}
