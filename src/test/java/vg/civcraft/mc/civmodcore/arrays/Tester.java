package vg.civcraft.mc.civmodcore.arrays;

import org.junit.Assert;
import org.junit.Test;
import vg.civcraft.mc.civmodcore.util.Iteration;

public class Tester {

	@Test
	public void testArrayConcatination() {
		// Setup
		Integer[] FULL_ARRAY = { 1, 2, 3, 4, 5, 6 };
		Integer[] SEGMENT_ONE = { 1, 2, 3 };
		Integer[] SEGMENT_TWO = { 4, 5, 6 };
		// Process
		Integer[] CONCAT = Iteration.concat(SEGMENT_ONE, SEGMENT_TWO);
		// Check
		Assert.assertArrayEquals(FULL_ARRAY, CONCAT);
	}

}
