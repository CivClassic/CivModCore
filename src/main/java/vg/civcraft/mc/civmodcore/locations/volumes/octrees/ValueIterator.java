package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

final class ValueIterator<T extends IIntVolumeBBox> implements Iterator<T> {
	private NodeIterator<T> nodeIterator;
	private Iterator<T> valueIterator;

	public ValueIterator(OcTreeNode<T> root) {
		nodeIterator = new NodeIterator<>(Objects.requireNonNull(root));
		valueIterator = nodeIterator.next().values().iterator();
	}

	@Override
	public boolean hasNext() {
		return valueIterator.hasNext() || nodeIterator.hasNext();
	}

	@Override
	public T next() {
		if (valueIterator.hasNext()) {
			return valueIterator.next();
		} else if (nodeIterator.hasNext()) {
			valueIterator = nodeIterator.next().values().iterator();
			return next();
		}

		throw new NoSuchElementException("No next value in iterator.");
	}
}
