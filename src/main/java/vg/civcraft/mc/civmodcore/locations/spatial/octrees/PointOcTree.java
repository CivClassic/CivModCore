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

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox3D;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntPoint3D;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class PointOcTree<ValueType extends IIntPoint3D> implements Collection<ValueType> {
	private IIntBBox3D area;
	private PointOcTreeNode<ValueType> root;
	private final int splitSize;
	private int size = 0;

	public PointOcTree(IIntBBox3D area, int splitSize) {
		assert splitSize > 1;
		this.area = area;
		root = new PointOcTreeNode<>(area, splitSize);
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

		if (!IIntBBox3D.contains(root, value)) {
			return false;
		} else {
			PointOcTreeNode<ValueType> insertionNode = selectNodeContainingBBox(value);
			insertionNode.addValue(value);
			insertionNode.splitIfNecessary();
			size++;
			return true;
		}
	}

	private PointOcTreeNode<ValueType> selectNodeContainingBBox(ValueType value) {
		assert IIntBBox3D.contains(area, value);

		PredicateNodeIterator<PointOcTreeNode<ValueType>, ValueType> it = new PredicateNodeIterator<>(root, node -> IIntBBox3D.contains(node, value));
		PointOcTreeNode<ValueType> selected = root;

		while (it.hasNext()) {
			PointOcTreeNode<ValueType> next = it.next();

			if (IIntBBox3D.contains(next, value)) {
				selected = next;
			}
		}

		return selected;
	}

	public int size() {
		return size;
	}

	public void clear() {
		root = new PointOcTreeNode<>(area, splitSize);
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
		NodeIterator<PointOcTreeNode<ValueType>, ValueType> nodeIt = new NodeIterator<>(root);
		while (nodeIt.hasNext()) {
			size += nodeIt.next().values().size();
		}

		return size;
	}

	int countNodes() {
		int size = 0;
		LinkedList<PointOcTreeNode<ValueType>> stack = new LinkedList<>();
		stack.add(root);

		while (!stack.isEmpty()) {
			PointOcTreeNode<ValueType> node = stack.pop();
			if (node.hasChildren()) {
				stack.addAll(node.getChildren());
			}
			size++;
		}

		return size;
	}

	PointOcTreeNode<ValueType> getRoot() {
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
	public Stream<ValueType> selectAllInVolume(IIntBBox3D box, boolean parallel) {
		return selectByPredicate(box::intersects, box::contains, parallel);
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
	public Stream<ValueType> selectByPredicate(Predicate<IIntBBox3D> nodeSelectionPredicate, Predicate<ValueType> valueSelectionPredicate, boolean parallel) {
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
		if (o instanceof IIntBBox3D) {
			IIntBBox3D box = (IIntBBox3D) o;
			PredicateValueIterator<PointOcTreeNode<ValueType>, ValueType> it = new PredicateValueIterator<>(
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
		if (o instanceof IIntPoint3D) {
			IIntPoint3D box = (IIntPoint3D) o;
			if (removeInternal(box)) {
				rebuildTree();
				size--;
				return true;
			}
		}

		return false;
	}

	private boolean removeInternal(IIntPoint3D box) {
		PredicateNodeIterator<PointOcTreeNode<ValueType>, ValueType> nodeIterator = new PredicateNodeIterator<>(root, node -> node.contains(box));

		while (nodeIterator.hasNext()) {
			PointOcTreeNode<ValueType> node = nodeIterator.next();
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
		ValueIterator<PointOcTreeNode<ValueType>, ValueType> it = new ValueIterator<>(root);
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
