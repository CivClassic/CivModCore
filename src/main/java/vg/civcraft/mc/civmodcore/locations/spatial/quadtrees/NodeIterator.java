package vg.civcraft.mc.civmodcore.locations.spatial.quadtrees;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

final class NodeIterator<NodeType extends BaseQuadTreeNode<NodeType, ValueType>, ValueType> implements Iterator<NodeType> {
	private final LinkedList<NodeType> queue = new LinkedList<>();

	public NodeIterator(NodeType root) {
		queue.push(Objects.requireNonNull(root));
	}

	@Override
	public final boolean hasNext() {
		return !queue.isEmpty();
	}

	@Override
	public final NodeType next() {
		NodeType next = queue.removeFirst();
		pushChildren(next);
		return next;
	}

	final void addIteratorValue(NodeType value) {
		queue.add(Objects.requireNonNull(value));
	}

	void pushChildren(NodeType next) {
		if (next.hasChildren()) {
			// This could be an easy expression like
			// next.getChildren().forEach(queue::add);
			// But for loops are maximum performance.
			for (int i = 0; i < AreaQuadTreeNode.CHILDREN_SIZE; i++) {
				addIteratorValue(next.getChild(i));
			}
		}
	}
}
