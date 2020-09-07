package vg.civcraft.mc.civmodcore.locations.spatial;

import java.util.Objects;

/**
 * @author psygate
 */
public class IntPoint2D implements IIntPoint2D {
	private final int x, y;

	public IntPoint2D(IntPoint2D point) {
		this(point.getX(), point.getY());
	}

	public IntPoint2D(int x, int y) {
		this.x = x;
		this.y = y;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IntPoint2D intPoint = (IntPoint2D) o;
		return x == intPoint.x &&
				y == intPoint.y
				;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
