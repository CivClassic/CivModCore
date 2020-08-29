package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Predicate;

/**
 * @author psygate
 */
final class PredicateValueIterator<T extends IIntVolumeBBox> implements Iterator<T> {
	private PredicateNodeIterator<T> nodeIterator;
	private Iterator<T> valueIterator = Collections.emptyIterator();
	private final Predicate<IIntVolumeBBox> valuePredicate;
	private final Predicate<IIntVolumeBBox> nodePredicate;

	public PredicateValueIterator(OcTreeNode<T> root, Predicate<IIntVolumeBBox> valuePredicate, Predicate<IIntVolumeBBox> nodePredicate) {
		this.valuePredicate = Objects.requireNonNull(valuePredicate);
		this.nodePredicate = Objects.requireNonNull(nodePredicate);
		nodeIterator = new PredicateNodeIterator<>(Objects.requireNonNull(root), Objects.requireNonNull(nodePredicate));
		setupValueIterator();
	}

	public PredicateValueIterator(OcTreeNode<T> root, Predicate<IIntVolumeBBox> predicate) {
		this(root, predicate, predicate);
	}

	private void setupValueIterator() {
		while (!valueIterator.hasNext() && nodeIterator.hasNext()) {
			OcTreeNode<T> currentNode = nodeIterator.next();
			valueIterator = currentNode.values().stream().filter(valuePredicate).iterator();
		}
	}

	@Override
	public boolean hasNext() {
		setupValueIterator();

		return valueIterator.hasNext();
	}

	@Override
	public T next() {
		setupValueIterator();

		return valueIterator.next();
	}
}
