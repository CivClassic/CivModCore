package vg.civcraft.mc.civmodcore.api;

import org.bukkit.Location;
import org.bukkit.World;
import vg.civcraft.mc.civmodcore.world.WorldUtils;

/**
 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils} instead.
 */
@Deprecated
public final class LocationAPI {

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getLocationWorld(Location)} instead.
	 */
	@Deprecated
	public static World getLocationWorld(Location location) {
		return WorldUtils.getLocationWorld(location);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#isValidLocation(Location)} instead.
	 */
	@Deprecated
	public static boolean isValidLocation(Location location) {
		return WorldUtils.isValidLocation(location);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getBlockLocation(Location)} instead.
	 */
	@Deprecated
	public static Location getBlockLocation(Location location) {
		return WorldUtils.getBlockLocation(location);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getMidBlockLocation(Location)} instead.
	 */
	@Deprecated
	public static Location getMidBlockLocation(Location location) {
		return WorldUtils.getMidBlockLocation(location);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#doLocationsHaveSameWorld(Location, Location)}
	 *     instead.
	 */
	@Deprecated
	public static boolean areLocationsSameWorld(Location former, Location latter) {
		return WorldUtils.doLocationsHaveSameWorld(former, latter);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#blockDistance(Location, Location, boolean)}
	 *     instead.
	 */
	@Deprecated
	public static int blockDistance(final Location former, final Location latter, final boolean consider2D) {
		return WorldUtils.blockDistance(former, latter, consider2D);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#isWithinBounds(Location)} instead.
	 */
	@Deprecated
	public static boolean isWithinBounds(final Location location) {
		return WorldUtils.isWithinBounds(location);
	}

}
