package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import org.junit.Test;
import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;

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

		tree.add(newCube(-1, -1, -1, 1));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddThrows2() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.add(newCube(-1, -1, -1, 1));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddThrows3() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.add(newCube(-1, -1, -1, 50));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddThrows4() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.add(newCube(-1, -1, -1, 101));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}
////////////////////////////////////////////////////////////////////////
@Test
public void testAddNoThrow() {
	OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
	assertTrue(tree.isEmpty());

	tree.add(newCube(1, 1, 1, 1));
	assertFalse(tree.isEmpty());

	assertEquals(1, tree.size());
	assertEquals(tree.countSize(), tree.size());
}

	@Test
	public void testAddNoThrow2() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.add(newCube(99, 99, 99, 1));
		assertFalse(tree.isEmpty());

		assertEquals(1, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow3() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		assertFalse(tree.addNoThrow(newCube(-1, -1, -1, 1)));
		assertTrue(tree.isEmpty());

		assertEquals(0, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow4() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.addNoThrow(newCube(-1, -1, -1, 1));
		assertTrue(tree.isEmpty());

		assertEquals(0, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow5() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.addNoThrow(newCube(-1, -1, -1, 50));
		assertTrue(tree.isEmpty());

		assertEquals(0, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}

	@Test
	public void testAddNoThrow6() {
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		assertTrue(tree.isEmpty());

		tree.addNoThrow(newCube(-1, -1, -1, 101));
		assertTrue(tree.isEmpty());

		assertEquals(0, tree.size());
		assertEquals(tree.countSize(), tree.size());
	}
}
