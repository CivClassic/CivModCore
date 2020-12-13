package vg.civcraft.mc.civmodcore.util;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.bukkit.Material;
import vg.civcraft.mc.civmodcore.api.MaterialAPI;


/**
 * @deprecated Use {@link MoreMapUtils} instead.
 */
@Deprecated(forRemoval = true)
public final class MapUtils {

	/**
	 * @deprecated Use {@link org.apache.commons.collections4.MapUtils#isEmpty(Map)} instead.
	 */
	@Deprecated
	public static <K, V> boolean isNullOrEmpty(Map<K, V> map) {
		return org.apache.commons.collections4.MapUtils.isEmpty(map);
	}

	/**
	 * @deprecated Use {@link MoreMapUtils#getKeyFromValue(Map, Object)} instead.
	 */
	@Deprecated
	public static <K, V> K getKeyFromValue(final Map<K, V> map, final V value) {
		return MoreMapUtils.getKeyFromValue(map, value);
	}

	/**
	 * @deprecated Use {@link MoreMapUtils#attemptGet(Map, Object, Object[])} instead.
	 */
	@Deprecated
	@SafeVarargs
	public static <K, V, R> R attemptGet(Map<K, V> map, R fallback, K... keys) {
		return MoreMapUtils.attemptGet(map, fallback, keys);
	}

	/**
	 * @deprecated Use {@link MoreMapUtils#attemptGet(Map, Function, Object, Object[])} instead.
	 */
	@Deprecated
	@SafeVarargs
	public static <K, V, R> R attemptGet(Map<K, V> map, Function<V, R> parser, R fallback, K... keys) {
		return MoreMapUtils.attemptGet(map, parser, fallback, keys);
	}

	// ------------------------------------------------------------
	// Parsers
	// ------------------------------------------------------------

	/**
	 * <p>Parses a list from a map.</p>
	 *
	 * <p>Use with {@link #attemptGet(Map, Function, Object, Object[])} as the parser.</p>
	 *
	 * @param value The value retrieved from the map.
	 * @return Returns the value cast to a list, or null.
	 */
	public static List<?> parseList(Object value) {
		if (value instanceof List) {
			return (List<?>) value;
		}
		return null;
	}

	/**
	 * <p>Parses a material from a map.</p>
	 *
	 * <p>Use with {@link #attemptGet(Map, Function, Object, Object[])} as the parser.</p>
	 *
	 * @param value The value retrieved from the map.
	 * @return Returns the value as a material, or null.
	 */
	public static Material parseMaterial(Object value) {
		if (value instanceof Material) {
			return (Material) value;
		}
		if (value instanceof String) {
			return MaterialAPI.getMaterial((String) value);
		}
		return null;
	}

}
