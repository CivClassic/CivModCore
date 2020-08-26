package vg.civcraft.mc.civmodcore.api;

import org.bukkit.Location;
import org.bukkit.World;
import vg.civcraft.mc.civmodcore.util.NullCoalescing;

/**
 * Class of utility functions for Locations.
 */
public final class LocationAPI {

	/**
	 * Retrieves the world from a location.
	 *
	 * @param location The location to retrieve the world from.
	 * @return Returns the world if loaded, or null.
	 */
	public static World getLocationWorld(Location location) {
		if (location == null) {
			return null;
		}
		try {
			return location.getWorld();
		}
		catch (IllegalArgumentException ignored) {
			return null;
		}
	}

	/**
	 * Determines whether a location is valid and safe to use.
	 *
	 * @param location The location to check.
	 * @return Returns true if the location exists, is valid, and safe to use.
	 */
	public static boolean isValidLocation(Location location) {
		if (location == null) {
			return false;
		}
		if (!location.isWorldLoaded()) {
			return false;
		}
		return true;
	}

	/**
	 * Converts a location into a block location.
	 *
	 * @param location The location to convert.
	 * @return Returns a block location, or null if the given location was null.
	 */
	public static Location getBlockLocation(Location location) {
		if (location == null) {
			return null;
		}
		return new Location(location.getWorld(),
				location.getBlockX(),
				location.getBlockY(),
				location.getBlockZ());
	}

	/**
	 * Determines whether two locations share the same world.
	 *
	 * @param former The first location.
	 * @param latter The second location.
	 * @return Returns true if the two locations share the same world.
	 */
	public static boolean areLocationsSameWorld(Location former, Location latter) {
		if (former == null || latter == null) {
			return false;
		}
		return NullCoalescing.equals(getLocationWorld(former), getLocationWorld(latter));
	}

}
