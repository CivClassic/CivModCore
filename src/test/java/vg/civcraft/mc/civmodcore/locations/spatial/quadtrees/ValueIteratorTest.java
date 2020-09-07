package vg.civcraft.mc.civmodcore.locations.spatial.quadtrees;

import org.junit.Test;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.lang.Integer.max;
import static org.junit.Assert.*;
import static vg.civcraft.mc.civmodcore.locations.spatial.quadtrees.Util.*;

public class ValueIteratorTest {
	@Test
	public void emptyTreeTest() {
		QuadTree<IIntBBox2D> empty = new QuadTree<>(newBox(0, 0, 100, 100), 32);
		ValueIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new ValueIterator<>(empty.getRoot());
		assertFalse(it.hasNext());
	}

	@Test
	public void treeTest() {
		final int BOUND = 100;
		Set<IIntBBox2D> leftSet = new HashSet<>();
		QuadTree<IIntBBox2D> tree = new QuadTree<>(newCube(0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox2D box = newCube(x, y, rand.nextInt((BOUND - 1) - max(x, y)) + 1);
			tree.add(box);
			leftSet.add(box);
		}

		ValueIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new ValueIterator<>(tree.getRoot());
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
		QuadTree<IIntBBox2D> tree = new QuadTree<>(newCube(0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox2D box = newCube(x, y, rand.nextInt((BOUND - 1) - max(x, y)) + 1);
			tree.add(box);
			leftSet.add(box);
		}

		ValueIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new ValueIterator<>(tree.getRoot());
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
}
