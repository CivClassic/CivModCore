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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author psygate
 */
final class PredicateNodeIterator<NodeType extends BaseOcTreeNode<NodeType, ValueType>, ValueType> implements Iterator<NodeType> {
	private final LinkedList<NodeType> queue = new LinkedList<>();
	private final Predicate<IIntBBox3D> predicate;

	public PredicateNodeIterator(NodeType root, Predicate<IIntBBox3D> predicate) {
		queue.push(Objects.requireNonNull(root));
		this.predicate = Objects.requireNonNull(predicate);
	}

	@Override
	public boolean hasNext() {
		if (queue.isEmpty()) {
			return false;
		} else if (predicate.test(queue.peek())) {
			return true;
		} else {
			findNextValue();
			return hasNext();
		}
	}

	private void findNextValue() {
		while (!queue.isEmpty() && !predicate.test(queue.peek())) {
			NodeType value = queue.removeFirst();
			if (value.hasChildren()) {
				queue.addAll(value.getChildren());
			}
		}
	}

	@Override
	public NodeType next() {
		NodeType value = queue.removeFirst();
		if (value.hasChildren()) {
			queue.addAll(value.getChildren());
		}

		return value;
	}
}
