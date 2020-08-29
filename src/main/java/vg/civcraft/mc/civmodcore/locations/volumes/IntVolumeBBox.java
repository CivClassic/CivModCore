package vg.civcraft.mc.civmodcore.locations.volumes;

import java.util.Objects;

/**
 * @author psygate
 */
public class IntVolumeBBox implements IIntVolumeBBox {
	private final int minX, minY, minZ, maxX, maxY, maxZ;

	public IntVolumeBBox(IIntVolumeBBox bbox) {
		this(bbox.getMinX(), bbox.getMinY(), bbox.getMinZ(), bbox.getMaxX(), bbox.getMaxY(), bbox.getMaxZ());
	}

	public IntVolumeBBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		assert minX < maxX;
		assert minY < maxY;
		assert minZ < maxZ;

		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public final int getMinX() {
		return minX;
	}

	public final int getMinY() {
		return minY;
	}

	public final int getMinZ() {
		return minZ;
	}

	public final int getMaxX() {
		return maxX;
	}

	public final int getMaxY() {
		return maxY;
	}

	public final int getMaxZ() {
		return maxZ;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IntVolumeBBox that = (IntVolumeBBox) o;
		return minX == that.minX &&
				minY == that.minY &&
				minZ == that.minZ &&
				maxX == that.maxX &&
				maxY == that.maxY &&
				maxZ == that.maxZ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public String toString() {
		return "(" + minX + ", " + minY + ", " + minZ + ")-(" + maxX + ", " + maxY + ", " + maxZ + ")";
	}
}
