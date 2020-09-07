package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

final class ValueIterator<NodeType extends BaseOcTreeNode<NodeType, ValueType>, ValueType> implements Iterator<ValueType> {
	private NodeIterator<NodeType, ValueType> nodeIterator;
	private Iterator<ValueType> valueIterator;

	public ValueIterator(NodeType root) {
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
	public ValueType next() {
		prepareNextValue();

		if (valueIterator.hasNext()) {
			return valueIterator.next();
		}

		throw new NoSuchElementException("No next value in iterator.");
	}
}
