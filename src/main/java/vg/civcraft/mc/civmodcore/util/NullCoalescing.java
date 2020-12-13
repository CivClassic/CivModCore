package vg.civcraft.mc.civmodcore.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @deprecated Use {@link NullUtils} instead.
 */
@Deprecated(forRemoval = true)
public final class NullCoalescing {

    @FunctionalInterface
    public interface NullChecker<T> {
        T get() throws Exception;
    }

    /**
	 * @deprecated Use {@link NullUtils#coalesce(Object[])} instead.
     */
    @Deprecated
    @SafeVarargs
	public static <T> T coalesce(T... items) {
    	return NullUtils.coalesce(items);
    }

	/**
	 * @deprecated Use {@link Chainer} instead.
	 */
	@Deprecated
    public static <T> T chain(NullChecker<T> statement) {
        return chain(statement, null);
    }

    /**
     * <p>Allows developers to chain statements that might otherwise require a ton of null checking.</p>
	 *
	 * <p>Emulates: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Optional_chaining and
	 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Nullish_coalescing_operator</p>
	 *
     * @param <T> Any non-primitive type.
     * @param statement Function that throws an exception to call the chained statement within.
     * @param fallback The value that will be fallen back upon if something goes wrong.
     * @return Returns the result of the chained statement, or the fallback if the chain failed.
	 *
	 * @deprecated Use {@link Chainer} instead.
	 */
	@Deprecated
    public static <T> T chain(NullChecker<T> statement, T fallback) {
        if (statement == null) {
            return fallback;
        }
        try {
            return statement.get();
        }
        catch (Exception ignored) {
            return fallback;
        }
    }

    /**
	 * Runs a handler only if the given value is not null.
	 *
	 * @param <T> The type of the given parameter.
	 * @param value The given parameter.
	 * @param handler The handler to run if the given parameter exists.
	 *
	 * @deprecated Just use an if statement.
	 */
	@Deprecated
    public static <T> void exists(T value, Consumer<T> handler) {
    	if (value != null && handler != null) {
    		handler.accept(value);
		}
    }

    /**
	 * Executes a function to supply a value should that value not already exist.
	 *
	 * @param <T> The type of the value.
	 * @param value The given value.
	 * @param handler The supplier that will be run should the given value be null.
	 * @return Returns the given value or the result of the handler.
	 *
	 * @deprecated Just use an if statement.
	 */
	@Deprecated
    public static <T> T notExists(T value, Supplier<T> handler) {
    	if (value == null && handler != null) {
    		value = handler.get();
		}
    	return value;
	}

	/**
	 * @deprecated Use {@link MoreClassUtils#castOrNull(Class, Object)} instead.
	 */
	@Deprecated
	public static <T> T castOrNull(Class<T> clazz, Object value) {
		return MoreClassUtils.castOrNull(clazz, value);
	}

	/**
	 * @deprecated Use {@link NullUtils#equals(Object, Object)} instead.
	 */
	@Deprecated
	public static boolean equals(Object former, Object latter) {
		return NullUtils.equals(former, latter);
	}

	/**
	 * @deprecated Use {@link NullUtils#equalsNotNull(Object, Object)} instead.
	 */
	@Deprecated
    public static boolean equalsNotNull(Object former, Object latter) {
		return NullUtils.equalsNotNull(former, latter);
	}

}
