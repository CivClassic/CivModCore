package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

final class NodeIterator<T extends IIntVolumeBBox> implements Iterator<OcTreeNode<T>> {
	private final LinkedList<OcTreeNode<T>> queue = new LinkedList<>();

	public NodeIterator(OcTreeNode<T> root) {
		queue.push(Objects.requireNonNull(root));
	}

	@Override
	public final boolean hasNext() {
		return !queue.isEmpty();
	}

	@Override
	public final OcTreeNode<T> next() {
		OcTreeNode<T> next = queue.removeFirst();
		pushChildren(next);
		return next;
	}

	final void addIteratorValue(OcTreeNode<T> value) {
		queue.add(Objects.requireNonNull(value));
	}

	void pushChildren(OcTreeNode<T> next) {
		if (next.hasChildren()) {
			// This could be an easy expression like
			// next.getChildren().forEach(queue::add);
			// But for loops are maximum performance.
			for (int i = 0; i < OcTreeNode.CHILDREN_SIZE; i++) {
				addIteratorValue(next.getChild(i));
			}
		}
	}
}
