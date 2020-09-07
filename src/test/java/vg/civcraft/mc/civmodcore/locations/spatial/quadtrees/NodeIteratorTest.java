package vg.civcraft.mc.civmodcore.locations.spatial.quadtrees;

import org.junit.Test;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;

import java.util.Random;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;
import static vg.civcraft.mc.civmodcore.locations.spatial.quadtrees.Util.*;

public class NodeIteratorTest {
	@Test
	public void testEmptyTree() {
		QuadTree<IIntBBox2D> box = new QuadTree<>(newCube(0, 0, 100), 4);
		NodeIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new NodeIterator<>(box.getRoot());

		assertTrue(it.hasNext());
		assertNotNull(it.next());
	}

	@Test
	public void testSimpleTree() {
		QuadTree<IIntBBox2D> box = new QuadTree<>(newCube(0, 0, 100), 4);

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				box.add(newCube(x, y, 1));
			}
		}

		NodeIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new NodeIterator<>(box.getRoot());

		assertTrue(it.hasNext());
		assertNotNull(it.next());
		long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new NodeIterator<>(box.getRoot()), Spliterator.IMMUTABLE), false).count();

		assertEquals(box.countNodes(), count);
	}

	@Test
	public void testSimpleTree2() {
		QuadTree<IIntBBox2D> box = new QuadTree<>(newCube(0, 0, 100), 32);

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				box.add(newCube(x, y, 1));
			}
		}

		NodeIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new NodeIterator<>(box.getRoot());

		assertTrue(it.hasNext());
		assertNotNull(it.next());
		long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new NodeIterator<>(box.getRoot()), Spliterator.IMMUTABLE), false).count();

		assertEquals(box.countNodes(), count);
	}

	@Test
	public void testSimpleTree3() {
		final int BOUNDS = 1000;
		QuadTree<IIntBBox2D> tree = new QuadTree<>(newCube(0, 0, 1000), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int offset = rand.nextInt(BOUNDS - 2);
			tree.add(newCube(offset, offset, rand.nextInt(BOUNDS - offset - 2) + 1));
		}
		NodeIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new NodeIterator<>(tree.getRoot());

		Set<AreaQuadTreeNode<IIntBBox2D>> itset = toSet(it);

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
