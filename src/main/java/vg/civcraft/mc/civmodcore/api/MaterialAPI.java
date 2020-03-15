package vg.civcraft.mc.civmodcore.api;

import com.google.common.base.Strings;
import org.bukkit.Material;

/**
 * This is a static class of generally useful APIs regarding materials.
 */
public final class MaterialAPI {

    private MaterialAPI() {
    }

    /**
     * Checks whether a material would be considered a valid item.
     *
     * @param material The material to check.
     * @return Returns true if the material would be considered a valid item.
     */
    public static boolean isValidItemMaterial(Material material) {
        if (material == null) {
            return false;
        }
        if (material == Material.AIR) {
            return false;
        }
        if (!material.isItem()) {
            return false;
        }
        return true;
    }

    /**
     * Attempts to retrieve a material by its slug or by its ID.
     *
     * @param value The value to search for a matching material by.
     * @return Returns a matched material or null.
     */
    @SuppressWarnings("deprecation")
    public static Material getMaterial(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        Material material = Material.getMaterial(value.toUpperCase());
        if (material != null) {
            return material;
        }
        try {
            return Material.getMaterial(Integer.parseInt(value));
        }
        catch (NumberFormatException ignored) {
        }
        return null;
    }

    /**
     * Checks whether a material makes use of item durability and or block data.
     *
     * @param material The material to check.
     * @return Returns true if the material does make use of item durability and or block data.
     *
     *         See also:
     *         {@link MaterialAPI#hasDurability(Material) hasDurability(Material)}
     *         {@link MaterialAPI#hasDiscriminator(Material) hasDiscriminator(Material)}
     */
    public static boolean usesDurability(Material material) {
        return hasDurability(material) || hasDiscriminator(material);
    }

