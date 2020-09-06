package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeType;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntVolumeBBox;

import java.util.Optional;

/**
 * @author psygate
 */
final class VolumeOcTreeNode<T extends IIntVolumeBBox> extends BaseOcTreeNode<VolumeOcTreeNode<T>, T> {
	VolumeOcTreeNode(IIntVolumeBBox bbox, int splitSize) {
		super(bbox, splitSize);
	}

	VolumeOcTreeNode(IIntVolumeBBox bbox, int splitSize, VolumeOcTreeNode parent) {
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
			if (IIntVolumeBBox.contains(child, value)) {
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

