package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;
import vg.civcraft.mc.civmodcore.locations.spatial.IntBox3D;

import java.util.*;

/**
 * @author psygate
 */
abstract class BaseOcTreeNode<NodeType extends BaseOcTreeNode<NodeType, ValueType>, ValueType> extends IntBox3D {
	public final static int CHILDREN_SIZE = 8;

	//	private final OcTree parentTree;
	private final NodeType parent;
	private final ArrayList<NodeType> children = new ArrayList<>(8);
	private List<ValueType> values = new LinkedList<>();
	private int splitSize;

	BaseOcTreeNode(IIntBBox2D bbox, int splitSize) {
		this(bbox.getMinX(), bbox.getMinY(), bbox.getMinZ(), bbox.getMaxX(), bbox.getMaxY(), bbox.getMaxZ(), splitSize, null);
	}

	BaseOcTreeNode(IIntBBox2D bbox, int splitSize, NodeType parent) {
		this(bbox.getMinX(), bbox.getMinY(), bbox.getMinZ(), bbox.getMaxX(), bbox.getMaxY(), bbox.getMaxZ(), splitSize, parent);
	}

	BaseOcTreeNode(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int splitSize, NodeType parent) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.parent = parent;
		this.splitSize = splitSize;
	}

	protected abstract boolean nodeContainsValue(ValueType value);

	protected abstract NodeType selectChild(ValueType value);

	protected abstract Optional<NodeType> selectChildOpt(ValueType value);

	protected abstract NodeType createNewNode(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int splitSize, NodeType nodeType);


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
		addChild(createNewNode(getMinX(), getMinY(), getMinZ(), getHalfPointX(), getHalfPointY(), getHalfPointZ(), splitSize, (NodeType) this));
		addChild(createNewNode(getHalfPointX(), getMinY(), getMinZ(), getMaxX(), getHalfPointY(), getHalfPointZ(), splitSize, (NodeType) this));
		addChild(createNewNode(getMinX(), getMinY(), getHalfPointZ(), getHalfPointX(), getHalfPointY(), getMaxZ(), splitSize, (NodeType) this));
		addChild(createNewNode(getHalfPointX(), getMinY(), getHalfPointZ(), getMaxX(), getHalfPointY(), getMaxZ(), splitSize, (NodeType) this));

		//Upper 4 cubes
		addChild(createNewNode(getMinX(), getHalfPointY(), getMinZ(), getHalfPointX(), getMaxY(), getHalfPointZ(), splitSize, (NodeType) this));
		addChild(createNewNode(getHalfPointX(), getHalfPointY(), getMinZ(), getMaxX(), getMaxY(), getHalfPointZ(), splitSize, (NodeType) this));
		addChild(createNewNode(getMinX(), getHalfPointY(), getHalfPointZ(), getHalfPointX(), getMaxY(), getMaxZ(), splitSize, (NodeType) this));
		addChild(createNewNode(getHalfPointX(), getHalfPointY(), getHalfPointZ(), getMaxX(), getMaxY(), getMaxZ(), splitSize, (NodeType) this));
	}


	/**
	 * Tree nodes that only have a width of 1 cannot be split any further. Those are terminal nodes.
	 */
	private boolean isSplittable() {
		return width() >= 2 && height() >= 2 && depth() >= 2;
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

