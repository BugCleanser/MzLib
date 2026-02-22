package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

@ApiStatus.Experimental
public interface Cache<K, V extends @Nullable Object>
{
    V get(K key);
    V get(K key, Supplier<V> supplier);
    void put(K key, V value);
    void clear();

    static <K, V> Builder<K, V> builder()
    {
        return new Builder<>();
    }
    class Builder<K, V>
    {
        boolean weakKey = false;
        Function<K, @Nullable V> defaultSupplier = ThrowableSupplier.constant((@Nullable V) null).ignore();
        public Builder<K, V> weakKey(boolean value)
        {
            this.weakKey = value;
            return this;
        }
        public Builder<K, V> weakKey()
        {
            return this.weakKey(true);
        }
        public Builder<K, V> defaultSupplier(Function<K, V> value)
        {
            this.defaultSupplier = value;
            return this;
        }
        public Cache<K, V> build()
        {
            return new Impl<>(weakKey ? new MapConcurrentWeakHash<>() : new ConcurrentHashMap<>(), this.defaultSupplier);
        }
    }

    class Impl<K, V> implements Cache<K, V>
    {
        Map<K, SoftReference<Box<V>>> data;
        Function<K, V> defaultSupplier;

        Impl(Map<K, SoftReference<Box<V>>> data, Function<K, V> defaultSupplier)
        {
            this.data = data;
            this.defaultSupplier = defaultSupplier;
        }

        @Override
        public V get(K key)
        {
            return this.get(key, () -> this.defaultSupplier.apply(key));
        }

        @Override
        public V get(K key, Supplier<V> supplier)
        {
            Box.Mut<@Nullable Box<V>> result = Box.Mut.of(null);
            this.data.compute(key, (k, v) ->
            {
                if(v != null)
                {
                    result.set(v.get());
                    if(result.get() != null)
                        return v;
                }
                Box<V> value = Box.of(supplier.get());
                result.set(value);
                return new SoftReference<>(value);
            });
            return Objects.requireNonNull(result.get()).get();
        }

        @Override
        public void put(K key, V value)
        {
            this.data.put(key, new SoftReference<>(Box.of(value)));
        }

        @Override
        public void clear()
        {
            this.data.clear();
        }
    }
}
