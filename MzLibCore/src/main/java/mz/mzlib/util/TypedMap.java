package mz.mzlib.util;

import java.util.*;

/**
 * @see TypedMapClass
 */
public interface TypedMap<K0 extends TypedMap.Key<?, K0>>
{
    Map<K0, Object> asMap();
    default <T, K extends Key<T, K0>> Option<T> get(K key)
    {
        return Option.fromNullable(RuntimeUtil.cast(this.asMap().get(RuntimeUtil.<K0>cast(key))));
    }
    default <T, K extends Key<T, K0>> Option<T> put(K key, T value)
    {
        return Option.fromNullable(RuntimeUtil.cast(this.asMap().put(RuntimeUtil.cast(key), RuntimeUtil.cast(value))));
    }
    default <T, K extends Key<T, K0>> Option<T> remove(K key)
    {
        return Option.fromNullable(RuntimeUtil.cast(this.asMap().remove(RuntimeUtil.<K0>cast(key))));
    }
    default <T, K extends Key<T, K0>> T getOr(K key, T defaultValue)
    {
        return RuntimeUtil.cast(this.asMap().getOrDefault(RuntimeUtil.<K0>cast(key), defaultValue));
    }
    default int size()
    {
        return this.asMap().size();
    }
    default boolean isEmpty()
    {
        return this.asMap().isEmpty();
    }
    default boolean containsKey(K0 key)
    {
        return this.asMap().containsKey(key);
    }
    default boolean containsValue(Object value)
    {
        return this.asMap().containsValue(value);
    }
    default void putAll(TypedMap<K0> m)
    {
        this.asMap().putAll(m.asMap());
    }
    default void clear()
    {
        this.asMap().clear();
    }
    default Set<K0> keySet()
    {
        return this.asMap().keySet();
    }
    default Collection<Object> values()
    {
        return this.asMap().values();
    }
    default <T, K extends Key<T, K0>> Option<T> putIfAbstract(K key, T value)
    {
        return Option.fromNullable(RuntimeUtil.cast(this.asMap().putIfAbsent(RuntimeUtil.cast(key), value)));
    }
    default <T, K extends Key<T, K0>> boolean remove(K key, T value)
    {
        return this.asMap().remove(RuntimeUtil.<K0>cast(key), value);
    }
    default <T, K extends Key<T, K0>> boolean replace(K key, T value, T newValue)
    {
        return this.asMap().replace(RuntimeUtil.cast(key), value, newValue);
    }
    default <T, K extends Key<T, K0>> Option<T> replace(K key, T value)
    {
        return Option.fromNullable(RuntimeUtil.cast(this.asMap().replace(RuntimeUtil.cast(key), value)));
    }
    default <T, K extends Key<T, K0>, E extends Throwable> T computeIfAbsent(
        K key,
        ThrowableFunction<? super K, ? extends T, E> action) throws E
    {
        return RuntimeUtil.cast(this.asMap().computeIfAbsent(RuntimeUtil.cast(key), RuntimeUtil.cast(action)));
    }
    default <T, K extends Key<T, K0>, E extends Throwable> T computeIfAbsent(
        K key,
        ThrowableSupplier<? extends T, E> action) throws E
    {
        return this.computeIfAbsent(key, k -> action.getOrThrow());
    }
    default <T, K extends Key<T, K0>, E extends Throwable> Option<T> computeIfPresent(
        K key,
        ThrowableBiFunction<? super K, ? super T, ? extends Option<? extends T>, E> action) throws E
    {
        RuntimeUtil.<E>declaredlyThrow();
        return Option.fromNullable(RuntimeUtil.cast(this.asMap().computeIfPresent(
            RuntimeUtil.cast(key),
            (k, v) -> action.apply(RuntimeUtil.cast(k), RuntimeUtil.cast(v)).toNullable()
        )));
    }
    default <T, K extends Key<T, K0>, E extends Throwable> Option<T> computeIfPresent(
        K key,
        ThrowableFunction<? super T, ? extends Option<? extends T>, E> action) throws E
    {
        return this.computeIfPresent(key, (k, v) -> action.applyOrThrow(v));
    }
    default <T, K extends Key<T, K0>, E extends Throwable> Option<T> compute(
        K key,
        ThrowableBiFunction<? super K, ? super T, ? extends Option<? extends T>, E> action) throws E
    {
        RuntimeUtil.<E>declaredlyThrow();
        return Option.fromNullable(RuntimeUtil.cast(this.asMap().compute(
            RuntimeUtil.cast(key),
            (k, v) -> action.apply(RuntimeUtil.cast(k), RuntimeUtil.cast(v)).toNullable()
        )));
    }
    default <T, K extends Key<T, K0>, E extends Throwable> Option<T> compute(
        K key,
        ThrowableFunction<? super T, ? extends Option<? extends T>, E> action) throws E
    {
        return this.compute(key, (k, v) -> action.applyOrThrow(v));
    }


    interface Key<T, K0 extends Key<?, K0>>
    {
    }

    static <K0 extends TypedMap.Key<?, K0>> TypedMap<K0> of()
    {
        //noinspection Convert2Diamond: fuck javac
        return of(new HashMap<K0, Object>());
    }
    static <K0 extends TypedMap.Key<?, K0>> TypedMap<K0> of(Map<K0, Object> map)
    {
        return new OfMap<>(map);
    }
    class OfMap<K0 extends TypedMap.Key<?, K0>> implements TypedMap<K0>
    {
        Map<K0, Object> delegate;
        public OfMap(Map<K0, Object> delegate)
        {
            this.delegate = delegate;
        }
        @Override
        public Map<K0, Object> asMap()
        {
            return this.delegate;
        }
    }
}
