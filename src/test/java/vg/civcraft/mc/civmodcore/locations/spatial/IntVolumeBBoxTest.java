package vg.civcraft.mc.civmodcore.locations.spatial;

import org.junit.Test;

import java.util.Random;

import static java.lang.Integer.max;
import static org.junit.Assert.*;
import static vg.civcraft.mc.civmodcore.locations.spatial.octrees.Util.getRandom;
import static vg.civcraft.mc.civmodcore.locations.spatial.octrees.Util.newBox;

public class IntVolumeBBoxTest  {

	@Test
	public void testTestEquals() {
		IIntBBox2D a = newBox(0, 0, 0, 1, 1, 1);
		IIntBBox2D b = newBox(0, 0, 0, 1, 1, 1);

		assertTrue(IIntBBox2D.equals(a, a));
		assertTrue(IIntBBox2D.equals(b, b));
		assertTrue(IIntBBox2D.equals(a, b));

		assertTrue(IIntBBox2D.equals(
				newBox(-2, -2, -2, -1, -1, -1),
				newBox(-2, -2, -2, -1, -1, -1)
		));

		assertTrue(IIntBBox2D.equals(
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

			IIntBBox2D l = newBox(lmx, lmy, lmz, lw, lh, ld);
			IIntBBox2D r = newBox(lmx, lmy, lmz, lw, lh, ld);

			assertTrue(IIntBBox2D.equals(l, r));

			assertFalse(IIntBBox2D.equals(l, newBox(lmx, lmy, lmz, lw + 1, lh, ld)));
			assertFalse(IIntBBox2D.equals(l, newBox(lmx, lmy, lmz, lw, lh + 1, ld)));
			assertFalse(IIntBBox2D.equals(l, newBox(lmx, lmy, lmz, lw, lh, ld + 1)));

			assertFalse(IIntBBox2D.equals(l, newBox(lmx + 1, lmy, lmz, max(lmx + 2, lw), lh, ld)));
			assertFalse(IIntBBox2D.equals(l, newBox(lmx, lmy + 1, lmz, lw, max(lmy + 2, lh), ld)));
			assertFalse(IIntBBox2D.equals(l, newBox(lmx, lmy, lmz + 1, lw, lh, max(lmz + 2, ld))));
		}
	}

	@Test
	public void testContains() {
		assertTrue(IIntBBox2D.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(1, 1, 1, 3, 3, 3)
		));

		assertTrue(IIntBBox2D.contains(
				newBox(-8, -8, -8, -4, -4, -4),
				newBox(-7, -7, -7, -5, -5, -5)
		));

		assertFalse(IIntBBox2D.contains(
				newBox(-8, -8, -8, -4, -4, -4),
				newBox(1, 1, 1, 3, 3, 3)
		));


		assertFalse(IIntBBox2D.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(1, 1, 1, 3, 3, 5)
		));

		assertTrue(IIntBBox2D.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(1, 1, 1, 3, 4, 3)
		));

		assertTrue(IIntBBox2D.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(1, 1, 1, 4, 3, 3)
		));

		assertTrue(IIntBBox2D.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(1, 1, 0, 3, 3, 3)
		));

		assertTrue(IIntBBox2D.contains(
				newBox(0, 0, 0, 4, 4, 4),
				newBox(1, 0, 1, 3, 3, 3)
		));

		assertTrue(IIntBBox2D.contains(
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

			IIntBBox2D l = newBox(lmx, lmy, lmz, lw, lh, ld);
			IIntBBox2D r = newBox(lmx+rand.nextInt(50), lmy+rand.nextInt(50), lmz+rand.nextInt(50), lw-50, lh-50, ld-50);

			assertTrue(IIntBBox2D.contains(l, r));
			assertFalse(IIntBBox2D.contains(r, l));
		}
	}

	@Test
	public void testIntersects() {
		//Scroll past on 6 axis
		final IIntBBox2D left = newBox(-1, -1, -1, 1, 1, 1);
		for (int i = -5; i <= 3; i++) {
			//Scroll past in X direction, top and bottom
			assertFalse(IIntBBox2D.intersects(left, newBox(i, -4, -1, i + 2, -2, 1)));
			assertFalse(IIntBBox2D.intersects(left, newBox(i, 2, -1, i + 2, 4, 1)));

			//Scroll past in X direction, left and right
			assertFalse(IIntBBox2D.intersects(left, newBox(i, -1, -4, i + 2, 1, -2)));
			assertFalse(IIntBBox2D.intersects(left, newBox(i, -1, 2, i + 2, 1, 4)));

			assertFalse(IIntBBox2D.intersects(left, newBox(-4, i, -1, -2, i + 2, 1)));
			assertFalse(IIntBBox2D.intersects(left, newBox(2, i, -1, 4, i + 2, 1)));

			assertFalse(IIntBBox2D.intersects(left, newBox(-1, i, -4, 1, i + 2, -2)));
			assertFalse(IIntBBox2D.intersects(left, newBox(-1, i, 2, 1, i + 2, 4)));

			assertFalse(IIntBBox2D.intersects(left, newBox(-4, -1, i, -2, 1, i + 2)));
			assertFalse(IIntBBox2D.intersects(left, newBox(2, -1, i, 4, 1, i + 2)));

			assertFalse(IIntBBox2D.intersects(left, newBox(-1, -4, i, 1, -2, i + 2)));
			assertFalse(IIntBBox2D.intersects(left, newBox(-1, 2, i, 1, 4, i + 2)));
		}
	}
}
