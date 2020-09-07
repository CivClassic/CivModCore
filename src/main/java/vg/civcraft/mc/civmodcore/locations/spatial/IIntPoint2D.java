package vg.civcraft.mc.civmodcore.locations.spatial;

/**
 * @author psygate
 */
public interface IIntPoint3D {
	int getX();

	int getY();

	int getZ();

	/**
	 * Calculate the euclidian distance square of this and the other point.
	 *
	 * @param other Point to calculate the distance to.
	 * @return Euclidian distance square of this and the other point.
	 */
	default double dist(IIntPoint3D other) {
		return dist(this, other);
	}

	/**
	 * Calculate the euclidian distance of this and the other point.
	 *
	 * @param other Point to calculate the distance to.
	 * @return Euclidian distance of this and the other point.
	 */
	default double distSqr(IIntPoint3D other) {
		return distSqr(this, other);
	}

	/**
	 * True if both points are the same.
	 *
	 * @param left  Left point to compare to.
	 * @param right Right point to compare to.
	 * @return True if the left point is equal to the right point.
	 */
	static boolean equals(IIntPoint3D left, IIntPoint3D right) {
		return left == right || (
				left.getX() == right.getX() &&
						left.getY() == right.getY() &&
						left.getZ() == right.getZ()
		);
	}

	/**
	 * Calculate the euclidian distance square of two points.
	 *
	 * @param a Point to calculate the distance from.
	 * @param b Point to calculate the distance to.
	 * @return Euclidian distance square of two points.
	 */
	static int distSqr(IIntPoint3D a, IIntPoint3D b) {
		return (a.getX() - b.getX()) * (a.getX() - b.getX()) +
				(a.getY() - b.getY()) * (a.getY() - b.getY()) +
				(a.getZ() - b.getZ()) * (a.getZ() - b.getZ())
				;
	}

	/**
	 * Calculate the euclidian distance of two points.
	 *
	 * @param a Point to calculate the distance from.
	 * @param b Point to calculate the distance to.
	 * @return Euclidian distance of two points.
	 */
	static double dist(IIntPoint3D a, IIntPoint3D b) {
		return Math.sqrt(distSqr(a, b));
	}
}
