package vg.civcraft.mc.civmodcore.custom.items;

import static vg.civcraft.mc.civmodcore.util.NullCoalescing.castOrNull;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.api.ItemAPI;
import vg.civcraft.mc.civmodcore.api.NamespaceAPI;
import vg.civcraft.mc.civmodcore.util.Iteration;
import vg.civcraft.mc.civmodcore.util.MapUtils;
import vg.civcraft.mc.civmodcore.util.TextUtil;

public class CustomItem implements ConfigurationSerializable {

	public static final String ID_KEY = "key";

	public static final String NAME_KEY = "name";

	public static final String EXTRA_KEY = "extra";

	protected final NamespacedKey key;

	protected final String name;

	protected final List<ItemCriteria> extra;

	public CustomItem(final NamespacedKey key, final String name, final ItemCriteria... extra) {
		if (key == null) {
			throw new IllegalArgumentException("Cannot create custom item criteria with a null key.");
		}
		if (Strings.isNullOrEmpty(name)) {
			throw new IllegalArgumentException("Cannot create custom item criteria with an invalid name.");
		}
		this.key = key;
		this.name = name;
		this.extra = Iteration.collect(ArrayList::new, extra);
	}

	public final NamespacedKey getKey() {
		return this.key;
	}

	public final String getName() {
		return this.name;
	}

	public boolean matches(final ItemStack item) {
		if (item == null || Iteration.isNullOrEmpty(this.extra)) {
			return false;
		}
		for (ItemCriteria criteria : this.extra) {
			if (!criteria.matches(this, item)) {
				return false;
			}
		}
		return true;
	}

	public ItemStack applyToItem(ItemStack item) {
		if (!Strings.isNullOrEmpty(this.name)) {
			ItemAPI.setDisplayName(item, "" + ChatColor.RESET + this.name);
		}
		for (ItemCriteria criteria : this.extra) {
			item = criteria.applyToItem(this, item);
		}
		return item;
	}

	@Override
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<>();
		data.put(ID_KEY, NamespaceAPI.getString(this.key));
		data.put(NAME_KEY, this.name);
		data.put(EXTRA_KEY, this.extra);
		return data;
	}

	public static CustomItem deserialize(final Map<String, Object> data) {
		final NamespacedKey key = NamespaceAPI.fromString(castOrNull(String.class, data.get(ID_KEY)));
		final String name = TextUtil.parse(castOrNull(String.class, data.get(NAME_KEY)));
		final ItemCriteria[] extra = MapUtils.attemptGet(data, MapUtils::parseList, new ArrayList<>(), EXTRA_KEY)
				.stream()
				.filter(entry -> entry instanceof ItemCriteria)
				.map(entry -> (ItemCriteria) entry)
				.toArray(ItemCriteria[]::new);
		return new CustomItem(key, name, extra);
	}

}
