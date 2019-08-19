package vg.civcraft.mc.civmodcore.api;

import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;
import org.bukkit.material.NetherWarts;

public final class CropAPI {
	private CropAPI() {} // Make the class effectively static

	/**
	 * Determines whether a something is a crop.
	 * @see BlockState#getData()
	 * @see ItemStack#getData()
	 * */
	public static boolean isCrop(MaterialData data) {
		if (data instanceof Crops) {
			return true;
		}
		if (data instanceof NetherWarts) {
			return true;
		}
		return false;
	}

	/**
	 * Determines the growth state of a crop as a normalised value.
	 * @return Returns the growth state or zero if the data is not of a crop.
	 * @see BlockState#getData()
	 * @see ItemStack#getData()
	 * */
	public static double getGrowthState(MaterialData data) {
		if (data instanceof Crops) {
			switch (((Crops)data).getState()) {
				case SEEDED:
					return 0.0d;
				case GERMINATED:
					return 0.14285714285714285;
				case VERY_SMALL:
					return 0.2857142857142857;
				case SMALL:
					return 0.42857142857142855;
				case MEDIUM:
					return 0.5714285714285714;
				case TALL:
					return 0.7142857142857142;
				case VERY_TALL:
					return 0.8571428571428571;
				case RIPE:
					return 1.0d;
			}
		}
		if (data instanceof NetherWarts) {
			switch (((NetherWarts)data).getState()) {
				case SEEDED:
					return 0.0d;
				case STAGE_ONE:
					return 0.3333333333333333;
				case STAGE_TWO:
					return 0.6666666666666666;
				case RIPE:
					return 1.0d;
			}
		}
		return 0.0d;
	}

	/**
	 * Determines whether a crop is ripe.
	 * @see BlockState#getData()
	 * @see ItemStack#getData()
	 * */
	public static boolean isCropRipe(MaterialData data) {
		return getGrowthState(data) == 1;
	}

}
