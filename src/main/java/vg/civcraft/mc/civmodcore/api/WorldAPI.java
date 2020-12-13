package vg.civcraft.mc.civmodcore.api;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import vg.civcraft.mc.civmodcore.world.WorldUtils;

@Deprecated
public final class WorldAPI {

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#isWorldLoaded(World)} instead.
	 */
	@Deprecated
	public static boolean isWorldLoaded(World world) {
		return WorldUtils.isWorldLoaded(world);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#isChunkLoaded(World, int, int)} instead.
	 */
	@Deprecated
	public static boolean isChunkLoaded(World world, int x, int z) {
		return WorldUtils.isChunkLoaded(world, x, z);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getLoadedChunk(World, int, int)} instead.
	 */
	@Deprecated
	public static Chunk getLoadedChunk(final World world, final int x, final int z) {
		return WorldUtils.getLoadedChunk(world, x, z);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#isBlockLoaded(Location)} instead.
	 */
	@Deprecated
	public static boolean isBlockLoaded(Location location) {
		return WorldUtils.isBlockLoaded(location);
	}

}
