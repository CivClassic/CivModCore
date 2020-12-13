package vg.civcraft.mc.civmodcore.api;

import org.bukkit.Material;
import org.bukkit.Tag;
import vg.civcraft.mc.civmodcore.inventory.items.ItemUtils;
import vg.civcraft.mc.civmodcore.inventory.items.MaterialUtils;
import vg.civcraft.mc.civmodcore.inventory.items.MoreTags;

/**
 * @deprecated Use {@link MaterialUtils}, {@link ItemUtils}, and {@link MoreTags} instead.
 */
@Deprecated
public final class MaterialAPI {

	/**
	 * @deprecated Use {@link ItemUtils#isValidItemMaterial(Material)} instead.
	 */
	@Deprecated
	public static boolean isValidItemMaterial(Material material) {
		return ItemUtils.isValidItemMaterial(material);
	}

	/**
	 * @deprecated Use {@link MaterialUtils#getMaterial(String)} instead.
	 */
	@Deprecated
	public static Material getMaterial(String value) {
		return MaterialUtils.getMaterial(value);
	}

	/**
	 * @deprecated Use {@link MaterialUtils#isAir(Material)} instead.
	 */
	@Deprecated
	public static boolean isAir(Material material) {
		return MaterialUtils.isAir(material);
	}

	/**
	 * @deprecated Please use {@code Tag.LOGS.isTagged(material);}
	 */
	@Deprecated
	public static boolean isLog(Material material) {
		return Tag.LOGS.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code Tag.PLANKS.isTagged(material);}
	 */
	@Deprecated
	public static boolean isPlank(Material material) {
		return Tag.PLANKS.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.STRIPPED_ALL.isTagged(material);}
	 */
	@Deprecated
	public static boolean isStripped(Material material) {
		return MoreTags.STRIPPED_ALL.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.STRIPPED_LOGS.isTagged(material);}
	 */
	@Deprecated
	public static boolean isStrippedLog(Material material) {
		return MoreTags.STRIPPED_LOGS.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.STRIPPED_PLANKS.isTagged(material);}
	 */
	@Deprecated
	public static boolean isStrippedPlank(Material material) {
		return MoreTags.STRIPPED_PLANKS.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.POTTABLE.isTagged(material);}
	 */
	@Deprecated
	public static boolean isPottable(Material material) {
		return MoreTags.POTTABLE.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.CROPS.isTagged(material);}
	 */
	@Deprecated
	public static boolean isCrop(Material material) {
		return MoreTags.CROPS.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.SKULLS.isTagged(material);}
	 */
	@Deprecated
	public static boolean isSkull(Material material) {
		return MoreTags.SKULLS.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.GLASS.isTagged(material);}
	 */
	@Deprecated
	public static boolean isGlassBlock(Material material) {
		return MoreTags.GLASS.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.GLASS_PANES.isTagged(material);}
	 */
	@Deprecated
	public static boolean isGlassPane(Material material) {
		return MoreTags.GLASS_PANES.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code Tag.DRAGON_IMMUNE.isTagged(material);}
	 */
	@Deprecated
	public static boolean isDragonImmune(Material material) {
		return Tag.DRAGON_IMMUNE.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code Tag.WITHER_IMMUNE.isTagged(material);}
	 */
	@Deprecated
	public static boolean isWitherImmune(Material material) {
		return Tag.WITHER_IMMUNE.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code Tag.FENCE_GATES.isTagged(material);}
	 */
	@Deprecated
	public static boolean isWoodenFenceGate(Material material) {
		return Tag.FENCE_GATES.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.INFESTED.isTagged(material);}
	 */
	@Deprecated
	public static boolean isInfested(Material material) {
		return MoreTags.INFESTED.isTagged(material);
	}

	/**
	 * Duplicate of {@link #isWoodenFenceGate(Material)}
	 *
	 * @deprecated Please use {@code Tag.FENCE_GATES.isTagged(material);}
	 */
	@Deprecated
	public static boolean isFenceGate(Material material) {
		return isWoodenFenceGate(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.DIRT.isTagged(material);}
	 */
	@Deprecated
	public static boolean isDirt(Material material) {
		return MoreTags.DIRT.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.POTIONS.isTagged(material);}
	 */
	@Deprecated
	public static boolean isPotion(Material material) {
		return MoreTags.POTIONS.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.SWORDS.isTagged(material);}
	 */
	@Deprecated
	public static boolean isSword(Material material) {
		return MoreTags.SWORDS.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.PICKAXES.isTagged(material);}
	 */
	@Deprecated
	public static boolean isPickaxe(Material material) {
		return MoreTags.PICKAXES.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.AXES.isTagged(material);}
	 */
	@Deprecated
	public static boolean isAxe(Material material) {
		return MoreTags.AXES.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.SPADES.isTagged(material);}
	 */
	@Deprecated
	public static boolean isShovel(Material material) {
		return MoreTags.SPADES.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.HOES.isTagged(material);}
	 */
	@Deprecated
	public static boolean isHoe(Material material) {
		return MoreTags.HOES.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.HELMETS.isTagged(material);}
	 */
	@Deprecated
	public static boolean isHelmet(Material material) {
		return MoreTags.HELMETS.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.CHESTPLATES.isTagged(material);}
	 */
	@Deprecated
	public static boolean isChestplate(Material material) {
		return MoreTags.CHESTPLATES.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.LEGGINGS.isTagged(material);}
	 */
	@Deprecated
	public static boolean areLeggings(Material material) {
		return MoreTags.LEGGINGS.isTagged(material);
	}

	/**
	 * @deprecated Please use {@code MoreTags.BOOTS.isTagged(material);}
	 */
	@Deprecated
	public static boolean areBoots(Material material) {
		return MoreTags.BOOTS.isTagged(material);
	}

	/**
	 * @deprecated Use {@link MaterialUtils#getMaterialHash(Object)} instead.
	 */
	@Deprecated
	public static Material getMaterialHash(Object object) {
		return MaterialUtils.getMaterialHash(object);
	}

}
