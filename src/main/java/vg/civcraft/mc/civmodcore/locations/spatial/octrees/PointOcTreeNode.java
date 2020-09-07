package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntPoint2D;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;

import java.util.Optional;

/**
 * @author psygate
 */
final class PointOcTreeNode<T extends IIntPoint2D> extends BaseOcTreeNode<PointOcTreeNode<T>, T> {
	PointOcTreeNode(IIntBBox2D bbox, int splitSize) {
		super(bbox, splitSize);
	}

	PointOcTreeNode(IIntBBox2D bbox, int splitSize, PointOcTreeNode parent) {
		super(bbox, splitSize, parent);
	}

	PointOcTreeNode(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int splitSize, PointOcTreeNode parent) {
		super(minX, minY, minZ, maxX, maxY, maxZ, splitSize, parent);
	}

	@Override
	protected boolean nodeContainsValue(T value) {
		return contains(value);
	}

	@Override
	protected PointOcTreeNode selectChild(T value) {
		assert childrenSize() > 0;
		for (int i = 0; i < childrenSize(); i++) {
			PointOcTreeNode<T> child = getChild(i);
			if (IIntBBox2D.contains(child, value)) {
				return child;
			}
		}

		return null;
	}

	@Override
	protected Optional<PointOcTreeNode<T>> selectChildOpt(T value) {
		PointOcTreeNode node = selectChild(value);

		if (node != null) {
			return Optional.of(node);
		} else {
			return Optional.empty();
		}
	}

	@Override
	protected PointOcTreeNode createNewNode(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int splitSize, PointOcTreeNode boxOcTreeNode) {
		return new PointOcTreeNode(minX, minY, minZ, maxX, maxY, maxZ, splitSize, this);
	}
}

