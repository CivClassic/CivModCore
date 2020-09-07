package vg.civcraft.mc.civmodcore.locations.spatial;

import java.util.Objects;

/**
 * @author psygate
 */
public class IntPoint3D implements IIntPoint3D {
	private final int x, y, z;

	public IntPoint3D(IIntPoint3D point) {
		this(point.getX(), point.getY(), point.getZ());
	}

	public IntPoint3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getZ() {
		return z;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IntPoint3D intPoint = (IntPoint3D) o;
		return x == intPoint.x &&
				y == intPoint.y &&
				z == intPoint.z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}
}
