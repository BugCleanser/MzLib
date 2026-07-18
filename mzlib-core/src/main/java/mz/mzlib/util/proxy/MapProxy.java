package mz.mzlib.util.proxy;

import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.ModifyMonitor;
import mz.mzlib.util.RuntimeUtil;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class MapProxy<K, V, K1, V1> extends AbstractMap<K, V>
{
    Map<K1, V1> delegate;
    FunctionInvertible<K1, K> functionKey;
    FunctionInvertible<V1, V> functionValue;
    @Nullable Class<? super K> typeKey;
    @Nullable Class<? super V> typeValue;
    ModifyMonitor modifyMonitor;
    
    public MapProxy(
            Map<K1, V1> delegate,
            FunctionInvertible<K1, K> functionKey,
            FunctionInvertible<V1, V> functionValue,
            @Nullable Class<? super K> typeKey,
            @Nullable Class<? super V> typeValue,
            ModifyMonitor modifyMonitor)
    {
        this.delegate = delegate;
        this.functionKey = functionKey;
        this.functionValue = functionValue;
        this.modifyMonitor = modifyMonitor;
        this.typeKey = typeKey;
        this.typeValue = typeValue;
    }
    public MapProxy(
            Map<K1, V1> delegate,
            FunctionInvertible<K1, K> functionKey,
            FunctionInvertible<V1, V> functionValue,
            @Nullable Class<? super K> typeKey,
            @Nullable Class<? super V> typeValue)
    {
        this(delegate, functionKey, functionValue, typeKey, typeValue, ModifyMonitor.Empty.instance);
    }
    public MapProxy(
            Map<K1, V1> delegate,
            FunctionInvertible<K1, K> functionKey,
            FunctionInvertible<V1, V> functionValue,
            ModifyMonitor modifyMonitor)
    {
        this(delegate, functionKey, functionValue, null, null, modifyMonitor);
    }
    public MapProxy(
            Map<K1, V1> delegate,
            FunctionInvertible<K1, K> functionKey,
            FunctionInvertible<V1, V> functionValue)
    {
        this(delegate, functionKey, functionValue, ModifyMonitor.Empty.instance);
    }
    public static <K, V> MapProxy<K, V, K, V> of(Map<K, V> delegate, ModifyMonitor modifyMonitor)
    {
        return new MapProxy<>(delegate, FunctionInvertible.identity(), FunctionInvertible.identity(), modifyMonitor);
    }
    
    public Map<K1, V1> getDelegate()
    {
        return this.delegate;
    }
    public FunctionInvertible<K1, K> getFunctionKey()
    {
        return this.functionKey;
    }
    public FunctionInvertible<V1, V> getFunctionValue()
    {
        return this.functionValue;
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
        if(this.typeKey != null)
        {
            if(!this.typeKey.isInstance(key))
                return false;
        }
        K1 k1;
        try
        {
            k1 = functionKey.inverse().apply(RuntimeUtil.cast(key));
        }
        catch(ClassCastException e)
        {
            return false;
        }
        return this.delegate.containsKey(k1);
    }

    @Override
    public boolean containsValue(Object value)
    {
        if(this.typeValue != null)
        {
            if(!this.typeValue.isInstance(value))
                return false;
        }
        V1 v1;
        try
        {
            v1 = functionValue.inverse().apply(RuntimeUtil.cast(value));
        }
        catch(ClassCastException e)
        {
            return false;
        }
        return this.delegate.containsValue(v1);
    }

    @Override
    public @Nullable V get(Object key)
    {
        if(this.typeKey != null)
        {
            if(!this.typeKey.isInstance(key))
                return null;
        }
        K1 k1;
        try
        {
            k1 = functionKey.inverse().apply(RuntimeUtil.cast(key));
        }
        catch(ClassCastException e)
        {
            return null;
        }
        V1 v1 = this.delegate.get(k1);
        if(v1 == null)
            return null;
        return functionValue.apply(v1);
    }

    @Override
    public @Nullable V put(K key, V value)
    {
        this.modifyMonitor.onModify();
        V1 result = this.delegate.put(functionKey.inverse().apply(key), functionValue.inverse().apply(value));
        this.modifyMonitor.markDirty();
        if(result == null)
            return null;
        return functionValue.apply(result);
    }

    @Override
    public @Nullable V remove(Object key)
    {
        if(this.typeKey != null)
        {
            if(!this.typeKey.isInstance(key))
                return null;
        }
        K1 k1;
        try
        {
            k1 = functionKey.inverse().apply(RuntimeUtil.cast(key));
        }
        catch(ClassCastException e)
        {
            return null;
        }
        this.modifyMonitor.onModify();
        V1 result = this.delegate.remove(k1);
        this.modifyMonitor.markDirty();
        if(result == null)
            return null;
        return functionValue.apply(result);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        this.modifyMonitor.onModify();
        this.delegate.putAll(new MapProxy<>(RuntimeUtil.cast(m), functionKey.inverse(), functionValue.inverse()));
        this.modifyMonitor.markDirty();
    }

    @Override
    public void clear()
    {
        this.modifyMonitor.onModify();
        this.delegate.clear();
        this.modifyMonitor.markDirty();
    }

    @Override
    public Set<K> keySet()
    {
        return new SetProxy<>(this.delegate.keySet(), this.functionKey, this.modifyMonitor);
    }

    @Override
    public Collection<V> values()
    {
        return CollectionProxy.of(this.delegate.values(), this.functionValue, this.modifyMonitor);
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return new SetProxy<>(
            this.delegate.entrySet(), FunctionInvertible.of(
            e -> new EntryProxy<>(e, functionKey, functionValue),
            e -> new EntryProxy<>(e, functionKey.inverse(), functionValue.inverse())
        ), this.modifyMonitor
        );
    }

    public static class EntryProxy<K, V, K1, V1> implements Map.Entry<K, V>
    {
        Map.Entry<K1, V1> delegate;
        Function<K1, K> functionKey;
        FunctionInvertible<V1, V> functionValue;

        public EntryProxy(
            Map.Entry<K1, V1> delegate,
            Function<K1, K> functionKey,
            FunctionInvertible<V1, V> functionValue)
        {
            this.delegate = delegate;
            this.functionKey = functionKey;
            this.functionValue = functionValue;
        }

        @Override
        public K getKey()
        {
            return this.functionKey.apply(this.delegate.getKey());
        }

        @Override
        public V getValue()
        {
            return this.functionValue.apply(this.delegate.getValue());
        }

        @Override
        public V setValue(V value)
        {
            return this.functionValue.apply(this.delegate.setValue(this.functionValue.inverse().apply(value)));
        }

        @Override
        public boolean equals(Object obj)
        {
            if(obj == this)
                return true;
            if(!(obj instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> other = (Map.Entry<?, ?>) obj;
            return Objects.equals(this.getKey(), other.getKey()) && Objects.equals(this.getValue(), other.getValue());
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(this.getKey(), this.getValue());
        }
    }
}
