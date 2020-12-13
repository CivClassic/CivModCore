package vg.civcraft.mc.civmodcore.api;

import org.bukkit.Material;
import org.bukkit.TreeType;
import vg.civcraft.mc.civmodcore.inventory.items.TreeTypeUtils;

/**
 * @deprecated Use {@link TreeTypeUtils} instead.
 */
@Deprecated
public final class TreeTypeAPI {

	/**
	 * @deprecated Use {@link TreeTypeUtils#getMatchingTreeType(Material)} instead.
	 */
	@Deprecated
	public static TreeType getMatchingTreeType(Material material) {
		return TreeTypeUtils.getMatchingTreeType(material);
	}

	/**
	 * @deprecated Use {@link TreeTypeUtils#getMatchingSapling(TreeType)} instead.
	 */
	@Deprecated
	public static Material getMatchingSapling(TreeType type) {
		return TreeTypeUtils.getMatchingSapling(type);
	}

}
