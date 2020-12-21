package vg.civcraft.mc.civmodcore.world;

import java.util.Objects;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import vg.civcraft.mc.civmodcore.util.NullUtils;

public class WorldXZ {

	private final UUID world;
	private final int x;
	private final int z;

	public WorldXZ(final Location location) {
		if (!WorldUtils.isValidLocation(location)) {
			throw new IllegalArgumentException("Location must be valid!");
		}
		this.world = NullUtils.isNotNull(location.getWorld()).getUID();
		this.x = location.getBlockX();
		this.z = location.getBlockZ();
	}

	public WorldXZ(final World world, final int x, final int z) {
		if (world == null) {
			throw new IllegalArgumentException("World must not be null!");
		}
		this.world = world.getUID();
		this.x = x;
		this.z = z;
	}

	public WorldXZ(final UUID world, final int x, final int z) {
		if (world == null) {
			throw new IllegalArgumentException("World UUID must not be null!");
		}
		this.world = world;
		this.x = x;
		this.z = z;
	}

	public UUID getWorld() {
		return this.world;
	}

	public int getX() {
		return this.x;
	}

	public int getZ() {
		return this.z;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof WorldXZ)) {
			return false;
		}
		final WorldXZ other = (WorldXZ) object;
		return Objects.equals(this.world, other.world)
				&& this.x == other.x
				&& this.z == other.z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.world, this.x, this.z);
	}

	public static WorldXZ fromLocation(final Location location) {
		if (!WorldUtils.isValidLocation(location)) {
			throw new IllegalArgumentException("Location cannot be null!");
		}
		final World world = NullUtils.isNotNull(location.getWorld());
		final int x = location.getBlockX();
		final int z = location.getBlockZ();
		return new WorldXZ(world.getUID(), x, z);
	}

}
