package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author psygate
 */
public final class OcTree<T extends IIntVolumeBBox> implements Collection<T> {
	private IIntVolumeBBox area;
	private OcTreeNode<T> root;
	private final int splitSize;
	private int size = 0;

	public OcTree(IIntVolumeBBox area, int splitSize) {
		assert splitSize > 1;
		this.area = area;
		root = new OcTreeNode<>(area, splitSize);
		this.splitSize = splitSize;
	}

	public void addThrowing(T value) {
		if (!add(value)) {
			throw new IllegalArgumentException("Object " + value + " not contained in OcTree area.");
		}
	}

	@Override
	public boolean add(T value) {
		Objects.requireNonNull(value);

		if (!IIntVolumeBBox.contains(root, value)) {
			return false;
		} else {
			OcTreeNode<T> insertionNode = selectNodeContainingBBox(value);
			insertionNode.addValue(value);
			insertionNode.splitIfNecessary();
			size++;
			return true;
		}
	}

	private OcTreeNode<T> selectNodeContainingBBox(T value) {
		assert IIntVolumeBBox.contains(area, value);

		PredicateNodeIterator<T> it = new PredicateNodeIterator<>(root, node -> IIntVolumeBBox.contains(node, value));
		OcTreeNode<T> selected = root;

		while (it.hasNext()) {
			OcTreeNode<T> next = it.next();

			if (IIntVolumeBBox.contains(next, value)) {
				selected = next;
			}
		}

		return selected;
	}

	public int size() {
		return size;
	}

	public void clear() {
		root = new OcTreeNode<>(area, splitSize);
		size = 0;
	}

	@Override
	public Spliterator<T> spliterator() {
		return null;
	}

	@Override
	public Stream<T> stream() {
		return null;
	}

	@Override
	public Stream<T> parallelStream() {
		return null;
	}

	//test method to assert that the counted size is the real size. very costly to run.
	int countSize() {
		int size = 0;
		NodeIterator<T> nodeIt = new NodeIterator<>(root);
		while (nodeIt.hasNext()) {
			size += nodeIt.next().values().size();
		}

		return size;
	}

	int countNodes() {
		int size = 0;
		LinkedList<OcTreeNode<T>> stack = new LinkedList<>();
		stack.add(root);

		while (!stack.isEmpty()) {
			OcTreeNode<T> node = stack.pop();
			if (node.hasChildren()) {
				stack.addAll(node.getChildren());
			}
			size++;
		}

		return size;
	}

	OcTreeNode<T> getRoot() {
		return root;
	}

	int getSplitSize() {
		return splitSize;
	}

	/**
	 * Selects all values contained within the provided volume box.
	 *
	 * @param box      Volume in which the values should be contained.
	 * @param parallel If the stream evaluation should happen in parallel.
	 * @return A stream containing all values in the tree, that are contained within the provided volume.
	 */
	public Stream<T> selectAllInVolume(IIntVolumeBBox box, boolean parallel) {
		return selectByPredicate(box::contains, box::intersects, parallel);
	}

	/**
	 * Selects all values intersecting with the provided volume box.
	 *
	 * @param box      Volume with which the values should intersect.
	 * @param parallel If the stream evaluation should happen in parallel.
	 * @return A stream containing all values in the tree, that are intersecting with the provided volume.
	 */
	public Stream<T> selectAllIntersectingVolume(IIntVolumeBBox box, boolean parallel) {
		return selectByPredicate(box::intersects, box::intersects, parallel);
	}

	/**
	 * Selects all values that contain the given point.
	 *
	 * @param x        X-Coordinate of the point.
	 * @param y        Y-Coordinate of the point.
	 * @param z        Z-Coordinate of the point.
	 * @param parallel If the stream evaluation should happen in parallel.
	 * @return A stream containing all values in the tree, that contain the given point.
	 */
	public Stream<T> selectAllContainingPoint(int x, int y, int z, boolean parallel) {
		return selectByPredicate(box -> box.contains(x, y, z), box -> box.contains(x, y, z), parallel);
	}

	/**
	 * Selects values by predicate and returns a stream of those values.
	 *
	 * @param nodeSelectionPredicate  Predicate that evaluates on the bounding box of tree nodes. A node is only iterated
	 *                                over if this predicate evaluates to true.
	 * @param valueSelectionPredicate Predicate that evaluates on the values in a tree node selected by the nodeSelectionPredicate,
	 *                                only values for which this predicate is true are returned in the stream.
	 * @param parallel                If the stream evaluation should happen in parallel.
	 * @return A stream containing all values for which the valueSelectionPredicate is true, and which are in a tree node,
	 * for which the nodeSelectionPredicate is true.
	 */
	public Stream<T> selectByPredicate(Predicate<IIntVolumeBBox> nodeSelectionPredicate, Predicate<IIntVolumeBBox> valueSelectionPredicate, boolean parallel) {
		return StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(
						new PredicateValueIterator<>(
								root,
								valueSelectionPredicate,
								nodeSelectionPredicate),
						Spliterator.IMMUTABLE | Spliterator.DISTINCT | Spliterator.NONNULL | Spliterator.ORDERED
				)
				, parallel);
	}

	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(Object o) {
		if (o instanceof IIntVolumeBBox) {
			IIntVolumeBBox box = (IIntVolumeBBox) o;
			PredicateValueIterator<T> it = new PredicateValueIterator<>(
					root,
					value -> value.equals(box),
					node -> node.contains(box)
			);

			return it.hasNext();
		} else {
			return false;
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new ValueIterator<>(root);
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		stream().forEach(action);
	}

	@Override
	public Object[] toArray() {
		Object[] out = new Object[size()];
		Iterator<T> it = iterator();

		for (int i = 0; i < size(); i++) {
			out[i] = it.next();
		}

		return out;
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {
		final T1[] arr;
		if (a.length < size()) {
			arr = Arrays.copyOf(a, size());
		} else {
			arr = a;
		}

		Iterator<T> it = iterator();

		for (int i = 0; i < size(); i++) {
			arr[i] = (T1) it.next();
		}

		return arr;
	}

	@Override
	public boolean remove(Object o) {
		if (o instanceof IIntVolumeBBox) {
			IIntVolumeBBox box = (IIntVolumeBBox) o;
			if (removeInternal(box)) {
				rebuildTree();
				size--;
				return true;
			}
		}

		return false;
	}

	private boolean removeInternal(IIntVolumeBBox box) {
		PredicateNodeIterator<T> nodeIterator = new PredicateNodeIterator<>(root, node -> node.contains(box));

		while (nodeIterator.hasNext()) {
			OcTreeNode<T> node = nodeIterator.next();
			if (node.remove(box)) {
				return true;
			}
		}

		return false;
	}

	private void rebuildTree() {
		root.rebuildRecursively();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return c.stream().allMatch(this::contains);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return c.stream().allMatch(this::add);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return removeIf(c::contains);
	}

	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		boolean mod = false;
		ValueIterator<T> it = new ValueIterator<>(root);
		while (it.hasNext()) {
			if (filter.test(it.next())) {
				it.remove();
				mod = true;
			}
		}

		if (mod) rebuildTree();

		return mod;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return removeIf(value -> !c.contains(value));
	}
}
