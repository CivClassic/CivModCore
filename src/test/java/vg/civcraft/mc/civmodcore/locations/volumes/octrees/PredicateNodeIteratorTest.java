package vg.civcraft.mc.civmodcore.locations.volumes.octrees;

import org.junit.Test;
import vg.civcraft.mc.civmodcore.locations.volumes.IIntVolumeBBox;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.Integer.max;
import static org.junit.Assert.*;
import static vg.civcraft.mc.civmodcore.locations.volumes.octrees.Util.*;

public class PredicateNodeIteratorTest {
	private final static Predicate<IIntVolumeBBox> PREDICATE_TRUE = value -> true;

	@Test
	public void testEmptyTree() {
		OcTree<IIntVolumeBBox> box = new OcTree<>(newCube(0, 0, 0, 100), 4);
		PredicateNodeIterator<IIntVolumeBBox> it = new PredicateNodeIterator<>(box.getRoot(), PREDICATE_TRUE);

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
		long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new PredicateNodeIterator<>(box.getRoot(), PREDICATE_TRUE), Spliterator.IMMUTABLE), false).count();

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

		PredicateNodeIterator<IIntVolumeBBox> it = new PredicateNodeIterator<>(box.getRoot(), PREDICATE_TRUE);

		assertTrue(it.hasNext());
		assertNotNull(it.next());
		long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new PredicateNodeIterator<>(box.getRoot(), PREDICATE_TRUE), Spliterator.IMMUTABLE), false).count();

		assertEquals(box.countNodes(), count);
	}

	@Test
	public void predicateTest() {
		final int BOUND = 100;
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntVolumeBBox box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		final IIntVolumeBBox SELECTION_CUBE = newCube(50, 50, 50, BOUND - 50);
		Predicate<IIntVolumeBBox> pred = value -> SELECTION_CUBE.contains(value);

		Set<OcTreeNode<IIntVolumeBBox>> nodeSet = getNodeSet(tree).parallelStream().filter(pred).collect(Collectors.toSet());

		Set<OcTreeNode<IIntVolumeBBox>> iteratorNodeSet = new HashSet<>();
		PredicateNodeIterator<IIntVolumeBBox> it = new PredicateNodeIterator<>(tree.getRoot(), pred);
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
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntVolumeBBox box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		final IIntVolumeBBox SELECTION_CUBE = newCube(25, 25, 25, 10);
		Predicate<IIntVolumeBBox> pred = value -> SELECTION_CUBE.contains(value);

		Set<OcTreeNode<IIntVolumeBBox>> nodeSet = getNodeSet(tree).parallelStream().filter(pred).collect(Collectors.toSet());

		Set<OcTreeNode<IIntVolumeBBox>> iteratorNodeSet = new HashSet<>();
		PredicateNodeIterator<IIntVolumeBBox> it = new PredicateNodeIterator<>(tree.getRoot(), pred);
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
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntVolumeBBox box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		final IIntVolumeBBox SELECTION_CUBE = newCube(25, 25, 25, 10);
		Predicate<IIntVolumeBBox> pred = value -> SELECTION_CUBE.intersects(value);

		Set<OcTreeNode<IIntVolumeBBox>> nodeSet = getNodeSet(tree).parallelStream().filter(pred).collect(Collectors.toSet());

		Set<OcTreeNode<IIntVolumeBBox>> iteratorNodeSet = new HashSet<>();
		PredicateNodeIterator<IIntVolumeBBox> it = new PredicateNodeIterator<>(tree.getRoot(), pred);
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
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntVolumeBBox box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		Set<OcTreeNode<IIntVolumeBBox>> nodeSet = getNodeSet(tree);
		Set<OcTreeNode<IIntVolumeBBox>> iteratorNodeSet = new HashSet<>();
		PredicateNodeIterator<IIntVolumeBBox> it = new PredicateNodeIterator<>(tree.getRoot(), PREDICATE_TRUE);
		while (it.hasNext()) {
			iteratorNodeSet.add(it.next());
		}

		assertEquals(nodeSet, iteratorNodeSet);
	}

	private static Set<OcTreeNode<IIntVolumeBBox>> getNodeSet(OcTree<IIntVolumeBBox> tree) {
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

	@Test
	public void predicateTestRandomIntersectWithNode() {
		final int BOUND = 100;
		OcTree<IIntVolumeBBox> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntVolumeBBox box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		for (int i = 0; i < 100; i++) {
			int x = rand.nextInt(BOUND - 2), y = rand.nextInt(BOUND - 2), z = rand.nextInt(BOUND - 2);
			IIntVolumeBBox box = newCube(x, y, z, rand.nextInt(BOUND - 2 - max(max(x, y), z)) + 1);

			Predicate<IIntVolumeBBox> pred = box::intersects;
			Set<OcTreeNode<IIntVolumeBBox>> nodeSet = getNodeSet(tree).stream().filter(pred).collect(Collectors.toSet());
			PredicateNodeIterator<IIntVolumeBBox> it = new PredicateNodeIterator<>(tree.getRoot(), pred);
			Set<OcTreeNode<IIntVolumeBBox>> iteratorNodeSet = toSet(it);

			assertEquals(nodeSet, iteratorNodeSet);
		}
	}
}
