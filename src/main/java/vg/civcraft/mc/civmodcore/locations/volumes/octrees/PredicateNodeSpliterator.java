package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

final class PredicateNodeSpliterator<T extends IIntVolumeBBox> implements Spliterator<OcTreeNode<T>> {
	private final Predicate<IIntVolumeBBox> predicate;
	private final LinkedList<OcTreeNode<T>> nodeStack = new LinkedList<>();

	public PredicateNodeSpliterator(OcTreeNode<T> root, Predicate<IIntVolumeBBox> predicate) {
		addNode(root);
		this.predicate = Objects.requireNonNull(predicate);
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
		if (predicate.test(node)) {
			nodeStack.add(Objects.requireNonNull(node));
		}
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
			return new PredicateNodeSpliterator<>(nodeStack.removeFirst(), predicate);
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
