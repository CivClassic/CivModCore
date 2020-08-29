package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

final class NodeSpliterator<T extends IIntVolumeBBox> implements Spliterator<OcTreeNode<T>> {
	private final LinkedList<OcTreeNode<T>> nodeStack = new LinkedList<>();

	public NodeSpliterator(OcTreeNode<T> root) {
		addNode(root);
		nodeStack.add(Objects.requireNonNull(root));
	}

	@Override
	public boolean tryAdvance(Consumer<? super OcTreeNode<T>> action) {
		if (!nodeStack.isEmpty()) {
			OcTreeNode<T> node = pushChildrenOntoStack(nodeStack.removeFirst());

			action.accept(node);

			return true;
		} else {
			return false;
		}
	}

	private void addNode(OcTreeNode<T> node) {
		nodeStack.add(Objects.requireNonNull(node));
	}

	private OcTreeNode<T> pushChildrenOntoStack(OcTreeNode<T> node) {
		if (node.hasChildren()) {
			node.getChildren().forEach(this::addNode);
		}

		return node;
	}

	@Override
	public void forEachRemaining(Consumer<? super OcTreeNode<T>> action) {
		while (!nodeStack.isEmpty()) {
			OcTreeNode<T> node = pushChildrenOntoStack(nodeStack.removeFirst());
			action.accept(node);
		}
	}

	@Override
	public Spliterator<OcTreeNode<T>> trySplit() {
		if (!nodeStack.isEmpty()) {
			return new NodeSpliterator<>(nodeStack.removeFirst());
		} else {
			return null;
		}
	}

	@Override
	public long estimateSize() {
		return nodeStack.size();
	}

	@Override
	public long getExactSizeIfKnown() {
		return -1;
	}

	@Override
	public int characteristics() {
		return ORDERED | NONNULL | IMMUTABLE | DISTINCT;
	}

	@Override
	public boolean hasCharacteristics(int characteristics) {
		return (characteristics() & characteristics) != 0;
	}

	@Override
	public Comparator<? super OcTreeNode<T>> getComparator() {
		throw new IllegalStateException();
	}
}
