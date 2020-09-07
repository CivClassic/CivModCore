package vg.civcraft.mc.civmodcore.locations.spatial;

/**
 * @author psygate
 */
public interface IIntBBox3D {
	int getMinX();

	int getMinY();

	int getMinZ();

	int getMaxX();

	int getMaxY();

	int getMaxZ();

	default int width() {
		return getMaxX() - getMinX();
	}

	default int height() {
		return getMaxY() - getMinY();
	}

	default int depth() {
		return getMaxZ() - getMinZ();
	}

	default int getHalfPointX() {
		return (getMaxX() + getMinX()) / 2;
	}

	default int getHalfPointY() {
		return (getMaxY() + getMinY()) / 2;
	}

	default int getHalfPointZ() {
		return (getMaxZ() + getMinZ()) / 2;
	}

	default boolean contains(IIntBBox3D other) {
		return IIntBBox3D.contains(this, other);
	}

	default boolean intersects(IIntBBox3D other) {
		return IIntBBox3D.intersects(this, other);
	}

	/**
	 * True if both boxes describe the same volume.
	 *
	 * @param left  Left volume to compare to.
	 * @param right Right volume to compare to.
	 * @return True if the left volume is equal to the right volume.
	 */
	static boolean equals(IIntBBox3D left, IIntBBox3D right) {
		return left == right ||
				(
						left.getMinX() == right.getMinX() &&
								left.getMinY() == right.getMinY() &&
								left.getMinZ() == right.getMinZ() &&
								left.getMaxX() == right.getMaxX() &&
								left.getMaxY() == right.getMaxY() &&
								left.getMaxZ() == right.getMaxZ()
				);
	}

	/**
	 * Strict check whether the outer volume contains the inner. This is true if both are equal, or the outer volume
	 * completely encloses the inner volume.
	 *
	 * @param outer Outer volume.
	 * @param inner Inner volume.
	 * @return True if both are equal, or the outer volume completely encloses the inner volume.
	 */
	static boolean contains(IIntBBox3D outer, IIntBBox3D inner) {
		return outer.getMinX() <= inner.getMinX() &&
				outer.getMinY() <= inner.getMinY() &&
				outer.getMinZ() <= inner.getMinZ() &&
				outer.getMaxX() >= inner.getMaxX() &&
				outer.getMaxY() >= inner.getMaxY() &&
				outer.getMaxZ() >= inner.getMaxZ()
				;
	}

	/**
	 * Check whether two volumes intersect. "Touching" is counted as intersection in this case.
	 *
	 * @param left  Left bounding volume to check.
	 * @param right Right bounding volume to check.
	 * @return True if the volumes intersect.
	 */
	static boolean intersects(IIntBBox3D left, IIntBBox3D right) {
		return left.getMaxX() >= right.getMinX() && right.getMaxX() >= left.getMinX() &&
				left.getMaxY() >= right.getMinY() && right.getMaxY() >= left.getMinY() &&
				left.getMaxZ() >= right.getMinZ() && right.getMaxZ() >= left.getMinZ()
				;

	}

	/**
	 * Inverse of contains, see {@link #contains(IIntBBox3D)}
	 *
	 * @param iIntVolumeBBox
	 * @return
	 */
	default boolean notContains(IIntBBox3D iIntVolumeBBox) {
		return !contains(iIntVolumeBBox);
	}

	/**
	 * True if the bounding volume contains the point represented by the x,y,z coordinates.
	 *
	 * @param box Volume
	 * @param x   Point X Coordinate
	 * @param y   Point Y Coordinate
	 * @param z   Point Z Coordinate
	 * @return True if the box contains the point.
	 */
	static boolean contains(IIntBBox3D box, int x, int y, int z) {
		return box.getMinX() <= x && box.getMaxX() >= x &&
				box.getMinY() <= y && box.getMaxY() >= y &&
				box.getMinZ() <= z && box.getMaxZ() >= z
				;
	}

	/**
	 * True if the bounding volume contains the point represented by the x,y,z coordinates.
	 *
	 * @param box   Volume
	 * @param point Point to check
	 * @return True if the box contains the point.
	 */
	static boolean contains(IIntBBox3D box, IIntPoint3D point) {
		return contains(box, point.getX(), point.getY(), point.getZ());
	}

	/**
	 * True if the bounding box contains the point.
	 *
	 * @param x X coordinate of the point.
	 * @param y Y Coordinate of the point.
	 * @param z Z Coordinate of the point.
	 * @return True if the bounding box contains the point.
	 */
	default boolean contains(int x, int y, int z) {
		return contains(this, x, y, z);
	}

	/**
	 * True if the bounding box contains the point.
	 *
	 * @param point Point to check.
	 * @return True if the bounding box contains the point.
	 */
	default boolean contains(IIntPoint3D point) {
		return contains(this, point.getX(), point.getY(), point.getZ());
	}
}
