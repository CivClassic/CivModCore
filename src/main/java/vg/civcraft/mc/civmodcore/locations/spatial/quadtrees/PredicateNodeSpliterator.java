package vg.civcraft.mc.civmodcore.locations.spatial.quadtrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox3D;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

final class PredicateNodeSpliterator<T extends IIntBBox3D> implements Spliterator<AreaQuadTreeNode<T>> {
	private final Predicate<IIntBBox3D> predicate;
	private final LinkedList<AreaQuadTreeNode<T>> nodeStack = new LinkedList<>();

	public PredicateNodeSpliterator(AreaQuadTreeNode<T> root, Predicate<IIntBBox3D> predicate) {
		addNode(root);
		this.predicate = Objects.requireNonNull(predicate);
	}

	@Override
	public boolean tryAdvance(Consumer<? super AreaQuadTreeNode<T>> action) {
		if (!nodeStack.isEmpty()) {
			AreaQuadTreeNode<T> node = pushChildrenOntoStack(nodeStack.removeFirst());

			action.accept(node);

			return true;
		} else {
			return false;
		}
	}

	private void addNode(AreaQuadTreeNode<T> node) {
		if (predicate.test(node)) {
			nodeStack.add(Objects.requireNonNull(node));
		}
	}

	private AreaQuadTreeNode<T> pushChildrenOntoStack(AreaQuadTreeNode<T> node) {
		if (node.hasChildren()) {
			node.getChildren().forEach(this::addNode);
		}

		return node;
	}

	@Override
	public void forEachRemaining(Consumer<? super AreaQuadTreeNode<T>> action) {
		while (!nodeStack.isEmpty()) {
			AreaQuadTreeNode<T> node = pushChildrenOntoStack(nodeStack.removeFirst());
			action.accept(node);
		}
	}

	@Override
	public Spliterator<AreaQuadTreeNode<T>> trySplit() {
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
	public Comparator<? super AreaQuadTreeNode<T>> getComparator() {
		throw new IllegalStateException();
	}
}
