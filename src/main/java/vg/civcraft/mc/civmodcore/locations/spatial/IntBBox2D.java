package vg.civcraft.mc.civmodcore.locations.spatial;

import java.util.Objects;

/**
 * @author psygate
 */
public class IntBBox2D implements IIntBBox2D {
	private final int minX, minY, maxX, maxY;

	public IntBBox2D(IIntBBox2D bbox) {
		this(bbox.getMinX(), bbox.getMinY(), bbox.getMaxX(), bbox.getMaxY());
	}

	public IntBBox2D(int minX, int minY, int maxX, int maxY) {
		assert minX < maxX;
		assert minY < maxY;

		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public final int getMinX() {
		return minX;
	}

	public final int getMinY() {
		return minY;
	}

	public final int getMaxX() {
		return maxX;
	}

	public final int getMaxY() {
		return maxY;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IntBBox2D that = (IntBBox2D) o;
		return minX == that.minX &&
				minY == that.minY &&
				maxX == that.maxX &&
				maxY == that.maxY
				;
	}

	@Override
	public int hashCode() {
		return Objects.hash(minX, minY, maxX, maxY);
	}

	@Override
	public String toString() {
		return "(" + minX + ", " + minY + ")-(" + maxX + ", " + maxY + ")";
	}
}
