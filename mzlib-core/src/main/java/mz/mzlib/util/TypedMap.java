package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@ApiStatus.Experimental
public class TypedMap<K, V>
{
    Map<Key<K, ? extends V>, V> delegate;
    
    TypedMap(Map<Key<K, ? extends V>, V> delegate)
    {
        this.delegate = delegate;
    }
    public TypedMap(Supplier<Map<Key<K, ? extends V>, V>> delegateBuilder)
    {
        this(RuntimeUtil.require(delegateBuilder.get(), Map::isEmpty));
    }
    public TypedMap()
    {
        this(HashMap::new);
    }
    
    public int size()
    {
        return this.delegate.size();
    }
    
    public boolean isEmpty()
    {
        return this.delegate.isEmpty();
    }
    
    public boolean containsKey(Key<?, ?> key)
    {
        return this.delegate.containsKey(key);
    }
    
    public boolean containsValue(Object value)
    {
        //noinspection SuspiciousMethodCalls
        return this.delegate.containsValue(value);
    }
    
    public <V1 extends V> @Nullable V1 get(Key<K, V1> key)
    {
        //noinspection unchecked
        return (@Nullable V1) this.delegate.get(key);
    }
    
    public <V1 extends V> @Nullable V1 put(Key<K, V1> key, V1 value)
    {
        //noinspection unchecked
        return (@Nullable V1) this.delegate.put(key, value);
    }
    
    public <V1 extends V> @Nullable V1 remove(Key<K, V1> key)
    {
        //noinspection unchecked
        return (@Nullable V1) this.delegate.remove(key);
    }
    
    public void putAll(TypedMap<K, ? extends V> m)
    {
        this.delegate.putAll(m.delegate);
    }
    
    public void clear()
    {
        this.delegate.clear();
    }
    
    public Set<Key<K, ? extends V>> keySet()
    {
        return this.delegate.keySet();
    }
    
    public Collection<V> values()
    {
        return this.delegate.values();
    }
    
    public Set<Map.Entry<Key<K, ? extends V>, V>> entrySet()
    {
        return this.delegate.entrySet();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof TypedMap))
            return false;
        TypedMap<?, ?> other = (TypedMap<?, ?>) obj;
        return this.delegate.equals(other.delegate);
    }
    
    @Override
    public int hashCode()
    {
        return this.delegate.hashCode();
    }
    
    public <V1 extends V> V1 getOrDefault(Key<K, V1> key, V1 defaultValue)
    {
        //noinspection unchecked
        return (V1) this.delegate.getOrDefault(key, defaultValue);
    }
    
    public void forEach(BiConsumer<? super Key<K, ? extends V>, ? super V> action)
    {
        this.delegate.forEach(action);
    }
    
    public <V1 extends V> V1 putIfAbsent(Key<K, V1> key, V1 value)
    {
        //noinspection unchecked
        return (V1) this.delegate.putIfAbsent(key, value);
    }
    
    public <V1> boolean remove(Key<?, V1> key, V1 value)
    {
        //noinspection SuspiciousMethodCalls
        return this.delegate.remove(key, value);
    }
    
    public <V1 extends V> boolean replace(Key<K, V1> key, V1 oldValue, V1 newValue)
    {
        return this.delegate.replace(key, oldValue, newValue);
    }
    
    public <V1 extends V> V1 replace(Key<K, V1> key, V1 value)
    {
        //noinspection unchecked
        return (V1) this.delegate.replace(key, value);
    }
    
    public <V1 extends V> V1 computeIfAbsent(Key<K, V1> key, Function<? super K, ? extends V1> mappingFunction)
    {
        //noinspection unchecked
        return (V1) this.delegate.computeIfAbsent(key, it -> mappingFunction.apply(it.getData()));
    }
    
    public <V1 extends V> @Nullable V1 computeIfPresent(Key<K, V1> key, BiFunction<? super K, ? super V1, ? extends @Nullable V1> mappingFunction)
    {
        //noinspection unchecked
        return (@Nullable V1) this.delegate.computeIfPresent(key, (k, v) -> mappingFunction.apply(k.getData(), (V1) v));
    }
    
    public <V1 extends V> @Nullable V1 compute(Key<K, V1> key, BiFunction<? super K, ? super @Nullable V1, ? extends @Nullable V1> mappingFunction)
    {
        //noinspection unchecked
        return (@Nullable V1) this.delegate.compute(key, (k, v) -> mappingFunction.apply(k.getData(), (V1) v));
    }
    
    public <V1 extends V> @Nullable V1 merge(Key<K, V1> key, V1 value, BiFunction<? super V1, ? super V1, @Nullable V1> remappingFunction)
    {
        //noinspection unchecked
        return (V1) this.delegate.merge(key, value, (a, b) -> remappingFunction.apply((V1) a, (V1) b));
    }
    
    @SuppressWarnings("unused")
    public static class Key<K, V1> implements Comparable<Key<K, ?>>
    {
        protected K data;
        
        public Key(K data)
        {
            this.data = data;
        }
        public static <K, V1> Key<K, V1> of(K data)
        {
            return new Key<>(data);
        }
        
        public K getData()
        {
            return this.data;
        }
        
        @Override
        public int hashCode()
        {
            return this.data.hashCode();
        }
        @Override
        public boolean equals(Object obj)
        {
            if(!(obj instanceof Key))
                return false;
            return this.data.equals(((Key<?, ?>)obj).data);
        }
        @Override
        public int compareTo(Key<K, ?> o)
        {
            return RuntimeUtil.<Comparable<K>>cast(this.data).compareTo(o.data);
        }
    }
    
    public static class KeySafe<K, V1> extends Key<K, V1>
    {
        protected Class<V1> type;
        
        public KeySafe(Class<V1> type, K data)
        {
            super(data);
            this.type = type;
        }
        
        @Override
        public int hashCode()
        {
            return Objects.hash(this.type, this.data);
        }
        @Override
        public boolean equals(Object obj)
        {
            if(!(obj instanceof KeySafe))
                return false;
            KeySafe<?, ?> that = (KeySafe<?, ?>)obj;
            return this.type.equals(that.type) && this.data.equals(that.data);
        }
        @ApiStatus.Experimental
        @Override
        public int compareTo(Key<K, ?> o)
        {
            throw new UnsupportedOperationException();
        }
    }
}
