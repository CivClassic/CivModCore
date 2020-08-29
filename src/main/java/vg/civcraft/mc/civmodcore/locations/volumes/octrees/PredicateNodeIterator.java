package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author psygate
 */
final class PredicateNodeIterator<T extends IIntVolumeBBox> implements Iterator<OcTreeNode<T>> {
	private final LinkedList<OcTreeNode<T>> stack = new LinkedList<>();
	private final Predicate<IIntVolumeBBox> predicate;

	public PredicateNodeIterator(OcTreeNode<T> root, Predicate<IIntVolumeBBox> predicate) {
		stack.push(Objects.requireNonNull(root));
		this.predicate = Objects.requireNonNull(predicate);
	}

	@Override
	public boolean hasNext() {
		if (stack.isEmpty()) {
			return false;
		} else if (predicate.test(stack.peek())) {
			return true;
		} else {
			findNextValue();
			return hasNext();
		}
	}

	private void findNextValue() {
		while (!stack.isEmpty() && !predicate.test(stack.peek())) {
			OcTreeNode<T> value = stack.removeFirst();
			if (value.hasChildren()) {
				stack.addAll(value.getChildren());
			}
		}
	}

	@Override
	public OcTreeNode<T> next() {
		OcTreeNode<T> value = stack.removeFirst();
		if (value.hasChildren()) {
			stack.addAll(value.getChildren());
		}

		return value;
	}
}
