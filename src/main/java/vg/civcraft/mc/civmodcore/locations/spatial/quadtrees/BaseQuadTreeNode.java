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

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;
import vg.civcraft.mc.civmodcore.locations.spatial.IntBBox2D;

import java.util.*;

/**
 * @author psygate
 */
abstract class BaseQuadTreeNode<NodeType extends BaseQuadTreeNode<NodeType, ValueType>, ValueType> extends IntBBox2D {
	public final static int CHILDREN_SIZE = 4;

	//	private final OcTree parentTree;
	private final NodeType parent;
	private final ArrayList<NodeType> children = new ArrayList<>(CHILDREN_SIZE);
	private List<ValueType> values = new LinkedList<>();
	private int splitSize;

	BaseQuadTreeNode(IIntBBox2D bbox, int splitSize) {
		this(bbox.getMinX(), bbox.getMinY(), bbox.getMaxX(), bbox.getMaxY(), splitSize, null);
	}

	BaseQuadTreeNode(IIntBBox2D bbox, int splitSize, NodeType parent) {
		this(bbox.getMinX(), bbox.getMinY(), bbox.getMaxX(), bbox.getMaxY(), splitSize, parent);
	}

	BaseQuadTreeNode(int minX, int minY, int maxX, int maxY, int splitSize, NodeType parent) {
		super(minX, minY, maxX, maxY);
		this.parent = parent;
		this.splitSize = splitSize;
	}

	protected abstract boolean nodeContainsValue(ValueType value);

	protected abstract NodeType selectChild(ValueType value);

	protected abstract Optional<NodeType> selectChildOpt(ValueType value);

	protected abstract NodeType createNewNode(int minX, int minY, int maxX, int maxY, int splitSize, NodeType nodeType);

	List<NodeType> getChildren() {
		return children;
	}

	NodeType getChild(int i) {
		return children.get(i);
	}

	int childrenSize() {
		return children.size();
	}

	List<ValueType> values() {
		return values;
	}

	void addValue(ValueType value) {
		assert nodeContainsValue(value);
		values.add(value);
	}


	void splitIfNecessary() {
		if (children.isEmpty() && isSplittable()) {
			if (values.size() > splitSize) {
				createChildrenNodes();
				moveValuesIntoChildren();
			}
		}
	}

	private void moveValuesIntoChildren() {
		assert childrenSize() == CHILDREN_SIZE;
		ListIterator<ValueType> it = values.listIterator();
		while (it.hasNext()) {
			ValueType value = it.next();
			// If a bbox doesnt fit into any child, the value stays here in the root node.
			selectChildOpt(value).ifPresent(node -> {
				node.addValue(value);
				it.remove();
			});
		}
	}

	private void addChild(NodeType node) {
		children.add(Objects.requireNonNull(node));
	}

	private void createChildrenNodes() {
		//Lower 4 cubes.
		addChild(createNewNode(getMinX(), getMinY(), getHalfPointX(), getHalfPointY(), splitSize, (NodeType) this));
		addChild(createNewNode(getHalfPointX(), getMinY(), getMaxX(), getHalfPointY(), splitSize, (NodeType) this));
		addChild(createNewNode(getMinX(), getMinY(), getHalfPointX(), getHalfPointY(), splitSize, (NodeType) this));
		addChild(createNewNode(getHalfPointX(), getMinY(), getMaxX(), getHalfPointY(), splitSize, (NodeType) this));

		//Upper 4 cubes
		addChild(createNewNode(getMinX(), getHalfPointY(), getHalfPointX(), getMaxY(), splitSize, (NodeType) this));
		addChild(createNewNode(getHalfPointX(), getHalfPointY(), getMaxX(), getMaxY(), splitSize, (NodeType) this));
		addChild(createNewNode(getMinX(), getHalfPointY(), getHalfPointX(), getMaxY(), splitSize, (NodeType) this));
		addChild(createNewNode(getHalfPointX(), getHalfPointY(), getMaxX(), getMaxY(), splitSize, (NodeType) this));
	}


	/**
	 * Tree nodes that only have a width of 1 cannot be split any further. Those are terminal nodes.
	 */
	private boolean isSplittable() {
		return width() >= 2 && height() >= 2;
	}

	boolean hasChildren() {
		return !children.isEmpty();
	}

	boolean hasNoValues() {
		return values.isEmpty();
	}

	@Override
	public String toString() {
		return "OcTreeNode{" +
				super.toString()
				+ "}";
	}

	boolean remove(Object box) {
		return values.remove(box);
	}

	int size() {
		return values.size();
	}

	int subTreeSize() {
		int acc = values.size();

		for (NodeType child : children) {
			acc += child.subTreeSize();
		}

		return acc;
	}

	void rebuildRecursively() {
		for (NodeType child : children) {
			child.rebuildRecursively();
		}

		if (subTreeSize() <= splitSize) {
			for (NodeType child : children) {
				NodeIterator<NodeType, ValueType> nodes = new NodeIterator<>(child);
				while (nodes.hasNext()) {
					values.addAll(nodes.next().values());
				}
			}

			deleteAllChildren();
		}
	}

	private void deleteAllChildren() {
		children.clear();
	}
}

