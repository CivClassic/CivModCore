package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import org.junit.Test;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntPoint3D;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntVolumeBBox;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static vg.civcraft.mc.civmodcore.locations.spatial.octrees.Util.*;

public class PointOcTreeTest {
	@Test
	public void testAdd() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.add(newPoint(1, 1, 1));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAdd2() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.add(newPoint(99, 99, 99));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddThrows() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.addThrowing(newPoint(-1, -1, -1));
		fail("Previous statement is supposed to throw an exception.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddThrows2() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.addThrowing(newPoint(-1, -1, -1));
		fail("Previous statement is supposed to throw an exception.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddThrows3() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.addThrowing(newPoint(-1, -1, -1));
		fail("Previous statement is supposed to throw an exception.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddThrows4() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.addThrowing(newPoint(-1, -1, -1));
		fail("Previous statement is supposed to throw an exception.");
	}

	////////////////////////////////////////////////////////////////////////
	@Test
	public void testAddNoThrow() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertTrue(tree.add(newPoint(1, 1, 1)));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow2() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertTrue(tree.add(newPoint(99, 99, 99)));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow3() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertFalse(tree.add(newPoint(-1, -1, -1)));
		assertTrue(tree.isEmpty());

		assertEquals(0, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow4() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertFalse(tree.add(newPoint(-1, -1, -1)));
		assertTrue(tree.isEmpty());

		assertEquals(0, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow5() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertFalse(tree.add(newPoint(-1, -1, -1)));
		assertTrue(tree.isEmpty());

		assertEquals(0, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow6() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertFalse(tree.add(newPoint(-1, -1, -1)));
		assertTrue(tree.isEmpty());

		assertEquals(0, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testRemove() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 1000), 8);
		Set<IIntPoint3D> values = new HashSet<>(newRandomPoints(1000, 100));

		for (IIntPoint3D box : values) {
			assertTrue(tree.add(box));
		}

		assertEquals(values.size(), tree.size());
		assertEquals(65, tree.countNodes());

		int size = tree.size();
		for (IIntPoint3D box : values) {
			assertTrue(tree.remove(box));
			size--;
			assertEquals(size, tree.size());
			assertFalse(tree.remove(box));
		}

		assertEquals(1, tree.countNodes());
		assertTrue(tree.getRoot().getChildren().isEmpty());
		assertTrue(tree.getRoot().values().isEmpty());
	}

	@Test
	public void testRemoveSame() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 1000), 8);
		Set<IIntPoint3D> values = new HashSet<>(clonePoint(newPoint(0, 0, 0), 100));

		for (IIntPoint3D box : values) {
			assertTrue(tree.add(box));
		}

		assertEquals(values.size(), tree.size());
		assertEquals(73, tree.countNodes());

		int size = tree.size();
		for (IIntPoint3D box : values) {
			assertTrue(tree.remove(box));
			size--;
			assertEquals(size, tree.size());
			assertFalse(tree.remove(box));
		}

		assertEquals(1, tree.countNodes());
		assertTrue(tree.getRoot().getChildren().isEmpty());
		assertTrue(tree.getRoot().values().isEmpty());
	}

	@Test
	public void testRemoveAll() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 1000), 8);
		Set<IIntPoint3D> values = new HashSet<>(newRandomPoints(1000, 100));

		assertTrue(tree.addAll(Collections.unmodifiableCollection(values)));
		assertEquals(65, tree.countNodes());
		assertEquals(values.size(), tree.size());

		int size = tree.size();
		assertTrue(tree.removeAll(Collections.unmodifiableCollection(values)));

		assertEquals(1, tree.countNodes());
		assertTrue(tree.getRoot().getChildren().isEmpty());
		assertTrue(tree.getRoot().values().isEmpty());
	}

	@Test
	public void testRemoveAllSame() {
		PointOcTree<IIntPoint3D> tree = new PointOcTree<>(newCube(0, 0, 0, 1000), 8);
		Set<IIntPoint3D> values = new HashSet<>(clonePoint(Util.newPoint(0, 0, 0), 100));


		assertTrue(tree.addAll(Collections.unmodifiableCollection(values)));
		assertEquals(73, tree.countNodes());
		assertEquals(values.size(), tree.size());

		assertTrue(tree.removeAll(Collections.unmodifiableCollection(values)));

		assertEquals(1, tree.countNodes());
		assertTrue(tree.getRoot().getChildren().isEmpty());
		assertTrue(tree.getRoot().values().isEmpty());
	}
}
