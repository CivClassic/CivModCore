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

package vg.civcraft.mc.civmodcore.locations.spatial.quadtrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox3D;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

final class NodeSpliterator<T extends IIntBBox3D> implements Spliterator<AreaQuadTreeNode<T>> {
	private final LinkedList<AreaQuadTreeNode<T>> nodeStack = new LinkedList<>();

	public NodeSpliterator(AreaQuadTreeNode<T> root) {
		addNode(root);
		nodeStack.add(Objects.requireNonNull(root));
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
		nodeStack.add(Objects.requireNonNull(node));
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
	public Comparator<? super AreaQuadTreeNode<T>> getComparator() {
		throw new IllegalStateException();
	}
}
