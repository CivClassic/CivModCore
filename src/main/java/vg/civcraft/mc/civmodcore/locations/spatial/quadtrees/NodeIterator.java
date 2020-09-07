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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

final class NodeIterator<NodeType extends BaseQuadTreeNode<NodeType, ValueType>, ValueType> implements Iterator<NodeType> {
	private final LinkedList<NodeType> queue = new LinkedList<>();

	public NodeIterator(NodeType root) {
		queue.push(Objects.requireNonNull(root));
	}

	@Override
	public final boolean hasNext() {
		return !queue.isEmpty();
	}

	@Override
	public final NodeType next() {
		NodeType next = queue.removeFirst();
		pushChildren(next);
		return next;
	}

	final void addIteratorValue(NodeType value) {
		queue.add(Objects.requireNonNull(value));
	}

	void pushChildren(NodeType next) {
		if (next.hasChildren()) {
			// This could be an easy expression like
			// next.getChildren().forEach(queue::add);
			// But for loops are maximum performance.
			for (int i = 0; i < AreaQuadTreeNode.CHILDREN_SIZE; i++) {
				addIteratorValue(next.getChild(i));
			}
		}
	}
}
