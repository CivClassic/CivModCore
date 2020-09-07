package vg.civcraft.mc.civmodcore.locations.spatial.octrees;

import vg.civcraft.mc.civmodcore.locations.spatial.IIntPoint3D;
import vg.civcraft.mc.civmodcore.locations.spatial.IIntVolumeBBox;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.max;
import static org.junit.Assert.assertTrue;

public class Util {
	public final static Predicate<IIntVolumeBBox> PREDICATE_TRUE = value -> true;
	public final static Predicate<IIntVolumeBBox> PREDICATE_FALSE = value -> false;

	public static <T> Set<T> difference(Set<T> left, Set<T> right) {
		Set<T> compare = new HashSet<>(left);
		compare.removeAll(right);
		return compare;
	}


	public static <T extends IIntVolumeBBox> Set<T> toSet(Iterator<T> it) {
		Set<T> set = new HashSet<>();

		while (it.hasNext()) {
			set.add(it.next());
		}

		return set;
	}


	public static Set<VolumeOcTreeNode<IIntVolumeBBox>> getNodeSet(OcTree<IIntVolumeBBox> tree) {
		Set<VolumeOcTreeNode<IIntVolumeBBox>> nodeSet = new HashSet<>();
		LinkedList<VolumeOcTreeNode<IIntVolumeBBox>> stack = new LinkedList<>();
		stack.add(tree.getRoot());

		while (!stack.isEmpty()) {
			VolumeOcTreeNode<IIntVolumeBBox> node = stack.pop();
			nodeSet.add(node);
			if (node.hasChildren()) {
				stack.addAll(node.getChildren());
			}
		}

		return nodeSet;
	}

	public static IIntVolumeBBox newCube(int minx, int miny, int minz, int size) {
		assert size > 0;
		return newBox(minx, miny, minz, minx + size, miny + size, minz + size);
	}

	public static Random getRandom() {
		return new Random(0x1337733173311337L);
	}

	public static IIntVolumeBBox newBox(int minx, int miny, int minz, int maxx, int maxy, int maxz) {
		assertTrue(minx < maxx);
		assertTrue(miny < maxy);
		assertTrue(minz < maxz);

		return new IIntVolumeBBox() {
			@Override
			public int getMinX() {
				return minx;
			}

			@Override
			public int getMinY() {
				return miny;
			}

			@Override
			public int getMinZ() {
				return minz;
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
			public int getMaxZ() {
				return maxz;
			}

			@Override
			public boolean equals(Object obj) {
				if (obj instanceof IIntVolumeBBox) {
					return IIntVolumeBBox.equals(this, (IIntVolumeBBox) obj);
				} else {
					return false;
				}
			}

			@Override
			public int hashCode() {
				return Objects.hash(minx, miny, minz, maxx, maxy, maxz);
			}

			@Override
			public String toString() {
				return "[(" + minx + ", " + miny + ", " + minz + ")-(" + maxx + ", " + maxy + ", " + maxz + ")]";
			}
		};
	}

	public static List<IIntVolumeBBox> newRandomCubes(int BOUND, int count) {
		Random rand = getRandom();

		return IntStream.range(0, count).mapToObj(i -> {
			int x = rand.nextInt(BOUND - 1);
			int y = rand.nextInt(BOUND - 1);
			int z = rand.nextInt(BOUND - 1);
			return newCube(x, y, z, rand.nextInt((BOUND - 1) - max(max(x, y), z)) + 1);
		}).collect(Collectors.toList());
	}

	public static List<IIntPoint3D> clonePoint(IIntPoint3D p, int count) {
		return IntStream.range(0, count).mapToObj(i ->
				new IIntPoint3D() {
					@Override
					public int getX() {
						return p.getX();
					}

					@Override
					public int getY() {
						return p.getY();
					}

					@Override
					public int getZ() {
						return p.getZ();
					}
				}
		).collect(Collectors.toList());
	}

	public static List<IIntVolumeBBox> cloneCube(IIntVolumeBBox newCube, int count) {
		return IntStream.range(0, count).mapToObj(i ->
				new IIntVolumeBBox() {
					@Override
					public int getMinX() {
						return newCube.getMinX();
					}

					@Override
					public int getMinY() {
						return newCube.getMinY();
					}

					@Override
					public int getMinZ() {
						return newCube.getMinZ();
					}

					@Override
					public int getMaxX() {
						return newCube.getMaxX();
					}

					@Override
					public int getMaxY() {
						return newCube.getMaxY();
					}

					@Override
					public int getMaxZ() {
						return newCube.getMaxZ();
					}
				}
		).collect(Collectors.toList());
	}

	public static IIntPoint3D newPoint(int x, int y, int z) {
		return new IIntPoint3D() {
			@Override
			public int getX() {
				return x;
			}

			@Override
			public int getY() {
				return y;
			}

			@Override
			public int getZ() {
				return z;
			}
		};
	}

	public static List<IIntPoint3D> newRandomPoints(int BOUND, int count) {
		Random rand = getRandom();

		return IntStream.range(0, count).mapToObj(i -> newPoint(rand.nextInt(BOUND), rand.nextInt(BOUND), rand.nextInt(BOUND)))
				.collect(Collectors.toList());
	}
}
