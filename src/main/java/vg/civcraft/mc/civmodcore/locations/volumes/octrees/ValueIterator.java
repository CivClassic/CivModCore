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

	private void prepareNextValue() {
		while (!valueIterator.hasNext() && nodeIterator.hasNext()) {
			valueIterator = nodeIterator.next().values().iterator();
		}
	}

	@Override
	public boolean hasNext() {
		prepareNextValue();
		return valueIterator.hasNext();
	}

	@Override
	public void remove() {
		valueIterator.remove();
	}

	@Override
	public T next() {
		prepareNextValue();

		if (valueIterator.hasNext()) {
			return valueIterator.next();
		}

		throw new NoSuchElementException("No next value in iterator.");
	}
}
