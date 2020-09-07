package vg.civcraft.mc.civmodcore.locations.spatial.quadtrees;

import org.junit.Test;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.Integer.max;
import static org.junit.Assert.*;
import static vg.civcraft.mc.civmodcore.locations.spatial.quadtrees.Util.*;

public class PredicateNodeIteratorTest {

	@Test
	public void testEmptyTree() {
		QuadTree<IIntBBox2D> box = new QuadTree<>(newCube(0, 0, 100), 4);
		PredicateNodeIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new PredicateNodeIterator<>(box.getRoot(), truePredicate());

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
		long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new PredicateNodeIterator<>(box.getRoot(), truePredicate()), Spliterator.IMMUTABLE), false).count();

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

		PredicateNodeIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new PredicateNodeIterator<>(box.getRoot(), truePredicate());

		assertTrue(it.hasNext());
		assertNotNull(it.next());
		long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new PredicateNodeIterator<>(box.getRoot(), truePredicate()), Spliterator.IMMUTABLE), false).count();

		assertEquals(box.countNodes(), count);
	}

	@Test
	public void predicateTest() {
		final int BOUND = 100;
		QuadTree<IIntBBox2D> tree = new QuadTree<>(newCube(0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox2D box = newCube(x, y, rand.nextInt((BOUND - 1) - max(x, y)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		final IIntBBox2D SELECTION_CUBE = newCube(50, 50, BOUND - 50);
		Predicate<IIntBBox2D> pred = value -> SELECTION_CUBE.contains(value);

		Set<AreaQuadTreeNode<IIntBBox2D>> nodeSet = getNodeSet(tree).parallelStream().filter(pred).collect(Collectors.toSet());

		Set<AreaQuadTreeNode<IIntBBox2D>> iteratorNodeSet = new HashSet<>();
		PredicateNodeIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new PredicateNodeIterator<>(tree.getRoot(), pred);
		while (it.hasNext()) {
			iteratorNodeSet.add(it.next());
		}

//		assertEquals(nodeSet.size(), iteratorNodeSet.size());
		assertEquals(
				"Set difference:" +
						"\nOnly left: " + difference(nodeSet, iteratorNodeSet) +
						"\nOnly right: " + difference(iteratorNodeSet, nodeSet)
				,
				nodeSet, iteratorNodeSet);
	}

	@Test
	public void predicateTest2() {
		final int BOUND = 100;
		QuadTree<IIntBBox2D> tree = new QuadTree<>(newCube(0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox2D box = newCube(x, y, rand.nextInt((BOUND - 1) - max(x, y)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		final IIntBBox2D SELECTION_CUBE = newCube(25, 25, 10);
		Predicate<IIntBBox2D> pred = value -> SELECTION_CUBE.contains(value);

		Set<AreaQuadTreeNode<IIntBBox2D>> nodeSet = getNodeSet(tree).parallelStream().filter(pred).collect(Collectors.toSet());

		Set<AreaQuadTreeNode<IIntBBox2D>> iteratorNodeSet = new HashSet<>();
		PredicateNodeIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new PredicateNodeIterator<>(tree.getRoot(), pred);
		while (it.hasNext()) {
			iteratorNodeSet.add(it.next());
		}

//		assertEquals(nodeSet.size(), iteratorNodeSet.size());
		assertEquals(
				"Set difference:" +
						"\nOnly left: " + difference(nodeSet, iteratorNodeSet) +
						"\nOnly right: " + difference(iteratorNodeSet, nodeSet)
				,
				nodeSet, iteratorNodeSet);
	}

	@Test
	public void predicateTest3() {
		final int BOUND = 100;
		QuadTree<IIntBBox2D> tree = new QuadTree<>(newCube(0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox2D box = newCube(x, y, rand.nextInt((BOUND - 1) - max(x, y)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		final IIntBBox2D SELECTION_CUBE = newCube(25, 25, 10);
		Predicate<IIntBBox2D> pred = value -> SELECTION_CUBE.intersects(value);

		Set<AreaQuadTreeNode<IIntBBox2D>> nodeSet = getNodeSet(tree).parallelStream().filter(pred).collect(Collectors.toSet());

		Set<AreaQuadTreeNode<IIntBBox2D>> iteratorNodeSet = new HashSet<>();
		PredicateNodeIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new PredicateNodeIterator<>(tree.getRoot(), pred);
		while (it.hasNext()) {
			iteratorNodeSet.add(it.next());
		}

//		assertEquals(nodeSet.size(), iteratorNodeSet.size());
		assertEquals(
				"Set difference:" +
						"\nOnly left: " + difference(nodeSet, iteratorNodeSet) +
						"\nOnly right: " + difference(iteratorNodeSet, nodeSet)
				,
				nodeSet, iteratorNodeSet);
	}

	@Test
	public void predicateTrueTest() {
		final int BOUND = 100;
		QuadTree<IIntBBox2D> tree = new QuadTree<>(newCube(0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox2D box = newCube(x, y, rand.nextInt((BOUND - 1) - max(x, y)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		Set<AreaQuadTreeNode<IIntBBox2D>> nodeSet = getNodeSet(tree);
		Set<AreaQuadTreeNode<IIntBBox2D>> iteratorNodeSet = new HashSet<>();
		PredicateNodeIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new PredicateNodeIterator<>(tree.getRoot(), truePredicate());
		while (it.hasNext()) {
			iteratorNodeSet.add(it.next());
		}

		assertEquals(nodeSet, iteratorNodeSet);
	}

	private static Set<AreaQuadTreeNode<IIntBBox2D>> getNodeSet(QuadTree<IIntBBox2D> tree) {
		Set<AreaQuadTreeNode<IIntBBox2D>> nodeSet = new HashSet<>();
		LinkedList<AreaQuadTreeNode<IIntBBox2D>> stack = new LinkedList<>();
		stack.add(tree.getRoot());

		while (!stack.isEmpty()) {
			AreaQuadTreeNode<IIntBBox2D> node = stack.pop();
			nodeSet.add(node);
			if (node.hasChildren()) {
				stack.addAll(node.getChildren());
			}
		}

		return nodeSet;
	}

	@Test
	public void predicateTestRandomIntersectWithNode() {
		final int BOUND = 100;
		QuadTree<IIntBBox2D> tree = new QuadTree<>(newCube(0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox2D box = newCube(x, y, rand.nextInt((BOUND - 1) - max(x, y)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		for (int i = 0; i < 100; i++) {
			int x = rand.nextInt(BOUND - 2), y = rand.nextInt(BOUND - 2), z = rand.nextInt(BOUND - 2);
			IIntBBox2D box = newCube(x, y, rand.nextInt(BOUND - 2 - max(max(x, y), z)) + 1);

			Predicate<IIntBBox2D> pred = box::intersects;
			Set<AreaQuadTreeNode<IIntBBox2D>> nodeSet = getNodeSet(tree).stream().filter(pred).collect(Collectors.toSet());
			PredicateNodeIterator<AreaQuadTreeNode<IIntBBox2D>, IIntBBox2D> it = new PredicateNodeIterator<>(tree.getRoot(), pred);
			Set<AreaQuadTreeNode<IIntBBox2D>> iteratorNodeSet = toSet(it);

			assertEquals(nodeSet, iteratorNodeSet);
		}
	}
}
