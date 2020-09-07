package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import org.junit.Test;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox3D;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.Integer.max;
import static org.junit.Assert.*;
import static vg.civcraft.mc.civmodcore.locations.spatial.octrees.Util.*;

public class PredicateNodeIteratorTest {
	private final static Predicate<IIntBBox3D> PREDICATE_TRUE = value -> true;

	@Test
	public void testEmptyTree() {
		OcTree<IIntBBox3D> box = new OcTree<>(newCube(0, 0, 0, 100), 4);
		PredicateNodeIterator<VolumeOcTreeNode<IIntBBox3D>, IIntBBox3D> it = new PredicateNodeIterator<>(box.getRoot(), PREDICATE_TRUE);

		assertTrue(it.hasNext());
		assertNotNull(it.next());
	}

	@Test
	public void testSimpleTree() {
		OcTree<IIntBBox3D> box = new OcTree<>(newCube(0, 0, 0, 100), 4);

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				for (int z = 0; z < 10; z++) {
					box.add(newCube(x, y, z, 1));
				}
			}
		}

		NodeIterator<VolumeOcTreeNode<IIntBBox3D>, IIntBBox3D> it = new NodeIterator<>(box.getRoot());

		assertTrue(it.hasNext());
		assertNotNull(it.next());
		long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new PredicateNodeIterator<>(box.getRoot(), PREDICATE_TRUE), Spliterator.IMMUTABLE), false).count();

		assertEquals(box.countNodes(), count);
	}

	@Test
	public void testSimpleTree2() {
		OcTree<IIntBBox3D> box = new OcTree<>(newCube(0, 0, 0, 100), 32);

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				for (int z = 0; z < 10; z++) {
					box.add(newCube(x, y, z, 1));
				}
			}
		}

		PredicateNodeIterator<VolumeOcTreeNode<IIntBBox3D>, IIntBBox3D> it = new PredicateNodeIterator<>(box.getRoot(), PREDICATE_TRUE);

		assertTrue(it.hasNext());
		assertNotNull(it.next());
		long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new PredicateNodeIterator<>(box.getRoot(), PREDICATE_TRUE), Spliterator.IMMUTABLE), false).count();

		assertEquals(box.countNodes(), count);
	}

	@Test
	public void predicateTest() {
		final int BOUND = 100;
		OcTree<IIntBBox3D> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox3D box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		final IIntBBox3D SELECTION_CUBE = newCube(50, 50, 50, BOUND - 50);
		Predicate<IIntBBox3D> pred = value -> SELECTION_CUBE.contains(value);

		Set<VolumeOcTreeNode<IIntBBox3D>> nodeSet = getNodeSet(tree).parallelStream().filter(pred).collect(Collectors.toSet());

		Set<VolumeOcTreeNode<IIntBBox3D>> iteratorNodeSet = new HashSet<>();
		PredicateNodeIterator<VolumeOcTreeNode<IIntBBox3D>, IIntBBox3D> it = new PredicateNodeIterator<>(tree.getRoot(), pred);
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
		OcTree<IIntBBox3D> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox3D box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		final IIntBBox3D SELECTION_CUBE = newCube(25, 25, 25, 10);
		Predicate<IIntBBox3D> pred = value -> SELECTION_CUBE.contains(value);

		Set<VolumeOcTreeNode<IIntBBox3D>> nodeSet = getNodeSet(tree).parallelStream().filter(pred).collect(Collectors.toSet());

		Set<VolumeOcTreeNode<IIntBBox3D>> iteratorNodeSet = new HashSet<>();
		PredicateNodeIterator<VolumeOcTreeNode<IIntBBox3D>, IIntBBox3D> it = new PredicateNodeIterator<>(tree.getRoot(), pred);
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
		OcTree<IIntBBox3D> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox3D box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		final IIntBBox3D SELECTION_CUBE = newCube(25, 25, 25, 10);
		Predicate<IIntBBox3D> pred = value -> SELECTION_CUBE.intersects(value);

		Set<VolumeOcTreeNode<IIntBBox3D>> nodeSet = getNodeSet(tree).parallelStream().filter(pred).collect(Collectors.toSet());

		Set<VolumeOcTreeNode<IIntBBox3D>> iteratorNodeSet = new HashSet<>();
		PredicateNodeIterator<VolumeOcTreeNode<IIntBBox3D>, IIntBBox3D> it = new PredicateNodeIterator<>(tree.getRoot(), pred);
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
		OcTree<IIntBBox3D> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox3D box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		Set<VolumeOcTreeNode<IIntBBox3D>> nodeSet = getNodeSet(tree);
		Set<VolumeOcTreeNode<IIntBBox3D>> iteratorNodeSet = new HashSet<>();
		PredicateNodeIterator<VolumeOcTreeNode<IIntBBox3D>, IIntBBox3D> it = new PredicateNodeIterator<>(tree.getRoot(), PREDICATE_TRUE);
		while (it.hasNext()) {
			iteratorNodeSet.add(it.next());
		}

		assertEquals(nodeSet, iteratorNodeSet);
	}

	private static Set<VolumeOcTreeNode<IIntBBox3D>> getNodeSet(OcTree<IIntBBox3D> tree) {
		Set<VolumeOcTreeNode<IIntBBox3D>> nodeSet = new HashSet<>();
		LinkedList<VolumeOcTreeNode<IIntBBox3D>> stack = new LinkedList<>();
		stack.add(tree.getRoot());

		while (!stack.isEmpty()) {
			VolumeOcTreeNode<IIntBBox3D> node = stack.pop();
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
		OcTree<IIntBBox3D> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			IIntBBox3D box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
			tree.add(box);
		}

		assertEquals(1000, tree.countSize());

		for (int i = 0; i < 100; i++) {
			int x = rand.nextInt(BOUND - 2), y = rand.nextInt(BOUND - 2), z = rand.nextInt(BOUND - 2);
			IIntBBox3D box = newCube(x, y, z, rand.nextInt(BOUND - 2 - max(max(x, y), z)) + 1);

			Predicate<IIntBBox3D> pred = box::intersects;
			Set<VolumeOcTreeNode<IIntBBox3D>> nodeSet = getNodeSet(tree).stream().filter(pred).collect(Collectors.toSet());
			PredicateNodeIterator<VolumeOcTreeNode<IIntBBox3D>, IIntBBox3D> it = new PredicateNodeIterator<>(tree.getRoot(), pred);
			Set<VolumeOcTreeNode<IIntBBox3D>> iteratorNodeSet = toSet(it);

			assertEquals(nodeSet, iteratorNodeSet);
		}
	}
}
