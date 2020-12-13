package vg.civcraft.mc.civmodcore.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @deprecated Use {@link MoreArrayUtils}, {@link MoreCollectionUtils}, and {@link MoreMapUtils} instead.
 */
@Deprecated(forRemoval = true)
public final class Iteration {

	@FunctionalInterface
	public interface ElementAndBoolConsumer<T> {
		void accept(T former, boolean latter);
	}

	/**
	 * @deprecated Use {@link ArrayUtils#isEmpty(Object[])} instead.
	 */
	@SafeVarargs
	@Deprecated
	public static <T> boolean isNullOrEmpty(T... array) {
		return ArrayUtils.isEmpty(array);
	}

	/**
	 * @deprecated Use {@link CollectionUtils#isEmpty(Collection)} instead.
	 */
	@Deprecated
    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
    	return CollectionUtils.isEmpty(collection);
	}

	/**
	 * @deprecated Use {@link ArrayUtils#contains(Object[], Object)} instead.
	 */
	@SafeVarargs
	@Deprecated
    public static <T> boolean contains(T base, T... values) {
		return ArrayUtils.contains(values, base);
	}

	/**
	 * @deprecated Use {@link Collection#forEach(Consumer)} and {@link Collection#clear()} instead.
	 */
	@Deprecated
    public static <T> void iterateThenClear(Collection<T> collection, Consumer<T> processor) {
		if (isNullOrEmpty(collection) || processor == null) {
			return;
		}
		collection.forEach(processor);
		collection.clear();
	}

	/**
	 * @deprecated Use {@link Collection#iterator()} instead.
	 */
	@Deprecated
    public static <T> void iterateHasNext(Collection<T> collection, ElementAndBoolConsumer<T> processor) {
		if (isNullOrEmpty(collection) || processor == null) {
			return;
		}
		Iterator<T> iterator = collection.iterator();
		while (iterator.hasNext()) {
			processor.accept(iterator.next(), iterator.hasNext());
		}
	}

	/**
	 * @deprecated Use {@link MoreArrayUtils#fill(Object[], Object)} instead.
	 */
	@Deprecated
    public static <T> T[] fill(T[] array, T value) {
    	return MoreArrayUtils.fill(array, value);
	}

	/**
	 * @deprecated Use {@link MoreArrayUtils#anyMatch(Object[], Predicate)} instead.
	 */
	@Deprecated
	public static <T> boolean some(T[] array, Predicate<T> predicate) {
		return anyMatch(array, predicate);
	}

	/**
	 * @deprecated Use {@link MoreArrayUtils#anyMatch(Object[], Predicate)} instead.
	 */
	@Deprecated
	public static <T> boolean anyMatch(T[] array, Predicate<T> predicate) {
		return MoreArrayUtils.anyMatch(array, predicate);
	}

	/**
	 * @deprecated Use {@link MoreArrayUtils#allMatch(Object[], Predicate)} instead.
	 */
	@Deprecated
	public static <T> boolean every(T[] array, Predicate<T> predicate) {
		return MoreArrayUtils.allMatch(array, predicate);
	}

	/**
	 * @deprecated Use {@link MoreArrayUtils#allMatch(Object[], Predicate)} instead.
	 */
	@Deprecated
	public static <T> boolean allMatch(T[] array, Predicate<T> predicate) {
		return MoreArrayUtils.allMatch(array, predicate);
	}

	/**
	 * @deprecated Use {@link MoreMapUtils#validEntry(Map.Entry)} instead.
	 */
	@Deprecated
	public static boolean validEntry(Map.Entry<?, ?> entry) {
		return MoreMapUtils.validEntry(entry);
	}

	/**
	 * @deprecated Use {@link CollectionUtils#addAll(Collection, Object[])} instead.
	 */
	@SafeVarargs
	@Deprecated
	public static <T, K extends Collection<T>> K collect(Supplier<K> constructor, T... elements) {
		final K collection = constructor.get();
		CollectionUtils.addAll(collection, elements);
		return collection;
	}

	/**
	 * @deprecated Use {@link CollectionUtils#addAll(Collection, Object[])} instead.
	 */
	@SafeVarargs
	@Deprecated
	public static <T> void addAll(Collection<T> collection, T... values) {
		CollectionUtils.addAll(collection, values);
	}

	/**
	 * @deprecated Use {@link MoreCollectionUtils#removeLastElement(List)} instead.
	 */
	@Deprecated
	public static <T> T removeLastElement(List<T> list) {
		return MoreCollectionUtils.removeLastElement(list);
	}

	/**
	 * @deprecated Use {@link MoreArrayUtils#randomElement(Object[])} instead.
	 */
	@Deprecated
	@SafeVarargs
	public static <T> T randomElement(final T... array) {
		return MoreArrayUtils.randomElement(array);
	}

	/**
	 * @deprecated Use {@link MoreCollectionUtils#randomElement(List)} instead.
	 */
	@Deprecated
	public static <T> T randomElement(final List<T> list) {
		return MoreCollectionUtils.randomElement(list);
	}

}
