package vg.civcraft.mc.civmodcore.custom.items;

import com.google.common.base.Preconditions;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.api.MaterialAPI;
import vg.civcraft.mc.civmodcore.util.EnumUtils;
import vg.civcraft.mc.civmodcore.util.MapUtils;

public class MaterialCriteria extends ItemCriteria {

	public static final String MATERIAL_KEY = "material";

	private final Material material;

	public MaterialCriteria(final Material material) {
		Preconditions.checkArgument(MaterialAPI.isValidItemMaterial(material),
				"MaterialCriteria for CustomItem cannot use a non-item material!");
		this.material = material;
	}

	@Override
	public boolean matches(final CustomItem custom, final ItemStack item) {
		return item.getType() == this.material;
	}

	@Override
	public ItemStack applyToItem(final CustomItem custom, final ItemStack item) {
		if (item.getType() != this.material) {
			item.setType(this.material);
		}
		return item;
	}

	@Override
	public Map<String, Object> serialize() {
		final Map<String, Object> data = super.serialize();
		data.put(MATERIAL_KEY, EnumUtils.getSlug(this.material));
		return data;
	}

	public static MaterialCriteria deserialize(final Map<String, Object> data) {
		final Material material = MapUtils.attemptGet(data,
				MapUtils::parseMaterial, (Material) null, MATERIAL_KEY);
		return new MaterialCriteria(material);
	}

}
