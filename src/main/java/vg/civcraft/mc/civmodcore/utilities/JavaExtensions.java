package vg.civcraft.mc.civmodcore.utilities;

import java.util.function.Supplier;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;

/**
 * Set of extension methods to make Java more tolerable. Use {@link ExtensionMethod @ExtensionMethod} to take most
 * advantage of this.
 */
@UtilityClass
public final class JavaExtensions {
	
	public static <T> T orElse(final T self, final T fallback) {
		return self == null ? fallback : self;
	}

	public static <T> T orElseGet(final T self, final Supplier<T> getter) {
		return self == null ? getter.get() : self;
	}

}
