package mz.mzlib.util;

import javax.management.Notification;
import javax.management.NotificationListener;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class WeakRefMap<K, V> extends AbstractMap<K, V>
{
    public Map<WeakRef<K>, V> delegate;
    public GcListener gcListener;
    public WeakRefMap(Map<WeakRef<K>, V> delegate)
    {
        this.delegate = delegate;
        RuntimeUtil.addGcListener(this.gcListener = new GcListener(this));
    }
    public WeakRefMap()
    {
        this(new HashMap<>());
    }
    
    public void gc()
    {
        List<WeakRef<K>> garbage = new ArrayList<>();
        for(Map.Entry<WeakRef<K>, V> entry: this.delegate.entrySet())
        {
            if(entry.getKey().get()==null)
                garbage.add(entry.getKey());
        }
        for(WeakRef<K> ref: garbage)
            this.delegate.remove(ref);
    }
    
    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        return new EntrySet<>(this.delegate.entrySet());
    }
    
    @Override
    public V get(Object key)
    {
        return this.delegate.get(new WeakRef<>(key));
    }
    
    @Override
    public V put(K key, V value)
    {
        return this.delegate.put(new WeakRef<>(key), value);
    }
    
    @Override
    public V remove(Object key)
    {
        return this.delegate.remove(new WeakRef<>(key));
    }
    
    @Override
    public boolean containsKey(Object key)
    {
        return this.delegate.containsKey(new WeakRef<>(key));
    }
    
    @Override
    public boolean containsValue(Object value)
    {
        return this.delegate.containsValue(value);
    }
    
    @Override
    public int size()
    {
        return this.delegate.size();
    }
    
    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
    {
        return this.delegate.compute(new WeakRef<>(key), (k, v)->remappingFunction.apply(k.get(), v));
    }
    
    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)
    {
        return this.delegate.computeIfAbsent(new WeakRef<>(key), k->mappingFunction.apply(k.get()));
    }
    
    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
    {
        return this.delegate.computeIfPresent(new WeakRef<>(key), (k, v)->remappingFunction.apply(k.get(), v));
    }
    
    public static class GcListener implements NotificationListener
    {
        public WeakRef<WeakRefMap<?, ?>> refMap;
        public GcListener(WeakRefMap<?, ?> map)
        {
            this.refMap = new WeakRef<>(map);
        }
        
        @Override
        public void handleNotification(Notification notification, Object handback)
        {
            WeakRefMap<?, ?> map = refMap.get();
            if(map!=null)
                map.gc();
            else
                RuntimeUtil.removeGcListener(this);
        }
    }
    
    public static class Entry<K, V> implements Map.Entry<K, V>
    {
        public Map.Entry<WeakRef<K>, V> delegate;
        
        public Entry(Map.Entry<WeakRef<K>, V> delegate)
        {
            this.delegate = delegate;
        }
        
        @Override
        public K getKey()
        {
            return this.delegate.getKey().get();
        }
        @Override
        public V getValue()
        {
            return this.delegate.getValue();
        }
        @Override
        public V setValue(V value)
        {
            return this.delegate.setValue(value);
        }
    }
    
    public static class EntryIterator<K, V> implements java.util.Iterator<Map.Entry<K, V>>
    {
        public Iterator<Map.Entry<WeakRef<K>, V>> delegate;
        
        public EntryIterator(Iterator<Map.Entry<WeakRef<K>, V>> delegate)
        {
            this.delegate = delegate;
        }
        @Override
        public boolean hasNext()
        {
            return this.delegate.hasNext();
        }
        
        @Override
        public Entry<K, V> next()
        {
            return new Entry<>(this.delegate.next());
        }
    }
    
    public static class EntrySet<K, V> extends AbstractSet<Map.Entry<K, V>>
    {
        public Set<Map.Entry<WeakRef<K>, V>> delegate;
        
        public EntrySet(Set<Map.Entry<WeakRef<K>, V>> delegate)
        {
        }
        
        @Override
        public EntryIterator<K, V> iterator()
        {
            return new EntryIterator<>(delegate.iterator());
        }
        @Override
        public int size()
        {
            return this.delegate.size();
        }
        
        @Override
        public boolean contains(Object o)
        {
            if(!(o instanceof Map.Entry))
                return false;
            return this.delegate.contains(new MapEntry<>(new WeakRef<>(((Map.Entry<?, ?>)o).getKey()), ((Map.Entry<?, ?>)o).getValue()));
        }
        
        @Override
        public boolean add(Map.Entry<K, V> kvEntry)
        {
            return this.delegate.add(new MapEntry<>(new WeakRef<>(kvEntry.getKey()), kvEntry.getValue()));
        }
        
        @Override
        public boolean remove(Object o)
        {
            if(!(o instanceof Map.Entry))
                return false;
            return this.delegate.remove(new MapEntry<>(new WeakRef<>(((Map.Entry<?, ?>)o).getKey()), ((Map.Entry<?, ?>)o).getValue()));
        }
    }
}
