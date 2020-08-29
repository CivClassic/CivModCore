package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;
import vg.civcraft.mc.civmodcore.locations.volumes.IntVolumeBBox;

import java.util.*;

/**
 * @author psygate
 */
final class OcTreeNode<T extends IIntVolumeBBox> extends IntVolumeBBox {
	public final static int CHILDREN_SIZE = 8;
	private final static OcTreeNode[] NULL_CHILDREN = new OcTreeNode[CHILDREN_SIZE];

	//	private final OcTree parentTree;
	private final OcTreeNode parent;
	private final ArrayList<OcTreeNode<T>> children = new ArrayList<>(8);
	private List<T> values = new LinkedList<>();
	private int splitSize;

	public OcTreeNode(IIntVolumeBBox bbox, int splitSize) {
		this(bbox.getMinX(), bbox.getMinY(), bbox.getMinZ(), bbox.getMaxX(), bbox.getMaxY(), bbox.getMaxZ(), splitSize, null);
	}

	public OcTreeNode(IIntVolumeBBox bbox, int splitSize, OcTreeNode parent) {
		this(bbox.getMinX(), bbox.getMinY(), bbox.getMinZ(), bbox.getMaxX(), bbox.getMaxY(), bbox.getMaxZ(), splitSize, parent);
	}

	private OcTreeNode(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int splitSize, OcTreeNode parent) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
//		this.parentTree = Objects.requireNonNull(parentTree);
		this.parent = parent;
		this.splitSize = splitSize;
	}

	public List<OcTreeNode<T>> getChildren() {
		return children;
	}

	public OcTreeNode<T> getChild(int i) {
		return children.get(i);
	}

	public List<T> values() {
		return values;
	}

	public void addValue(T value) {
		assert IIntVolumeBBox.contains(this, value);
		values.add(value);
	}

	public void splitIfNecessary() {
		if (children.isEmpty() && isSplittable()) {
			if (values.size() > splitSize) {
				createChildrenNodes();
				moveValuesIntoChildren();
			}
		}
	}

	private void moveValuesIntoChildren() {
		ListIterator<T> it = values.listIterator();
		while (it.hasNext()) {
			T value = it.next();
			OcTreeNode<T> node = selectChild(value);

			// If a bbox doesnt fit into any child, the value stays here in the root node.1
			if (node != null) {
				node.addValue(value);
				it.remove();
			}
		}
	}

	private OcTreeNode<T> selectChild(T value) {
		for (int i = 0; i < children.size(); i++) {
			if (IIntVolumeBBox.contains(children.get(i), value)) {
				return children.get(i);
			}
		}

		return null;
	}

	private void createChildrenNodes() {
		//Lower 4 cubes.
		children.add(new OcTreeNode<>(getMinX(), getMinY(), getMinZ(), getHalfPointX(), getHalfPointY(), getHalfPointZ(), splitSize, this));
		children.add(new OcTreeNode<>(getHalfPointX(), getMinY(), getMinZ(), getMaxX(), getHalfPointY(), getHalfPointZ(), splitSize, this));
		children.add(new OcTreeNode<>(getMinX(), getMinY(), getHalfPointZ(), getHalfPointX(), getHalfPointY(), getMaxZ(), splitSize, this));
		children.add(new OcTreeNode<>(getHalfPointX(), getMinY(), getHalfPointZ(), getMaxX(), getHalfPointY(), getMaxZ(), splitSize, this));

		//Upper 4 cubes
		children.add(new OcTreeNode<>(getMinX(), getHalfPointY(), getMinZ(), getHalfPointX(), getMaxY(), getHalfPointZ(), splitSize, this));
		children.add(new OcTreeNode<>(getHalfPointX(), getHalfPointY(), getMinZ(), getMaxX(), getMaxY(), getHalfPointZ(), splitSize, this));
		children.add(new OcTreeNode<>(getMinX(), getHalfPointY(), getHalfPointZ(), getHalfPointX(), getMaxY(), getMaxZ(), splitSize, this));
		children.add(new OcTreeNode<>(getHalfPointX(), getHalfPointY(), getHalfPointZ(), getMaxX(), getMaxY(), getMaxZ(), splitSize, this));
	}

	/**
	 * Tree nodes that only have a width of 1 cannot be split any further. Those are terminal nodes.
	 */
	private boolean isSplittable() {
		return width() >= 2 && height() >= 2 && depth() >= 2;
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public boolean hasNoValues() {
		return values.isEmpty();
	}

	@Override
	public String toString() {
		return "OcTreeNode{" +
				super.toString()
				+ "}";
	}
}

