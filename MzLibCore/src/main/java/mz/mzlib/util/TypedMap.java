package mz.mzlib.util;

import mz.mzlib.util.proxy.SetProxy;

import java.util.*;

public interface TypedMap<K extends TypedMap.Key<?, K>> extends Iterable<TypedMap.Entry<?, K, ?>>
{
    boolean containsKey(K key);
    <T, K1 extends Key<T, K>> Option<T> get(K1 key);
    <T, K1 extends Key<T, K>> Option<T> put(K1 key, T value);
    <T, K1 extends Key<T, K>> Option<T> remove(K1 key);

    Map<K, Object> asMap();

    default Set<Entry<?, K, ?>> entrySet()
    {
        return RuntimeUtil.cast(new SetProxy<>(this.asMap().entrySet(), FunctionInvertible.of(EntryMap::new, EntryMap::getDelegate)));
    }

    @Override
    default Iterator<Entry<?, K, ?>> iterator()
    {
        return this.entrySet().iterator();
    }

    @Override
    default Spliterator<Entry<?, K, ?>> spliterator()
    {
        return Spliterators.spliterator(this.entrySet(), Spliterator.DISTINCT);
    }

    interface Key<T, K extends Key<?, K>>
    {
    }
    /**
     * @param <T> the value type
     * @param <K> the key type of map
     * @param <K1> the actual type of key
     */
    interface Entry<T, K extends Key<?, K>, K1 extends Key<T, K>> extends Map.Entry<K1, T>
    {
    }
    class EntryMap<T, K extends Key<?, K>, K1 extends Key<T, K>> implements Entry<T, K, K1>
    {
        Map.Entry<K, Object> delegate;
        public EntryMap(Map.Entry<K, Object> delegate)
        {
            this.delegate = delegate;
        }
        Map.Entry<K, Object> getDelegate()
        {
            return this.delegate;
        }
        @Override
        public K1 getKey()
        {
            return RuntimeUtil.cast(this.delegate.getKey());
        }
        @Override
        public T getValue()
        {
            return RuntimeUtil.cast(this.delegate.getValue());
        }
        @Override
        public T setValue(T value)
        {
            return RuntimeUtil.cast(this.delegate.setValue(value));
        }
        @Override
        public boolean equals(Object o)
        {
            if(o instanceof EntryMap<?, ?, ?>)
                return this.delegate.equals(((EntryMap<?, ?, ?>) o).delegate);
            return this.delegate.equals(o);
        }
        @Override
        public int hashCode()
        {
            return this.delegate.hashCode();
        }
    }

    static <K extends TypedMap.Key<?, K>> TypedMap<K> of()
    {
        return of(new HashMap<K, Object>());
    }
    static <K extends TypedMap.Key<?, K>> TypedMap<K> of(Map<K, Object> map)
    {
        return new OfMap<>(map);
    }
    class OfMap<K extends TypedMap.Key<?, K>> implements TypedMap<K>
    {
        Map<K, Object> delegate;
        public OfMap(Map<K, Object> delegate)
        {
            this.delegate = delegate;
        }
        @Override
        public boolean containsKey(K key)
        {
            return this.delegate.containsKey(key);
        }
        @Override
        public <T, K1 extends Key<T, K>> Option<T> get(K1 key)
        {
            return RuntimeUtil.cast(Option.fromNullable(this.delegate.get(RuntimeUtil.<K>cast(key))));
        }
        @Override
        public <T, K1 extends Key<T, K>> Option<T> put(K1 key, T value)
        {
            return RuntimeUtil.cast(Option.fromNullable(this.delegate.put(RuntimeUtil.cast(key), RuntimeUtil.cast(value))));
        }
        @Override
        public <T, K1 extends Key<T, K>> Option<T> remove(K1 key)
        {
            return RuntimeUtil.cast(Option.fromNullable(this.delegate.remove(RuntimeUtil.<K>cast(key))));
        }
        @Override
        public Map<K, Object> asMap()
        {
            return this.delegate;
        }
    }
}
