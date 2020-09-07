/*
MIT License

Copyright (c) 2020 psygate (psygate.github.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author psygate
 */
public final class OcTree<ValueType extends IIntBBox2D> implements Collection<ValueType> {
	private IIntBBox2D area;
	private VolumeOcTreeNode<ValueType> root;
	private final int splitSize;
	private int size = 0;

	public OcTree(IIntBBox2D area, int splitSize) {
		assert splitSize > 1;
		this.area = area;
		root = new VolumeOcTreeNode<>(area, splitSize);
		this.splitSize = splitSize;
	}

	public void addThrowing(ValueType value) {
		if (!add(value)) {
			throw new IllegalArgumentException("Object " + value + " not contained in OcTree area.");
		}
	}

	@Override
	public boolean add(ValueType value) {
		Objects.requireNonNull(value);

		if (!IIntBBox2D.contains(root, value)) {
			return false;
		} else {
			VolumeOcTreeNode<ValueType> insertionNode = selectNodeContainingBBox(value);
			insertionNode.addValue(value);
			insertionNode.splitIfNecessary();
			size++;
			return true;
		}
	}

	private VolumeOcTreeNode<ValueType> selectNodeContainingBBox(ValueType value) {
		assert IIntBBox2D.contains(area, value);

		PredicateNodeIterator<VolumeOcTreeNode<ValueType>, ValueType> it = new PredicateNodeIterator<>(root, node -> IIntBBox2D.contains(node, value));
		VolumeOcTreeNode<ValueType> selected = root;

		while (it.hasNext()) {
			VolumeOcTreeNode<ValueType> next = it.next();

			if (IIntBBox2D.contains(next, value)) {
				selected = next;
			}
		}

		return selected;
	}

	public int size() {
		return size;
	}

	public void clear() {
		root = new VolumeOcTreeNode<>(area, splitSize);
		size = 0;
	}

	@Override
	public Spliterator<ValueType> spliterator() {
		return null;
	}

	@Override
	public Stream<ValueType> stream() {
		return null;
	}

	@Override
	public Stream<ValueType> parallelStream() {
		return null;
	}

	//test method to assert that the counted size is the real size. very costly to run.
	int countSize() {
		int size = 0;
		NodeIterator<VolumeOcTreeNode<ValueType>, ValueType> nodeIt = new NodeIterator<>(root);
		while (nodeIt.hasNext()) {
			size += nodeIt.next().values().size();
		}

		return size;
	}

	int countNodes() {
		int size = 0;
		LinkedList<VolumeOcTreeNode<ValueType>> stack = new LinkedList<>();
		stack.add(root);

		while (!stack.isEmpty()) {
			VolumeOcTreeNode<ValueType> node = stack.pop();
			if (node.hasChildren()) {
				stack.addAll(node.getChildren());
			}
			size++;
		}

		return size;
	}

	VolumeOcTreeNode<ValueType> getRoot() {
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
	public Stream<ValueType> selectAllInVolume(IIntBBox2D box, boolean parallel) {
		return selectByPredicate(box::contains, box::intersects, parallel);
	}

	/**
	 * Selects all values intersecting with the provided volume box.
	 *
	 * @param box      Volume with which the values should intersect.
	 * @param parallel If the stream evaluation should happen in parallel.
	 * @return A stream containing all values in the tree, that are intersecting with the provided volume.
	 */
	public Stream<ValueType> selectAllIntersectingVolume(IIntBBox2D box, boolean parallel) {
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
	public Stream<ValueType> selectAllContainingPoint(int x, int y, int z, boolean parallel) {
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
	public Stream<ValueType> selectByPredicate(Predicate<IIntBBox2D> nodeSelectionPredicate, Predicate<ValueType> valueSelectionPredicate, boolean parallel) {
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
		if (o instanceof IIntBBox2D) {
			IIntBBox2D box = (IIntBBox2D) o;
			PredicateValueIterator<VolumeOcTreeNode<ValueType>, ValueType> it = new PredicateValueIterator<>(
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
	public Iterator<ValueType> iterator() {
		return new ValueIterator<>(root);
	}

	@Override
	public void forEach(Consumer<? super ValueType> action) {
		stream().forEach(action);
	}

	@Override
	public Object[] toArray() {
		Object[] out = new Object[size()];
		Iterator<ValueType> it = iterator();

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

		Iterator<ValueType> it = iterator();

		for (int i = 0; i < size(); i++) {
			arr[i] = (T1) it.next();
		}

		return arr;
	}

	@Override
	public boolean remove(Object o) {
		if (o instanceof IIntBBox2D) {
			IIntBBox2D box = (IIntBBox2D) o;
			if (removeInternal(box)) {
				rebuildTree();
				size--;
				return true;
			}
		}

		return false;
	}

	private boolean removeInternal(IIntBBox2D box) {
		PredicateNodeIterator<VolumeOcTreeNode<ValueType>, ValueType> nodeIterator = new PredicateNodeIterator<>(root, node -> node.contains(box));

		while (nodeIterator.hasNext()) {
			VolumeOcTreeNode<ValueType> node = nodeIterator.next();
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
	public boolean addAll(Collection<? extends ValueType> c) {
		return c.stream().allMatch(this::add);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return removeIf(c::contains);
	}

	@Override
	public boolean removeIf(Predicate<? super ValueType> filter) {
		boolean mod = false;
		ValueIterator<VolumeOcTreeNode<ValueType>, ValueType> it = new ValueIterator<>(root);
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
