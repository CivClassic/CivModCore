package vg.civcraft.mc.civmodcore.locations.spatial.quadtrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntBBox2D;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntPoint2D;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.max;
import static org.junit.Assert.assertTrue;

public class Util {

	public static <T> Predicate<T> truePredicate() {
		return v -> true;
	}

	public static <T> Set<T> difference(Set<T> left, Set<T> right) {
		Set<T> compare = new HashSet<>(left);
		compare.removeAll(right);
		return compare;
	}


	public static <T extends IIntBBox2D> Set<T> toSet(Iterator<T> it) {
		Set<T> set = new HashSet<>();

		while (it.hasNext()) {
			set.add(it.next());
		}

		return set;
	}


	public static Set<AreaQuadTreeNode<IIntBBox2D>> getNodeSet(QuadTree<IIntBBox2D> tree) {
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

	public static IIntBBox2D newCube(int minx, int miny, int size) {
		assert size > 0;
		return newBox(minx, miny, minx + size, miny + size);
	}

	public static Random getRandom() {
		return new Random(0x1337733173311337L);
	}

	public static IIntBBox2D newBox(int minx, int miny, int maxx, int maxy) {
		assertTrue(minx < maxx);
		assertTrue(miny < maxy);

		return new IIntBBox2D() {
			@Override
			public int getMinX() {
				return minx;
			}

			@Override
			public int getMinY() {
				return miny;
			}

			@Override
			public int getMaxX() {
				return maxx;
			}

			@Override
			public int getMaxY() {
				return maxy;
			}

			@Override
			public boolean equals(Object obj) {
				if (obj instanceof IIntBBox2D) {
					return IIntBBox2D.equals(this, (IIntBBox2D) obj);
				} else {
					return false;
				}
			}

			@Override
			public int hashCode() {
				return Objects.hash(minx, miny, maxx, maxy);
			}

			@Override
			public String toString() {
				return "[(" + minx + ", " + miny + ")-(" + maxx + ", " + maxy + ")]";
			}
		};
	}

	public static List<IIntBBox2D> newRandomCubes(int BOUND, int count) {
		Random rand = getRandom();

		return IntStream.range(0, count).mapToObj(i -> {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			return newCube(x, y, rand.nextInt((BOUND - 1) - max(x, y)) + 1);
		}).collect(Collectors.toList());
	}

	public static List<IIntPoint2D> clonePoint(IIntPoint2D p, int count) {
		return IntStream.range(0, count).mapToObj(i ->
				new IIntPoint2D() {
					@Override
					public int getX() {
						return p.getX();
					}

					@Override
					public int getY() {
						return p.getY();
					}
				}
		).collect(Collectors.toList());
	}

	public static List<IIntBBox2D> cloneCube(IIntBBox2D newCube, int count) {
		return IntStream.range(0, count).mapToObj(i ->
				new IIntBBox2D() {
					@Override
					public int getMinX() {
						return newCube.getMinX();
					}

					@Override
					public int getMinY() {
						return newCube.getMinY();
					}


					@Override
					public int getMaxX() {
						return newCube.getMaxX();
					}

					@Override
					public int getMaxY() {
						return newCube.getMaxY();
					}


				}
		).collect(Collectors.toList());
	}

	public static IIntPoint2D newPoint(int x, int y) {
		return new IIntPoint2D() {
			@Override
			public int getX() {
				return x;
			}

			@Override
			public int getY() {
				return y;
			}

		};
	}

	public static List<IIntPoint2D> newRandomPoints(int BOUND, int count) {
		Random rand = getRandom();

		return IntStream.range(0, count).mapToObj(i -> newPoint(rand.nextInt(BOUND), rand.nextInt(BOUND)))
				.collect(Collectors.toList());
	}
}
