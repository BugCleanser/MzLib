package mz.mzlib.util;

import jakarta.annotation.Nonnull;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MapConcurrentWeakHash<K, V> extends AbstractMap<K, V>
{
    ConcurrentHashMap<RefWeak<K>, V> delegate = new ConcurrentHashMap<>();
    WeakHashMap<WeakReference<K>, RefWeak<K>> keyMap = new WeakHashMap<>();
    ReferenceQueue<K> referenceQueue = new ReferenceQueue<>();
    Queue<WeakReference<K>> cleanQueue = new ConcurrentLinkedQueue<>();

    public MapConcurrentWeakHash()
    {
    }

    @Override
    public int size()
    {
        this.clean();
        return this.delegate.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key)
    {
        return this.delegate.containsKey(new RefWeak<>(key));
    }

    @Override
    public boolean containsValue(Object value)
    {
        return this.delegate.containsValue(value);
    }

    @Override
    public V get(Object key)
    {
        this.autoClean();
        return this.delegate.get(new RefWeak<>(key));
    }

    @Override
    public V put(K key, V value)
    {
        this.autoClean();
        RefWeak<K> k = new RefWeak<>(key, this.referenceQueue);
        V last = this.delegate.put(k, value);
        if(last == null)
            synchronized(this)
            {
                this.keyMap.put(k.getDelegate(), k);
            }
        return last;
    }

    @Override
    public V remove(Object key)
    {
        this.autoClean();
        return this.delegate.remove(new RefWeak<>(key));
    }
    @Override
    public boolean remove(Object key, Object value)
    {
        this.autoClean();
        return this.delegate.remove(new RefWeak<>(key), value);
    }

    @Override
    public synchronized void clear()
    {
        this.delegate.clear();
        this.keyMap.clear();
        while(this.referenceQueue.poll() != null)
            RuntimeUtil.nop();
        this.cleanQueue.clear();
    }

    @Override
    @Nonnull
    public Collection<V> values()
    {
        this.autoClean();
        return this.delegate.values();
    }

    @Override
    @Nonnull
    public Set<Map.Entry<K, V>> entrySet()
    {
        return this.new SetEntry();
    }
    class SetEntry extends SetBase<Map.Entry<K, V>>
    {
        @Override
        public boolean contains(Object o)
        {
            if(!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            return Objects.equals(MapConcurrentWeakHash.this.get(e.getKey()), e.getValue());
        }
        @Override
        public boolean remove(Object o)
        {
            if(!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            return MapConcurrentWeakHash.this.remove(e.getKey(), e.getValue());
        }
        @Override
        @Nonnull
        public Iterator<Map.Entry<K, V>> iterator()
        {
            return MapConcurrentWeakHash.this.new IteratorEntry();
        }
    }

    @Override
    @Nonnull
    public Set<K> keySet()
    {
        return this.new SetKey();
    }
    class SetKey extends SetBase<K>
    {
        @Override
        public boolean contains(Object o)
        {
            return MapConcurrentWeakHash.this.containsKey(o);
        }
        @Override
        public boolean remove(Object o)
        {
            return MapConcurrentWeakHash.this.remove(o) != null;
        }
        @Override
        @Nonnull
        public Iterator<K> iterator()
        {
            return MapConcurrentWeakHash.this.new IteratorKey();
        }
    }

    abstract class SetBase<T> extends AbstractSet<T>
    {
        @Override
        public int size()
        {
            return MapConcurrentWeakHash.this.size();
        }
        @Override
        public boolean isEmpty()
        {
            return MapConcurrentWeakHash.this.isEmpty();
        }
        @Override
        public void clear()
        {
            MapConcurrentWeakHash.this.clear();
        }
    }

    class Entry implements Map.Entry<K, V>
    {
        Map.Entry<RefWeak<K>, V> delegate;
        K key;
        public Entry(Map.Entry<RefWeak<K>, V> delegate, K key)
        {
            this.delegate = delegate;
            this.key = key;
        }
        @Override
        public K getKey()
        {
            return this.key;
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

        public boolean equals(Object o)
        {
            if(this == o)
                return true;
            if(!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            return Objects.equals(this.getKey(), e.getKey()) && Objects.equals(this.getValue(), e.getValue());
        }
        public int hashCode()
        {
            return Objects.hashCode(this.getKey()) ^ Objects.hashCode(this.getValue());
        }
        public String toString() {
            return this.getKey() + "=" + this.getValue();
        }
    }

    class IteratorEntry extends IteratorBase<Map.Entry<K, V>>
    {
        @Override
        public Map.Entry<K, V> next()
        {
            return this.nextEntry();
        }
    }
    class IteratorKey extends IteratorBase<K>
    {
        @Override
        public K next()
        {
            return this.nextEntry().getKey();
        }
    }
    abstract class IteratorBase<T> implements Iterator<T>
    {
        Iterator<Map.Entry<RefWeak<K>, V>> delegate = MapConcurrentWeakHash.this.delegate.entrySet().iterator();
        K last;
        Entry next;
        @Override
        public boolean hasNext()
        {
            if(this.next != null)
                return true;
            while(this.delegate.hasNext())
            {
                Map.Entry<RefWeak<K>, V> entry = this.delegate.next();
                K key = entry.getKey().get();
                if(key == null)
                {
                    this.delegate.remove();
                    continue;
                }
                this.next = new Entry(entry, key);
                return true;
            }
            return false;
        }
        public Entry nextEntry()
        {
            if(!this.hasNext())
                throw new NoSuchElementException();
            Entry result = this.next;
            this.next = null;
            this.last = result.getKey();
            return result;
        }
        @Override
        public void remove()
        {
            if(this.last == null)
                throw new IllegalStateException();
            MapConcurrentWeakHash.this.remove(this.last);
        }
    }

    void autoClean()
    {
        this.clean1();
        if(cleanQueue.size() > 5)
            this.clean2();
    }
    void clean()
    {
        this.clean1();
        this.clean2();
    }
    void clean1()
    {
        Reference<? extends K> ref;
        while((ref = this.referenceQueue.poll()) != null)
        {
            this.cleanQueue.offer(RuntimeUtil.cast(ref));
        }
    }
    synchronized void clean2()
    {
        Reference<? extends K> ref;
        while((ref = this.cleanQueue.poll()) != null)
        {
            RefWeak<K> key = this.keyMap.remove(ref);
            if(key != null)
                this.delegate.remove(key);
        }
    }
}
