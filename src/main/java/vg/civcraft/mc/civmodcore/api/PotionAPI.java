package vg.civcraft.mc.civmodcore.api;

import org.bukkit.potion.PotionEffectType;
import vg.civcraft.mc.civmodcore.inventory.items.PotionUtils;

/**
 * @deprecated Use {@link PotionUtils} instead.
 */
@Deprecated
public final class PotionAPI {

	/**
	 * @deprecated Use {@link PotionUtils#getEffectNiceName(PotionEffectType)} instead.
	 */
	@Deprecated
	public static String getNiceName(PotionEffectType effect) {
		return PotionUtils.getEffectNiceName(effect);
	}
	
}
