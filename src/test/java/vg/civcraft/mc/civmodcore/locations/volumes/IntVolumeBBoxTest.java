package vg.civcraft.mc.civmodcore.locations.volumes;

import junit.framework.TestCase;

import java.util.Random;

import static java.lang.Integer.max;

public class IntVolumeBBoxTest extends TestCase {

	public void testTestEquals() {
		IIntVolumeBBox a = newBox(0, 0, 0, 1, 1, 1);
		IIntVolumeBBox b = newBox(0, 0, 0, 1, 1, 1);

		assertTrue(IIntVolumeBBox.equals(a, a));
		assertTrue(IIntVolumeBBox.equals(b, b));
		assertTrue(IIntVolumeBBox.equals(a, b));

		assertTrue(IIntVolumeBBox.equals(
				newBox(-2, -2, -2, -1, -1, -1),
				newBox(-2, -2, -2, -1, -1, -1)
		));

		assertTrue(IIntVolumeBBox.equals(
				newBox(-2, -2, -2, -1, -1, -1),
				newBox(-2, -2, -2, -1, -1, -1)
		));

		Random rand = getRandom();

		for (int i = 0; i < 100; i++) {
			int lmx = rand.nextInt(10000) - 5000;
			int lmy = rand.nextInt(10000) - 5000;
			int lmz = rand.nextInt(10000) - 5000;
			int lw = lmx + rand.nextInt(100) + 1;
			int lh = lmy + rand.nextInt(100) + 1;
			int ld = lmz + rand.nextInt(100) + 1;

			IIntVolumeBBox l = newBox(lmx, lmy, lmz, lw, lh, ld);
			IIntVolumeBBox r = newBox(lmx, lmy, lmz, lw, lh, ld);

			assertTrue(IIntVolumeBBox.equals(l, r));

			assertFalse(IIntVolumeBBox.equals(l, newBox(lmx, lmy, lmz, lw + 1, lh, ld)));
			assertFalse(IIntVolumeBBox.equals(l, newBox(lmx, lmy, lmz, lw, lh + 1, ld)));
			assertFalse(IIntVolumeBBox.equals(l, newBox(lmx, lmy, lmz, lw, lh, ld + 1)));

			assertFalse(IIntVolumeBBox.equals(l, newBox(lmx + 1, lmy, lmz, max(lmx + 2, lw), lh, ld)));
			assertFalse(IIntVolumeBBox.equals(l, newBox(lmx, lmy + 1, lmz, lw, max(lmy + 2, lh), ld)));
			assertFalse(IIntVolumeBBox.equals(l, newBox(lmx, lmy, lmz + 1, lw, lh, max(lmz + 2, ld))));
		}
	}

	private static Random getRandom() {
		return new Random(0x1337733173311337L);
	}

	public void testContains() {
		assertTrue(IIntVolumeBBox.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(1, 1, 1, 3, 3, 3)
		));

		assertTrue(IIntVolumeBBox.contains(
				newBox(-8, -8, -8, -4, -4, -4),
				newBox(-7, -7, -7, -5, -5, -5)
		));

		assertFalse(IIntVolumeBBox.contains(
				newBox(-8, -8, -8, -4, -4, -4),
				newBox(1, 1, 1, 3, 3, 3)
		));


		assertFalse(IIntVolumeBBox.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(1, 1, 1, 3, 3, 5)
		));

		assertTrue(IIntVolumeBBox.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(1, 1, 1, 3, 4, 3)
		));

		assertTrue(IIntVolumeBBox.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(1, 1, 1, 4, 3, 3)
		));

		assertTrue(IIntVolumeBBox.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(1, 1, 0, 3, 3, 3)
		));

		assertTrue(IIntVolumeBBox.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(1, 0, 1, 3, 3, 3)
		));

		assertTrue(IIntVolumeBBox.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(0, 1, 1, 3, 3, 3)
		));

		Random rand = getRandom();

		for (int i = 0; i < 100; i++) {
			int lmx = rand.nextInt(10000) - 5000;
			int lmy = rand.nextInt(10000) - 5000;
			int lmz = rand.nextInt(10000) - 5000;
			int lw = lmx + rand.nextInt(500) + 500;
			int lh = lmy + rand.nextInt(500) + 500;
			int ld = lmz + rand.nextInt(500) + 500;

			IIntVolumeBBox l = newBox(lmx, lmy, lmz, lw, lh, ld);
			IIntVolumeBBox r = newBox(lmx+rand.nextInt(50), lmy+rand.nextInt(50), lmz+rand.nextInt(50), lw-50, lh-50, ld-50);

			assertTrue(IIntVolumeBBox.contains(l, r));
			assertFalse(IIntVolumeBBox.contains(r, l));
		}
	}

	public void testIntersects() {
		//Scroll past on 6 axis
		final IIntVolumeBBox left = newBox(-1, -1, -1, 1, 1, 1);
		for (int i = -5; i <= 3; i++) {
			//Scroll past in X direction, top and bottom
			assertFalse(IIntVolumeBBox.intersects(left, newBox(i, -4, -1, i + 2, -2, 1)));
			assertFalse(IIntVolumeBBox.intersects(left, newBox(i, 2, -1, i + 2, 4, 1)));

			//Scroll past in X direction, left and right
			assertFalse(IIntVolumeBBox.intersects(left, newBox(i, -1, -4, i + 2, 1, -2)));
			assertFalse(IIntVolumeBBox.intersects(left, newBox(i, -1, 2, i + 2, 1, 4)));

			assertFalse(IIntVolumeBBox.intersects(left, newBox(-4, i, -1, -2, i + 2, 1)));
			assertFalse(IIntVolumeBBox.intersects(left, newBox(2, i, -1, 4, i + 2, 1)));

			assertFalse(IIntVolumeBBox.intersects(left, newBox(-1, i, -4, 1, i + 2, -2)));
			assertFalse(IIntVolumeBBox.intersects(left, newBox(-1, i, 2, 1, i + 2, 4)));

			assertFalse(IIntVolumeBBox.intersects(left, newBox(-4, -1, i, -2, 1, i + 2)));
			assertFalse(IIntVolumeBBox.intersects(left, newBox(2, -1, i, 4, 1, i + 2)));

			assertFalse(IIntVolumeBBox.intersects(left, newBox(-1, -4, i, 1, -2, i + 2)));
			assertFalse(IIntVolumeBBox.intersects(left, newBox(-1, 2, i, 1, 4, i + 2)));
		}
	}

	private static IIntVolumeBBox newBox(int minx, int miny, int minz, int maxx, int maxy, int maxz) {
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
		};
	}
}
