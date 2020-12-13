package vg.civcraft.mc.civmodcore.api;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import vg.civcraft.mc.civmodcore.entities.EntityUtils;

/**
 * @deprecated Use {@link EntityUtils} instead.
 */
@Deprecated
public final class EntityAPI {

	/**
	 * @deprecated Use {@link EntityUtils#getEntityType(String)} instead.
	 */
	@Deprecated
	public static EntityType getEntityType(String value) {
		return EntityUtils.getEntityType(value);
	}

	/**
	 * @deprecated Use {@link EntityUtils#isPlayer(Entity)} instead.
	 */
	@Deprecated
	public static boolean isPlayer(Entity entity) {
		return EntityUtils.isPlayer(entity);
	}

}
