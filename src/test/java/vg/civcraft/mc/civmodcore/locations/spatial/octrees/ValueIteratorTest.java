package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import org.junit.Test;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.lang.Integer.max;
import static org.junit.Assert.*;

public class ValueIteratorTest {
	@Test
	public void emptyTreeTest() {
		OcTree<IIntBBox2D> empty = new OcTree<>(newBox(0, 0, 0, 100, 100, 100), 32);
		ValueIterator<VolumeOcTreeNode<IIntBBox2D>, IIntBBox2D> it = new ValueIterator<>(empty.getRoot());
		assertFalse(it.hasNext());
	}

	@Test
	public void treeTest() {
		final int BOUND = 100;
		Set<IIntBBox2D> leftSet = new HashSet<>();
		OcTree<IIntBBox2D> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox2D box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
			tree.add(box);
			leftSet.add(box);
		}

		ValueIterator<VolumeOcTreeNode<IIntBBox2D>, IIntBBox2D> it = new ValueIterator<>(tree.getRoot());
		assertTrue(it.hasNext());

		Set<IIntBBox2D> rightSet = toSet(it);
		assertEquals(leftSet.size(), rightSet.size());


		assertEquals(
				"Set difference:" +
						"\nOnly left: " + difference(leftSet, rightSet) +
						"\nOnly right: " + difference(rightSet, leftSet)
				,
				leftSet,
				rightSet
		);
	}

	@Test
	public void treeTest2() {
		final int BOUND = 10000;
		Set<IIntBBox2D> leftSet = new HashSet<>();
		OcTree<IIntBBox2D> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox2D box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
			tree.add(box);
			leftSet.add(box);
		}

		ValueIterator<VolumeOcTreeNode<IIntBBox2D>, IIntBBox2D> it = new ValueIterator<>(tree.getRoot());
		assertTrue(it.hasNext());

		Set<IIntBBox2D> rightSet = toSet(it);
		assertEquals(leftSet.size(), rightSet.size());


		assertEquals(
				"Set difference:" +
						"\nOnly left: " + difference(leftSet, rightSet) +
						"\nOnly right: " + difference(rightSet, leftSet)
				,
				leftSet,
				rightSet
		);
	}

	private <NodeType extends BaseOcTreeNode<NodeType, T>, T extends IIntBBox2D> Set<T> toSet(ValueIterator<NodeType, T> it) {
		Set<T> set = new HashSet<>();

		while (it.hasNext()) {
			set.add(it.next());
		}

		return set;
	}

	private static <T> Set<T> difference(Set<T> left, Set<T> right) {
		Set<T> compare = new HashSet<>(left);
		compare.removeAll(right);
		return compare;
	}

	private static IIntBBox2D newCube(int minx, int miny, int minz, int size) {
		assert size > 0;
		return newBox(minx, miny, minz, minx + size, miny + size, minz + size);
	}

	private static Random getRandom() {
		return new Random(0x1337733173311337L);
	}

	private static IIntBBox2D newBox(int minx, int miny, int minz, int maxx, int maxy, int maxz) {
		assertTrue(minx < maxx);
		assertTrue(miny < maxy);
		assertTrue(minz < maxz);

		return new IIntBBox2D() {
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
				if (obj instanceof IIntBBox2D) {
					return IIntBBox2D.equals(this, (IIntBBox2D) obj);
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
