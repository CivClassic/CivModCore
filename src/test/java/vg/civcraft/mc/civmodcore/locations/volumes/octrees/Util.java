package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.assertTrue;

public class Util {
	public final static Predicate<IIntVolumeBBox> PREDICATE_TRUE = value -> true;
	public final static Predicate<IIntVolumeBBox> PREDICATE_FALSE = value -> false;

	public static <T> Set<T> difference(Set<T> left, Set<T> right) {
		Set<T> compare = new HashSet<>(left);
		compare.removeAll(right);
		return compare;
	}


	public static <T extends IIntVolumeBBox> Set<T> toSet(Iterator<T> it) {
		Set<T> set = new HashSet<>();

		while (it.hasNext()) {
			set.add(it.next());
		}

		return set;
	}


	public static Set<OcTreeNode<IIntVolumeBBox>> getNodeSet(OcTree<IIntVolumeBBox> tree) {
		Set<OcTreeNode<IIntVolumeBBox>> nodeSet = new HashSet<>();
		LinkedList<OcTreeNode<IIntVolumeBBox>> stack = new LinkedList<>();
		stack.add(tree.getRoot());

		while (!stack.isEmpty()) {
			OcTreeNode<IIntVolumeBBox> node = stack.pop();
			nodeSet.add(node);
			if (node.hasChildren()) {
				stack.addAll(node.getChildren());
			}
		}

		return nodeSet;
	}

	public static IIntVolumeBBox newCube(int minx, int miny, int minz, int size) {
		assert size > 0;
		return newBox(minx, miny, minz, minx + size, miny + size, minz + size);
	}

	public static Random getRandom() {
		return new Random(0x1337733173311337L);
	}

	public static IIntVolumeBBox newBox(int minx, int miny, int minz, int maxx, int maxy, int maxz) {
		assertTrue(minx < maxx);
		assertTrue(miny < maxy);
		assertTrue(minz < maxz);

		return new IIntVolumeBBox() {
			@Override
			public int getMinX() {
				return minx;
			}

			@Override
			public int getMinY() {
				return miny;
			}

			@Override
			public int getMinZ() {
				return minz;
			}

			@Override
			public int getMaxX() {
				return maxx;
			}

			@Override
			public int getMaxY() {
				return maxy;
			}

			@Override
			public int getMaxZ() {
				return maxz;
			}

			@Override
			public boolean equals(Object obj) {
				if (obj instanceof IIntVolumeBBox) {
					return IIntVolumeBBox.equals(this, (IIntVolumeBBox) obj);
				} else {
					return false;
				}
			}

			@Override
			public String toString() {
				return "[(" + minx + ", " + miny + ", " + minz + ")-(" + maxx + ", " + maxy + ", " + maxz + ")]";
			}
		};
	}
}
