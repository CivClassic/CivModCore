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
import java.util.NoSuchElementException;
import java.util.Objects;

final class ValueIterator<NodeType extends BaseQuadTreeNode<NodeType, ValueType>, ValueType> implements Iterator<ValueType> {
	private NodeIterator<NodeType, ValueType> nodeIterator;
	private Iterator<ValueType> valueIterator;

	public ValueIterator(NodeType root) {
		nodeIterator = new NodeIterator<>(Objects.requireNonNull(root));
		valueIterator = nodeIterator.next().values().iterator();
	}

	private void prepareNextValue() {
		while (!valueIterator.hasNext() && nodeIterator.hasNext()) {
			valueIterator = nodeIterator.next().values().iterator();
		}
	}

	@Override
	public boolean hasNext() {
		prepareNextValue();
		return valueIterator.hasNext();
	}

	@Override
	public void remove() {
		valueIterator.remove();
	}

	@Override
	public ValueType next() {
		prepareNextValue();

		if (valueIterator.hasNext()) {
			return valueIterator.next();
		}

		throw new NoSuchElementException("No next value in iterator.");
	}
}
