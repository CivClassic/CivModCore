package vg.civcraft.mc.civmodcore.api;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import vg.civcraft.mc.civmodcore.util.KeyedUtils;

/**
 * @deprecated Use {@link KeyedUtils} instead.
 */
@Deprecated
public final class NamespaceAPI {

	/**
	 * @deprecated Use {@link KeyedUtils#fromString(String)} instead.
	 */
	@Deprecated
	public static NamespacedKey fromString(String key) {
		return KeyedUtils.fromString(key);
	}

	/**
	 * @deprecated Use {@link KeyedUtils#fromParts(String, String)} instead.
	 */
	@Deprecated
	public static NamespacedKey fromParts(String namespace, String key) {
		return KeyedUtils.fromParts(namespace, key);
	}

	/**
	 * @deprecated Use {@link KeyedUtils#getString(NamespacedKey)} instead.
	 */
	@Deprecated
	public static String getString(NamespacedKey key) {
		return KeyedUtils.getString(key);
	}

	/**
	 * @deprecated Use {@link KeyedUtils#getString(Keyed)} instead.
	 */
	@Deprecated
	public static String getString(Keyed keyed) {
		return KeyedUtils.getString(keyed);
	}

}