    /**
     * Checks whether a material has item durability, that it's an item that can break.
     *
     * @param material The material to check.
     * @return Returns true if the material represents a breakable item.
     *
     * @apiNote This will become deprecated in 1.13 due to the Damageable item meta interface.
     */
    public static boolean hasDurability(Material material) {
        switch (material) {
            case IRON_SPADE:
            case IRON_PICKAXE:
            case IRON_AXE:
            case FLINT_AND_STEEL:
            case BOW:
            case IRON_SWORD:
            case WOOD_SWORD:
            case WOOD_SPADE:
            case WOOD_PICKAXE:
            case WOOD_AXE:
            case STONE_SWORD:
            case STONE_SPADE:
            case STONE_PICKAXE:
            case STONE_AXE:
            case DIAMOND_SWORD:
            case DIAMOND_SPADE:
            case DIAMOND_PICKAXE:
            case DIAMOND_AXE:
            case GOLD_SWORD:
            case GOLD_SPADE:
            case GOLD_PICKAXE:
            case GOLD_AXE:
            case WOOD_HOE:
            case STONE_HOE:
            case IRON_HOE:
            case DIAMOND_HOE:
            case GOLD_HOE:
            case LEATHER_HELMET:
            case LEATHER_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case LEATHER_BOOTS:
            case CHAINMAIL_HELMET:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_LEGGINGS:
            case CHAINMAIL_BOOTS:
            case IRON_HELMET:
            case IRON_CHESTPLATE:
            case IRON_LEGGINGS:
            case IRON_BOOTS:
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
            case GOLD_HELMET:
            case GOLD_CHESTPLATE:
            case GOLD_LEGGINGS:
            case GOLD_BOOTS:
            case FISHING_ROD:
            case SHEARS:
            case CARROT_STICK:
            case SHIELD:
            case ELYTRA:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks whether a material has a discriminator, that its nature can change via its item durability and block data.
     *
     * For example: STONE:0 is Stone, but STONE:1 is Granite.
     *
     * @param material The material to check.
     * @return Returns true if the nature of the material can change through its item durability and block data.
     *
     * @apiNote This will become deprecated in 1.13 due to the flattening of materials.
     */
    public static boolean hasDiscriminator(Material material) {
        switch (material) {
            case STONE:
            case DIRT:
            case WOOD:
            case SAPLING:
            case SAND:
            case LOG:
            case SPONGE:
            case SANDSTONE:
            case LONG_GRASS:
            case WOOL:
            case RED_ROSE:
            case DOUBLE_STEP:
            case STEP:
            case STAINED_GLASS:
            case MONSTER_EGGS:
            case SMOOTH_BRICK:
            case WOOD_DOUBLE_STEP:
            case WOOD_STEP:
            case COBBLE_WALL:
            case QUARTZ_BLOCK:
            case STAINED_CLAY:
            case STAINED_GLASS_PANE:
            case LEAVES_2:
            case LOG_2:
            case PRISMARINE:
            case CARPET:
            case DOUBLE_PLANT:
            case RED_SANDSTONE:
            case CONCRETE:
            case CONCRETE_POWDER:
            case COAL:
            case GOLDEN_APPLE:
            case RAW_FISH:
            case COOKED_FISH:
            case INK_SACK:
            case MONSTER_EGG:
            case SKULL_ITEM:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks if the material is a type of sword.
     *
     * @param material The material to check.
     * @return Returns true if the material represents a type of sword.
     */
    @SuppressWarnings("Duplicates")
    public static boolean isSword(Material material) {
        switch (material) {
            case WOOD_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case GOLD_SWORD:
            case DIAMOND_SWORD:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks if the material is a type of shovel.
     *
     * @param material The material to check.
     * @return Returns true if the material represents a type of shovel.
     */
    @SuppressWarnings("Duplicates")
    public static boolean isShovel(Material material) {
        switch (material) {
            case WOOD_SPADE:
            case STONE_SPADE:
            case IRON_SPADE:
            case GOLD_SPADE:
            case DIAMOND_SPADE:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks if the material is a type of pickaxe.
     *
     * @param material The material to check.
     * @return Returns true if the material represents a type of pickaxe.
     */
    @SuppressWarnings("Duplicates")
    public static boolean isPickaxe(Material material) {
        switch (material) {
            case WOOD_PICKAXE:
            case STONE_PICKAXE:
            case IRON_PICKAXE:
            case GOLD_PICKAXE:
            case DIAMOND_PICKAXE:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks if the material is a type of axe.
     *
     * @param material The material to check.
     * @return Returns true if the material represents a type of axe.
     */
    @SuppressWarnings("Duplicates")
    public static boolean isAxe(Material material) {
        switch (material) {
            case WOOD_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLD_AXE:
            case DIAMOND_AXE:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks if the material is a type of hoe.
     *
     * @param material The material to check.
     * @return Returns true if the material represents a type of hoe.
     */
    @SuppressWarnings("Duplicates")
    public static boolean isHoe(Material material) {
        switch (material) {
            case WOOD_HOE:
            case STONE_HOE:
            case IRON_HOE:
            case GOLD_HOE:
            case DIAMOND_HOE:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks whether a material is air.
     * Will also return true if the given material is null.
     *
     * @param material The material to check.
     * @return Returns true if the material is air.
     * */
    public static boolean isAir(Material material) {
        return material == Material.AIR;
    }

    /**
     * Checks whether a material is a log.
     *
     * @param material The material to check.
     * @return Returns true if the material is a log.
     * */
    public static boolean isLog(Material material) {
        switch (material) {
            case LOG:
            case LOG_2:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks whether a material is a wood plank.
     *
     * @param material The material to check.
     * @return Returns true if the material is a wood plank.
     * */
    public static boolean isPlank(Material material) {
        return material == Material.WOOD;
    }

    /**
     * Checks whether a material can be placed into a pot.
     *
     * @param material The material to check.
     * @return Returns true if the material can be potted.
     * */
    public static boolean isPottable(Material material) {
        switch (material) {
            case SAPLING:
            case RED_ROSE:
            case BROWN_MUSHROOM:
            case CACTUS:
            case YELLOW_FLOWER:
            case DEAD_BUSH:
            case RED_MUSHROOM:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks whether a material is a crop. Something is a crop if it's a plant that can grow, excluding Saplings.
     *
     * @param material The material to check.
     * @return Returns true if the material is a crop.
     * */
    public static boolean isCrop(Material material) {
        switch (material) {
            case BEETROOT_BLOCK:
            case CACTUS:
            case CARROT:
            case CHORUS_FLOWER:
            case CHORUS_PLANT:
            case COCOA:
            case MELON_STEM:
            case NETHER_WART_BLOCK:
            case POTATO:
            case PUMPKIN_STEM:
            case SUGAR_CANE:
            case WHEAT:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks whether a material is a skull/head.
     *
     * @param material The material to check.
     * @return Returns true if the material is a skull/head.
     * */
    public static boolean isSkull(Material material) {
        switch (material) {
            case SKULL:
            case SKULL_ITEM:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks whether a material is a glass block, coloured or otherwise.
     *
     * @param material The material to check.
     * @return Returns true if the material is a glass block.
     * */
    public static boolean isGlassBlock(Material material) {
        switch (material) {
            case GLASS:
            case STAINED_GLASS:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks whether a material is a glass pane, coloured or otherwise.
     *
     * @param material The material to check.
     * @return Returns true if the material is a glass pane.
     * */
    public static boolean isGlassPane(Material material) {
        switch (material) {
            case THIN_GLASS:
            case STAINED_GLASS_PANE:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks whether a material is an infested block. This is what used to be referred to as Monster Egg blocks.
     *
     * @param material The material to check.
     * @return Returns true if the material is infested.
     * */
    public static boolean isInfested(Material material) {
        return material == Material.MONSTER_EGGS;
    }

    /**
     * Checks whether a material is a dirt like block.
     *
     * @param material The material to check.
     * @return Returns true if the material is dirty.
     * */
    public static boolean isDirt(Material material) {
        switch (material) {
            case DIRT:
            case MYCEL:
            case SOIL:
            case GRASS_PATH:
                return true;
            default:
                return false;
        }
    }

}
