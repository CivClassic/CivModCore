package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

final class PredicateNodeSpliterator<T extends IIntBBox2D> implements Spliterator<VolumeOcTreeNode<T>> {
	private final Predicate<IIntBBox2D> predicate;
	private final LinkedList<VolumeOcTreeNode<T>> nodeStack = new LinkedList<>();

	public PredicateNodeSpliterator(VolumeOcTreeNode<T> root, Predicate<IIntBBox2D> predicate) {
		addNode(root);
		this.predicate = Objects.requireNonNull(predicate);
	}

	@Override
	public boolean tryAdvance(Consumer<? super VolumeOcTreeNode<T>> action) {
		if (!nodeStack.isEmpty()) {
			VolumeOcTreeNode<T> node = pushChildrenOntoStack(nodeStack.removeFirst());

			action.accept(node);

			return true;
		} else {
			return false;
		}
	}

	private void addNode(VolumeOcTreeNode<T> node) {
		if (predicate.test(node)) {
			nodeStack.add(Objects.requireNonNull(node));
		}
	}

	private VolumeOcTreeNode<T> pushChildrenOntoStack(VolumeOcTreeNode<T> node) {
		if (node.hasChildren()) {
			node.getChildren().forEach(this::addNode);
		}

		return node;
	}

	@Override
	public void forEachRemaining(Consumer<? super VolumeOcTreeNode<T>> action) {
		while (!nodeStack.isEmpty()) {
			VolumeOcTreeNode<T> node = pushChildrenOntoStack(nodeStack.removeFirst());
			action.accept(node);
		}
	}

	@Override
	public Spliterator<VolumeOcTreeNode<T>> trySplit() {
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
	public Comparator<? super VolumeOcTreeNode<T>> getComparator() {
		throw new IllegalStateException();
	}
}
