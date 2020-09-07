package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;

import java.util.Optional;

/**
 * @author psygate
 */
final class VolumeOcTreeNode<T extends IIntBBox2D> extends BaseOcTreeNode<VolumeOcTreeNode<T>, T> {
	VolumeOcTreeNode(IIntBBox2D bbox, int splitSize) {
		super(bbox, splitSize);
	}

	VolumeOcTreeNode(IIntBBox2D bbox, int splitSize, VolumeOcTreeNode parent) {
		super(bbox, splitSize, parent);
	}

	VolumeOcTreeNode(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int splitSize, VolumeOcTreeNode parent) {
		super(minX, minY, minZ, maxX, maxY, maxZ, splitSize, parent);
	}

	@Override
	protected boolean nodeContainsValue(T value) {
		return contains(value);
	}

	@Override
	protected VolumeOcTreeNode selectChild(T value) {
		assert childrenSize() > 0;
		for (int i = 0; i < childrenSize(); i++) {
			VolumeOcTreeNode<T> child = getChild(i);
			if (IIntBBox2D.contains(child, value)) {
				return child;
			}
		}

		return null;
	}

	@Override
	protected Optional<VolumeOcTreeNode<T>> selectChildOpt(T value) {
		VolumeOcTreeNode node = selectChild(value);

		if (node != null) {
			return Optional.of(node);
		} else {
			return Optional.empty();
		}
	}

	@Override
	protected VolumeOcTreeNode createNewNode(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int splitSize, VolumeOcTreeNode boxOcTreeNode) {
		return new VolumeOcTreeNode(minX, minY, minZ, maxX, maxY, maxZ, splitSize, this);
	}
}

