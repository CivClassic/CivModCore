package vg.civcraft.mc.civmodcore.locations.spatial;

import org.junit.Test;

import static org.junit.Assert.*;
import static vg.civcraft.mc.civmodcore.locations.spatial.octrees.Util.newCube;
import static vg.civcraft.mc.civmodcore.locations.spatial.octrees.Util.newPoint;

public class IntPoint3DTest {
	@Test
	public void testEquals() {
		assertTrue(IIntPoint2D.equals(newPoint(0, 0, 0), newPoint(0, 0, 0)));
		assertTrue(IIntPoint2D.equals(newPoint(1, 0, 0), newPoint(1, 0, 0)));
		assertTrue(IIntPoint2D.equals(newPoint(1, 1, 0), newPoint(1, 1, 0)));
		assertTrue(IIntPoint2D.equals(newPoint(1, 1, 1), newPoint(1, 1, 1)));

		assertFalse(IIntPoint2D.equals(newPoint(0, 0, 0), newPoint(0, 0, 1)));
		assertFalse(IIntPoint2D.equals(newPoint(0, 0, 0), newPoint(0, 1, 0)));
		assertFalse(IIntPoint2D.equals(newPoint(0, 0, 0), newPoint(1, 0, 0)));
	}

	@Test
	public void testDistance() {
		assertEquals(0, IIntPoint2D.distSqr(newPoint(0, 0, 0), newPoint(0, 0, 0)));

		assertEquals(1, IIntPoint2D.distSqr(newPoint(0, 0, 0), newPoint(1, 0, 0)));
		assertEquals(1, IIntPoint2D.distSqr(newPoint(0, 0, 0), newPoint(0, 1, 0)));
		assertEquals(1, IIntPoint2D.distSqr(newPoint(0, 0, 0), newPoint(0, 0, 1)));

		assertEquals(2, IIntPoint2D.distSqr(newPoint(0, 0, 0), newPoint(1, 1, 0)));
		assertEquals(2, IIntPoint2D.distSqr(newPoint(0, 0, 0), newPoint(0, 1, 1)));
		assertEquals(2, IIntPoint2D.distSqr(newPoint(0, 0, 0), newPoint(1, 0, 1)));

		assertEquals(3, IIntPoint2D.distSqr(newPoint(0, 0, 0), newPoint(1, 1, 1)));
	}

	@Test
	public void testContainsInVolume() {
		IIntBBox2D box = newCube(0, 0, 0, 100);

		assertTrue(box.contains(newPoint(0, 0, 0)));
		assertTrue(box.contains(newPoint(100, 100, 100)));

		for (int x : new int[]{0, 100}) {
			for (int y : new int[]{0, 100}) {
				for (int z : new int[]{0, 100}) {
					assertTrue(box.contains(newPoint(x, y, z)));
				}
			}
		}

		for (int x : new int[]{-1, 101}) {
			for (int y : new int[]{-1, 101}) {
				for (int z : new int[]{-1, 101}) {
					assertFalse(box.contains(newPoint(x, y, z)));
				}
			}
		}
	}
}
