package vg.civcraft.mc.civmodcore.locations.volumes;

/**
 * @author psygate
 */
public interface IIntVolumeBBox {
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

	default boolean contains(IIntVolumeBBox other) {
		return IIntVolumeBBox.contains(this, other);
	}

	default boolean intersects(IIntVolumeBBox other) {
		return IIntVolumeBBox.intersects(this, other);
	}

	/**
	 * True if both boxes describe the same volume.
	 *
	 * @param left  Left volume to compare to.
	 * @param right Right volume to compare to.
	 * @return True if the left volume is equal to the right volume.
	 */
	static boolean equals(IIntVolumeBBox left, IIntVolumeBBox right) {
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
	static boolean contains(IIntVolumeBBox outer, IIntVolumeBBox inner) {
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
	static boolean intersects(IIntVolumeBBox left, IIntVolumeBBox right) {
		return left.getMaxX() >= right.getMinX() && right.getMaxX() >= left.getMinX() &&
				left.getMaxY() >= right.getMinY() && right.getMaxY() >= left.getMinY() &&
				left.getMaxZ() >= right.getMinZ() && right.getMaxZ() >= left.getMinZ()
				;

	}

	/**
	 * Inverse of contains, see {@link #contains(IIntVolumeBBox)}
	 *
	 * @param iIntVolumeBBox
	 * @return
	 */
	default boolean notContains(IIntVolumeBBox iIntVolumeBBox) {
		return !contains(iIntVolumeBBox);
	}

	/**
	 * True if the bounding box contains the point.
	 * @param x X coordinate of the point.
	 * @param y Y Coordinate of the point.
	 * @param z Z Coordinate of the point.
	 * @return True if the bounding box contains the point.
	 */
	default boolean contains(int x, int y, int z) {
		return getMinX() <= x && getMaxX() >= x &&
				getMinY() <= y && getMaxY() >= y &&
				getMinZ() <= z && getMaxZ() >= z
				;
	}
}
