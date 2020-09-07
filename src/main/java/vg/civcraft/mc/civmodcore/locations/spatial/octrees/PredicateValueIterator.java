package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author psygate
 */
final class PredicateValueIterator<NodeType extends BaseOcTreeNode<NodeType, ValueType>, ValueType> implements Iterator<ValueType> {
	private PredicateNodeIterator<NodeType, ValueType> nodeIterator;
	private Iterator<ValueType> valueIterator = Collections.emptyIterator();
	private final Predicate<ValueType> valuePredicate;
	private final Predicate<IIntBBox2D> nodePredicate;

	public PredicateValueIterator(NodeType root, Predicate<ValueType> valuePredicate, Predicate<IIntBBox2D> nodePredicate) {
		this.valuePredicate = Objects.requireNonNull(valuePredicate);
		this.nodePredicate = Objects.requireNonNull(nodePredicate);
		nodeIterator = new PredicateNodeIterator<>(Objects.requireNonNull(root), Objects.requireNonNull(nodePredicate));
		setupValueIterator();
	}

	private void setupValueIterator() {
		while (!valueIterator.hasNext() && nodeIterator.hasNext()) {
			NodeType currentNode = nodeIterator.next();
			valueIterator = currentNode.values().stream().filter(valuePredicate).iterator();
		}
	}

	@Override
	public boolean hasNext() {
		setupValueIterator();

		return valueIterator.hasNext();
	}

	@Override
	public ValueType next() {
		setupValueIterator();

		return valueIterator.next();
	}
}
