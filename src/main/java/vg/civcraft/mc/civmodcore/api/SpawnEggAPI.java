package vg.civcraft.mc.civmodcore.api;

import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import vg.civcraft.mc.civmodcore.inventory.items.SpawnEggUtils;

/**
 * @deprecated Use {@link SpawnEggUtils} instead.
 */
@Deprecated
public final class SpawnEggAPI {

	/**
	 * @deprecated Use {@link SpawnEggUtils#isSpawnEgg(Material)} instead.
	 */
	@Deprecated
	public static boolean isSpawnEgg(Material material) {
		return SpawnEggUtils.isSpawnEgg(material);
	}

	/**
	 * @deprecated Use {@link SpawnEggUtils#getEntityType(Material)} instead.
	 */
	@Deprecated
	@Nullable
	public static EntityType getEntityType(Material material) {
		return SpawnEggUtils.getEntityType(material);
	}

	/**
	 * @deprecated Use {@link SpawnEggUtils#getSpawnEgg(EntityType)} instead.
	 */
	@Deprecated
	@Nullable
	public static Material getSpawnEgg(EntityType entityType) {
		return SpawnEggUtils.getSpawnEgg(entityType);
	}

}
