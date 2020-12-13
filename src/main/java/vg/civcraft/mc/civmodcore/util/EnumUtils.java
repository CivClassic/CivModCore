package vg.civcraft.mc.civmodcore.util;

/**
 * @deprecated Use {@link org.apache.commons.lang3.EnumUtils} instead.
 */
@Deprecated(forRemoval = true)
public class EnumUtils {

	/**
	 * @deprecated Use {@link org.apache.commons.lang3.EnumUtils#getEnum(Class, String)} or
	 *     {@link org.apache.commons.lang3.EnumUtils#getEnumIgnoreCase(Class, String)} instead.
	 */
	@Deprecated
	public static <T extends Enum<T>> T fromSlug(Class<T> clazz, String slug, boolean caseInsensitive) {
		if (caseInsensitive) {
			return org.apache.commons.lang3.EnumUtils.getEnum(clazz, slug);
		}
		else {
			return org.apache.commons.lang3.EnumUtils.getEnumIgnoreCase(clazz, slug);
		}
	}

	/**
	 * @deprecated Use {@link Chainer} instead.
	 */
	@Deprecated
	public static String getSlug(Enum<?> element) {
		return Chainer.from(element).then(Enum::name).get();
	}

}
