package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import org.junit.Test;
import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;

import java.util.Random;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;
import static vg.civcraft.mc.civmodcore.locations.volumes.octrees.Util.*;

public class NodeIteratorTest {
	@Test
	public void testEmptyTree() {
		OcTree<IIntVolumeBBox> box = new OcTree<>(newCube(0, 0, 0, 100), 4);
		NodeIterator<IIntVolumeBBox> it = new NodeIterator<>(box.getRoot());

		assertTrue(it.hasNext());
		assertNotNull(it.next());
	}

	@Test
	public void testSimpleTree() {
		OcTree<IIntVolumeBBox> box = new OcTree<>(newCube(0, 0, 0, 100), 4);

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				for (int z = 0; z < 10; z++) {
					box.add(newCube(x, y, z, 1));
				}
			}
		}

		NodeIterator<IIntVolumeBBox> it = new NodeIterator<>(box.getRoot());

		assertTrue(it.hasNext());
		assertNotNull(it.next());
		long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new NodeIterator<>(box.getRoot()), Spliterator.IMMUTABLE), false).count();

		assertEquals(box.countNodes(), count);
	}

	@Test
	public void testSimpleTree2() {
		OcTree<IIntVolumeBBox> box = new OcTree<>(newCube(0, 0, 0, 100), 32);

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				for (int z = 0; z < 10; z++) {
					box.add(newCube(x, y, z, 1));
				}
			}
		}

		NodeIterator<IIntVolumeBBox> it = new NodeIterator<>(box.getRoot());

		assertTrue(it.hasNext());
		assertNotNull(it.next());
		long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new NodeIterator<>(box.getRoot()), Spliterator.IMMUTABLE), false).count();

		assertEquals(box.countNodes(), count);
	}

	@Test
	public void testSimpleTree3() {
		final int BOUNDS = 1000;
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, 1000), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int offset = rand.nextInt(BOUNDS - 2);
			tree.add(newCube(offset, offset, offset, rand.nextInt(BOUNDS - offset - 2) + 1));
		}
		NodeIterator<IIntVolumeBBox> it = new NodeIterator<>(tree.getRoot());
		Set<OcTreeNode<IIntVolumeBBox>> itset = toSet(it);

		assertEquals(
				"Set difference:" +
						"\nOnly left: " + difference(getNodeSet(tree), itset) +
						"\nOnly right: " + difference(itset, getNodeSet(tree))
				,
				getNodeSet(tree),
				itset
		);
	}
}
