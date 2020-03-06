package vg.civcraft.mc.civmodcore.api;

import org.bukkit.Material;

/**
 * This is a static class of generally useful APIs regarding materials.
 */
@SuppressWarnings("unused")
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

}
