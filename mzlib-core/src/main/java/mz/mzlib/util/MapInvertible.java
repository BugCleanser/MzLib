package mz.mzlib.util;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MapInvertible<K, V> extends Invertible<MapInvertible<V, K>> implements Map<K, V>
{
    protected Map<K, V> delegate;

    public MapInvertible(MapInvertible<V, K> inverse, Map<K, V> delegate)
    {
        this.inverse = inverse;
        this.delegate = delegate;
    }
    public MapInvertible(Supplier<Map<?, ?>> generator)
    {
        this.delegate = RuntimeUtil.cast(generator.get());
        this.inverse = new MapInvertible<>(this, RuntimeUtil.cast(generator.get()));
        if(!this.isEmpty() || !this.inverse.isEmpty())
            throw new IllegalArgumentException("The generated map is not empty");
    }
    public MapInvertible()
    {
        this(HashMap::new);
    }

    @Override
    protected MapInvertible<V, K> invert()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size()
    {
        return this.delegate.size();
    }
    @Override
    public boolean isEmpty()
    {
        return this.delegate.isEmpty();
    }
    @Override
    public boolean containsKey(Object key)
    {
        return this.delegate.containsKey(key);
    }
    @Override
    public boolean containsValue(Object value)
    {
        return this.inverse().containsKey(RuntimeUtil.<V>cast(value));
    }
    @Override
    public V get(Object key)
    {
        return this.delegate.get(key);
    }
    @Override
    public V put(K key, V value)
    {
        if(this.containsKey(key))
            this.inverse().delegate.remove(this.get(key));
        this.inverse().delegate.put(value, key);
        return this.delegate.put(key, value);
    }
    @Override
    public V remove(Object key)
    {
        if(!this.containsKey(key))
            return null;
        V v = this.delegate.remove(key);
        this.inverse().delegate.remove(v);
        return v;
    }
    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        for(Entry<? extends K, ? extends V> e : m.entrySet())
        {
            this.put(e.getKey(), e.getValue());
        }
    }
    @Override
    public void clear()
    {
        this.delegate.clear();
        this.inverse().delegate.clear();
    }
    @SuppressWarnings("EqualsDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o)
    {
        return this.delegate.equals(o);
    }
    @Override
    public int hashCode()
    {
        return this.delegate.hashCode();
    }
    @Override
    public V getOrDefault(Object key, V defaultValue)
    {
        return this.delegate.getOrDefault(key, defaultValue);
    }
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action)
    {
        this.delegate.forEach(action);
    }
    @Override
    public boolean remove(Object key, Object value)
    {
        this.inverse().delegate.remove(value, key);
        return this.delegate.remove(key, value);
    }
    public class Itr
    {
        Iterator<Entry<K, V>> delegate = MapInvertible.this.delegate.entrySet().iterator();
        Entry<K, V> current;
        public boolean hasNext()
        {
            return this.delegate.hasNext();
        }
        public Entry<K, V> nextEntry()
        {
            Entry<K, V> e = this.delegate.next();
            return current = new Entry<K, V>()
            {
                @Override
                public K getKey()
                {
                    return e.getKey();
                }
                @Override
                public V getValue()
                {
                    return e.getValue();
                }
                @Override
                public V setValue(V value)
                {
                    MapInvertible.this.inverse().delegate.remove(e.getValue());
                    MapInvertible.this.inverse().delegate.put(value, e.getKey());
                    return e.setValue(value);
                }
            };
        }
        public void remove()
        {
            MapInvertible.this.inverse().delegate.remove(current.getValue());
            this.delegate.remove();
        }
    }

    public class IteratorEntry extends Itr implements Iterator<Entry<K, V>>
    {
        @Override
        public Entry<K, V> next()
        {
            return this.nextEntry();
        }
    }

    public class IteratorKey extends Itr implements Iterator<K>
    {
        @Override
        public K next()
        {
            return this.nextEntry().getKey();
        }
    }

    public class IteratorValue extends Itr implements Iterator<V>
    {
        @Override
        public V next()
        {
            return this.nextEntry().getValue();
        }
    }
    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return new AbstractSet<Entry<K, V>>()
        {
            @Override
            public Iterator<Entry<K, V>> iterator()
            {
                return new IteratorEntry();
            }
            @Override
            public int size()
            {
                return MapInvertible.this.size();
            }
            @Override
            public void clear()
            {
                MapInvertible.this.clear();
            }
            @Override
            public boolean contains(Object o)
            {
                if(!(o instanceof Entry))
                    return false;
                Entry<?, ?> e = (Entry<?, ?>) o;
                return MapInvertible.this.containsKey(e.getKey()) && MapInvertible.this.get(e.getKey()) == e.getValue();
            }
            @Override
            public boolean remove(Object o)
            {
                if(!(o instanceof Entry))
                    return false;
                Entry<?, ?> e = (Entry<?, ?>) o;
                return MapInvertible.this.remove(e.getKey(), e.getValue());
            }
        };
    }
    @Override
    public Set<K> keySet()
    {
        return new AbstractSet<K>()
        {
            @Override
            public int size()
            {
                return MapInvertible.this.size();
            }
            @Override
            public void clear()
            {
                MapInvertible.this.clear();
            }
            @Override
            public Iterator<K> iterator()
            {
                return new IteratorKey();
            }
            @Override
            public boolean contains(Object o)
            {
                return MapInvertible.this.containsKey(o);
            }
        };
    }
    @Override
    public Collection<V> values()
    {
        return new AbstractCollection<V>()
        {
            @Override
            public int size()
            {
                return MapInvertible.this.size();
            }
            @Override
            public void clear()
            {
                MapInvertible.this.clear();
            }
            @Override
            public Iterator<V> iterator()
            {
                return new IteratorValue();
            }
            @Override
            public boolean contains(Object o)
            {
                return MapInvertible.this.containsValue(o);
            }
        };
    }
}
