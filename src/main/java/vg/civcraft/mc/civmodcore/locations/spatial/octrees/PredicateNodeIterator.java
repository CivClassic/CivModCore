package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author psygate
 */
final class PredicateNodeIterator<NodeType extends BaseOcTreeNode<NodeType, ValueType>, ValueType> implements Iterator<NodeType> {
	private final LinkedList<NodeType> queue = new LinkedList<>();
	private final Predicate<IIntBBox2D> predicate;

	public PredicateNodeIterator(NodeType root, Predicate<IIntBBox2D> predicate) {
		queue.push(Objects.requireNonNull(root));
		this.predicate = Objects.requireNonNull(predicate);
	}

	@Override
	public boolean hasNext() {
		if (queue.isEmpty()) {
			return false;
		} else if (predicate.test(queue.peek())) {
			return true;
		} else {
			findNextValue();
			return hasNext();
		}
	}

	private void findNextValue() {
		while (!queue.isEmpty() && !predicate.test(queue.peek())) {
			NodeType value = queue.removeFirst();
			if (value.hasChildren()) {
				queue.addAll(value.getChildren());
			}
		}
	}

	@Override
	public NodeType next() {
		NodeType value = queue.removeFirst();
		if (value.hasChildren()) {
			queue.addAll(value.getChildren());
		}

		return value;
	}
}
