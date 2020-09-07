package vg.civcraft.mc.civmodcore.locations.spatial.quadtrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox3D;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author psygate
 */
final class PredicateValueSpliterator<NodeType extends BaseQuadTreeNode<NodeType, ValueType>, ValueType extends IIntBBox3D> implements Iterator<ValueType> {
	private PredicateNodeIterator<NodeType, ValueType> nodeIterator;
	private Iterator<ValueType> valueIterator = Collections.emptyIterator();
	private final Predicate<IIntBBox3D> valuePredicate;
	private final Predicate<IIntBBox3D> nodePredicate;

	public PredicateValueSpliterator(NodeType root, Predicate<IIntBBox3D> valuePredicate, Predicate<IIntBBox3D> nodePredicate) {
		this.valuePredicate = Objects.requireNonNull(valuePredicate);
		this.nodePredicate = Objects.requireNonNull(nodePredicate);
		nodeIterator = new PredicateNodeIterator<>(Objects.requireNonNull(root), Objects.requireNonNull(nodePredicate));
		setupValueIterator();
	}

	public PredicateValueSpliterator(NodeType root, Predicate<IIntBBox3D> predicate) {
		this(root, predicate, predicate);
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
