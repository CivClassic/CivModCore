package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import org.junit.Test;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Integer.max;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static vg.civcraft.mc.civmodcore.locations.spatial.octrees.Util.*;

public class PredicateValueIteratorTest {
	private static Predicate<IIntBBox2D> truePredicate() {
		return iIntVolumeBBox -> true;
	}

	@Test
	public void emptyTest() {
		OcTree<IIntBBox2D> tree = new OcTree<>(newCube(0, 0, 0, 100), 32);
		PredicateValueIterator<VolumeOcTreeNode<IIntBBox2D>, IIntBBox2D> it = new PredicateValueIterator<>(tree.getRoot(), truePredicate(), truePredicate());
		assertFalse(it.hasNext());
	}

	@Test
	public void treeRootOnlyTest() {
		final int BOUND = 100;
		OcTree<IIntBBox2D> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		Set<IIntBBox2D> boxset = new HashSet<>();
		for (int i = 0; i < 100; i++) {
			int x = BOUND - 2, y = BOUND - 2, z = BOUND - 2;
			IIntBBox2D box = newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
		}

		PredicateValueIterator<VolumeOcTreeNode<IIntBBox2D>, IIntBBox2D> it = new PredicateValueIterator<>(tree.getRoot(), truePredicate(), truePredicate());
		assertFalse(it.hasNext());
	}


	@Test
	public void fullTreeTest() {
		final int BOUND = 100;
		Set<IIntBBox2D> leftSet = new HashSet<>();
		OcTree<IIntBBox2D> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 2);
			int y = rand.nextInt(BOUND - 2);
			int z = rand.nextInt(BOUND - 2);
			IIntBBox2D box = newCube(x, y, z, rand.nextInt((BOUND - 2) - max(max(x, y), z)) + 1);
			tree.add(box);
			leftSet.add(box);
		}

		PredicateValueIterator<VolumeOcTreeNode<IIntBBox2D>, IIntBBox2D> it = new PredicateValueIterator<>(tree.getRoot(), PREDICATE_TRUE, PREDICATE_TRUE);

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
	public void fullTreeTest2() {
		final int BOUND = 100;
		Set<IIntBBox2D> valueSet = new HashSet<>();
		OcTree<IIntBBox2D> tree = new OcTree<>(newCube(0, 0, 0, BOUND), 32);

		Random rand = getRandom();
		for (int i = 0; i < 1000; i++) {
			int x = rand.nextInt(BOUND - 2);
			int y = rand.nextInt(BOUND - 2);
			int z = rand.nextInt(BOUND - 2);
			IIntBBox2D box = newCube(x, y, z, rand.nextInt((BOUND - 2) - max(max(x, y), z)) + 1);
			tree.add(box);
			valueSet.add(box);
		}

		for (int i = 0; i < 100; i++) {
			int x = rand.nextInt(BOUND - 2);
			int y = rand.nextInt(BOUND - 2);
			int z = rand.nextInt(BOUND - 2);
			IIntBBox2D selectionBox = newCube(x, y, z, rand.nextInt((BOUND - 2) - max(max(x, y), z)) + 1);

			Set<IIntBBox2D> leftSet = valueSet.stream().filter(selectionBox::contains).collect(Collectors.toSet());
			PredicateValueIterator<VolumeOcTreeNode<IIntBBox2D>, IIntBBox2D> it = new PredicateValueIterator<>(tree.getRoot(), selectionBox::contains, selectionBox::intersects);

			Set<IIntBBox2D> rightSet = toSet(it);

			assertEquals(
					"Set difference: (" + leftSet.size() + "/" + rightSet.size() + ")" +
							"\nOnly left: " + difference(leftSet, rightSet) +
							"\nOnly right: " + difference(rightSet, leftSet)
					,
					leftSet,
					rightSet
			);
		}
	}
}
