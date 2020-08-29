package vg.civcraft.mc.civmodcore.custom.items;

import com.google.common.base.Strings;
import java.util.Map;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.api.NamespaceAPI;
import vg.civcraft.mc.civmodcore.serialization.NBTCompound;
import vg.civcraft.mc.civmodcore.util.NullCoalescing;
import vg.civcraft.mc.civmodcore.util.Validation;

public class NBTCriteria extends ItemCriteria {

	private static final String CUSTOM_ITEM_NBT = "CustomItem";

	@Override
	public boolean matches(final CustomItem custom, final ItemStack item) {
		NBTCompound nbt = NBTCompound.fromItem(item);
		if (!Validation.checkValidity(nbt) || nbt.isEmpty()) {
			return false;
		}
		String raw = nbt.getString(CUSTOM_ITEM_NBT);
		if (Strings.isNullOrEmpty(raw)) {
			return false;
		}
		NamespacedKey key = NamespaceAPI.fromString(raw);
		if (!NullCoalescing.equalsNotNull(key, custom.getKey())) {
			return false;
		}
		return true;
	}

	@Override
	public ItemStack applyToItem(final CustomItem custom, final ItemStack item) {
		return NBTCompound.processItem(item, nbt -> {
			nbt.setString(CUSTOM_ITEM_NBT, NamespaceAPI.getString(custom.getKey()));
		});
	}

	public static NBTCriteria deserialize(final Map<String, Object> data) {
		return new NBTCriteria();
	}

}
