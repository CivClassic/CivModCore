package vg.civcraft.mc.civmodcore.locations.spatial.quadtrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;

import java.util.Optional;

/**
 * @author psygate
 */
final class PointQuadTreeNode<T extends IIntBBox2D> extends BaseQuadTreeNode<PointQuadTreeNode<T>, T> {
	PointQuadTreeNode(IIntBBox2D bbox, int splitSize) {
		super(bbox, splitSize);
	}

	PointQuadTreeNode(IIntBBox2D bbox, int splitSize, PointQuadTreeNode parent) {
		super(bbox, splitSize, parent);
	}

	PointQuadTreeNode(int minX, int minY, int maxX, int maxY, int splitSize, PointQuadTreeNode parent) {
		super(minX, minY, maxX, maxY, splitSize, parent);
	}

	@Override
	protected boolean nodeContainsValue(T value) {
		return contains(value);
	}

	@Override
	protected PointQuadTreeNode selectChild(T value) {
		assert childrenSize() > 0;
		for (int i = 0; i < childrenSize(); i++) {
			PointQuadTreeNode<T> child = getChild(i);
			if (IIntBBox2D.contains(child, value)) {
				return child;
			}
		}

		return null;
	}

	@Override
	protected Optional<PointQuadTreeNode<T>> selectChildOpt(T value) {
		PointQuadTreeNode node = selectChild(value);
		
		if (node != null) {
			return Optional.of(node);
		} else {
			return Optional.empty();
		}
	}

	@Override
	protected PointQuadTreeNode createNewNode(int minX, int minY, int maxX, int maxY, int splitSize, PointQuadTreeNode boxOcTreeNode) {
		return new PointQuadTreeNode(minX, minY, maxX, maxY, splitSize, this);
	}
}

