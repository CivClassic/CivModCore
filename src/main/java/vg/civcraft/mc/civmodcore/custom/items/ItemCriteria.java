package vg.civcraft.mc.civmodcore.custom.items;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public abstract class ItemCriteria implements ConfigurationSerializable {

	public abstract boolean matches(final CustomItem custom, final ItemStack item);

	public abstract ItemStack applyToItem(final CustomItem custom, final ItemStack item);

	@Override
	public Map<String, Object> serialize() {
		return new HashMap<>();
	}

	// Copy and paste this to your implementation. Make sure to change the type
	// from ItemCriteria to whatever your class is.
	public static ItemCriteria deserialize(final Map<String, Object> data) {
		throw new NotImplementedException();
	}

}
