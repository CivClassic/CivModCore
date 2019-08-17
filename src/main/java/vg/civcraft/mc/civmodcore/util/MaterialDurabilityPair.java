package vg.civcraft.mc.civmodcore.util;

import io.protonull.utilities.libs.javax.validation.constraints.Max;
import io.protonull.utilities.libs.javax.validation.constraints.Min;
import io.protonull.utilities.libs.javax.validation.constraints.NotNull;
import org.bukkit.Material;

public final class MaterialDurabilityPair {

	private Material material;
	private short durability;

	public MaterialDurabilityPair(@NotNull Material material) {
		this.material = material;
		this.durability = 0;
	}

	public MaterialDurabilityPair(@NotNull Material material, @Min(0) short durability) {
		this.material = material;
		this.durability = durability;
	}

	public MaterialDurabilityPair(@NotNull Material material, @Min(0) @Max(Short.MAX_VALUE) int durability) {
		this.material = material;
		this.durability = (short) durability;
	}

	public Material getMaterial() {
		return this.material;
	}

	public short getDurability() {
		return this.durability;
	}

}
