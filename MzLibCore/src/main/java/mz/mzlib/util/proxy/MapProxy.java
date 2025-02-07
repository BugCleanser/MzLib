package mz.mzlib.util.proxy;

import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.MapEntry;
import mz.mzlib.util.ModifyMonitor;
import mz.mzlib.util.RuntimeUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MapProxy<K, V, K1, V1> implements Map<K, V>
{
    Map<K1, V1> delegate;
    InvertibleFunction<K, K1> functionKey;
    InvertibleFunction<V, V1> functionValue;
    ModifyMonitor modifyMonitor;
    
    public MapProxy(Map<K1, V1> delegate, InvertibleFunction<K, K1> functionKey, InvertibleFunction<V, V1> functionValue, ModifyMonitor modifyMonitor)
    {
        this.delegate = delegate;
        this.functionKey = functionKey;
        this.functionValue = functionValue;
        this.modifyMonitor = modifyMonitor;
    }
    public MapProxy(Map<K1, V1> delegate, InvertibleFunction<K, K1> functionKey, InvertibleFunction<V, V1> functionValue)
    {
        this(delegate, functionKey, functionValue, ModifyMonitor.Empty.instance);
    }
    public static <K, V> MapProxy<K, V, K, V> of(Map<K, V> delegate, ModifyMonitor modifyMonitor)
    {
        return new MapProxy<>(delegate, null, null, modifyMonitor);
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
        K1 k1;
        try
        {
            k1 = functionKey.apply(RuntimeUtil.cast(key));
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
        V1 v1;
        try
        {
            v1 = functionValue.apply(RuntimeUtil.cast(value));
        }
        catch(ClassCastException e)
        {
            return false;
        }
        return this.delegate.containsValue(v1);
    }
    
    @Override
    public V get(Object key)
    {
        K1 k1;
        try
        {
            k1 = functionKey.apply(RuntimeUtil.cast(key));
        }
        catch(ClassCastException e)
        {
            return null;
        }
        V1 v1 = this.delegate.get(k1);
        if(v1==null)
            return null;
        return functionValue.inverse().apply(v1);
    }
    
    @Override
    public V put(K key, V value)
    {
        this.modifyMonitor.onModify();
        V1 result = this.delegate.put(functionKey.apply(key), functionValue.apply(value));
        this.modifyMonitor.markDirty();
        if(result==null)
            return null;
        return functionValue.inverse().apply(result);
    }
    
    @Override
    public V remove(Object key)
    {
        K1 k1;
        try
        {
            k1 = functionKey.apply(RuntimeUtil.cast(key));
        }
        catch(ClassCastException e)
        {
            return null;
        }
        this.modifyMonitor.onModify();
        V1 result = this.delegate.remove(k1);
        this.modifyMonitor.markDirty();
        if(result==null)
            return null;
        return functionValue.inverse().apply(result);
    }
    
    @SuppressWarnings("NullableProblems")
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
    
    @SuppressWarnings("NullableProblems")
    @Override
    public Set<K> keySet()
    {
        return new SetProxy<>(this.delegate.keySet(), this.functionKey, this.modifyMonitor);
    }
    
    @SuppressWarnings("NullableProblems")
    @Override
    public Collection<V> values()
    {
        return new CollectionProxy<>(this.delegate.values(), this.functionValue, this.modifyMonitor);
    }
    
    @SuppressWarnings("NullableProblems")
    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return new SetProxy<>(this.delegate.entrySet(), new InvertibleFunction<>(x->new MapEntry<>(functionKey.apply(x.getKey()), functionValue.apply(x.getValue())), x->new MapEntry<>(functionKey.inverse().apply(x.getKey()), functionValue.inverse().apply(x.getValue()))), this.modifyMonitor);
    }
}
