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
import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox3D;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author psygate
 */
final class PredicateValueIterator<NodeType extends BaseOcTreeNode<NodeType, ValueType>, ValueType> implements Iterator<ValueType> {
	private PredicateNodeIterator<NodeType, ValueType> nodeIterator;
	private Iterator<ValueType> valueIterator = Collections.emptyIterator();
	private final Predicate<ValueType> valuePredicate;
	private final Predicate<IIntBBox3D> nodePredicate;

	public PredicateValueIterator(NodeType root, Predicate<ValueType> valuePredicate, Predicate<IIntBBox3D> nodePredicate) {
		this.valuePredicate = Objects.requireNonNull(valuePredicate);
		this.nodePredicate = Objects.requireNonNull(nodePredicate);
		nodeIterator = new PredicateNodeIterator<>(Objects.requireNonNull(root), Objects.requireNonNull(nodePredicate));
		setupValueIterator();
	}

	private void setupValueIterator() {
		while (!valueIterator.hasNext() && nodeIterator.hasNext()) {
			NodeType currentNode = nodeIterator.next();
			valueIterator = currentNode.values().stream().filter(valuePredicate).iterator();
		}
	}

	@Override
	public boolean hasNext() {
		setupValueIterator();

		return valueIterator.hasNext();
	}

	@Override
	public ValueType next() {
		setupValueIterator();

		return valueIterator.next();
	}
}
