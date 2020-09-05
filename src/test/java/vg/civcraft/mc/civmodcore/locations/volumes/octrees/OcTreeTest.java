package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import org.junit.Test;
import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static vg.civcraft.mc.civmodcore.locations.volumes.octrees.Util.newCube;

public class OcTreeTest {
	@Test
	public void testAdd() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.add(newCube(1, 1, 1, 1));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAdd2() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.add(newCube(99, 99, 99, 1));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddThrows() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.addThrowing(newCube(-1, -1, -1, 1));
		fail("Previous statement is supposed to throw an exception.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddThrows2() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.addThrowing(newCube(-1, -1, -1, 1));
		fail("Previous statement is supposed to throw an exception.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddThrows3() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.addThrowing(newCube(-1, -1, -1, 50));
		fail("Previous statement is supposed to throw an exception.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddThrows4() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.addThrowing(newCube(-1, -1, -1, 101));
		fail("Previous statement is supposed to throw an exception.");
	}

	////////////////////////////////////////////////////////////////////////
	@Test
	public void testAddNoThrow() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertTrue(tree.add(newCube(1, 1, 1, 1)));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow2() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertTrue(tree.add(newCube(99, 99, 99, 1)));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow3() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertFalse(tree.add(newCube(-1, -1, -1, 1)));
		assertTrue(tree.isEmpty());

		assertEquals(0, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow4() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertFalse(tree.add(newCube(-1, -1, -1, 1)));
		assertTrue(tree.isEmpty());

		assertEquals(0, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow5() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertFalse(tree.add(newCube(-1, -1, -1, 50)));
		assertTrue(tree.isEmpty());

		assertEquals(0, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow6() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertFalse(tree.add(newCube(-1, -1, -1, 101)));
		assertTrue(tree.isEmpty());

		assertEquals(0, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testRemove() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 1000), 8);
		Set<IIntVolumeBBox> values = new HashSet<>(Util.newRandomCubes(1000, 100));

		for (IIntVolumeBBox box : values) {
			assertTrue(tree.add(box));
		}

		assertEquals(values.size(), tree.size());
		assertEquals(49, tree.countNodes());

		int size = tree.size();
		for (IIntVolumeBBox box : values) {
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
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 1000), 8);
		Set<IIntVolumeBBox> values = new HashSet<>(Util.cloneCube(Util.newCube(0, 0, 0, 1000), 100));

		for (IIntVolumeBBox box : values) {
			assertTrue(tree.add(box));
		}

		assertEquals(values.size(), tree.size());
		assertEquals(9, tree.countNodes());

		int size = tree.size();
		for (IIntVolumeBBox box : values) {
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
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 1000), 8);
		Set<IIntVolumeBBox> values = new HashSet<>(Util.newRandomCubes(1000, 100));

		assertTrue(tree.addAll(Collections.unmodifiableCollection(values)));
		assertEquals(49, tree.countNodes());
		assertEquals(values.size(), tree.size());

		int size = tree.size();
		assertTrue(tree.removeAll(Collections.unmodifiableCollection(values)));

		assertEquals(1, tree.countNodes());
		assertTrue(tree.getRoot().getChildren().isEmpty());
		assertTrue(tree.getRoot().values().isEmpty());
	}

	@Test
	public void testRemoveAllSame() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 1000), 8);
		Set<IIntVolumeBBox> values = new HashSet<>(Util.cloneCube(Util.newCube(0, 0, 0, 1000), 100));


		assertTrue(tree.addAll(Collections.unmodifiableCollection(values)));
		assertEquals(9, tree.countNodes());
		assertEquals(values.size(), tree.size());

		assertTrue(tree.removeAll(Collections.unmodifiableCollection(values)));

		assertEquals(1, tree.countNodes());
		assertTrue(tree.getRoot().getChildren().isEmpty());
		assertTrue(tree.getRoot().values().isEmpty());
	}
}
