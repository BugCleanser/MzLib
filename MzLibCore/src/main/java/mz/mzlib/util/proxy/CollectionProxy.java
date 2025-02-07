package mz.mzlib.util.proxy;

import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.ModifyMonitor;
import mz.mzlib.util.RuntimeUtil;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public class CollectionProxy<T, U> extends AbstractCollection<T>
{
    Collection<U> delegate;
    InvertibleFunction<T, U> function;
    ModifyMonitor modifyMonitor;
    
    public CollectionProxy(Collection<U> delegate, InvertibleFunction<T, U> function, ModifyMonitor modifyMonitor)
    {
        this.delegate = delegate;
        this.function = function;
        this.modifyMonitor = modifyMonitor;
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
    public boolean contains(Object o)
    {
        U k1;
        try
        {
            k1 = this.function.apply(RuntimeUtil.cast(o));
        }
        catch(ClassCastException ignored)
        {
            return false;
        }
        return this.delegate.contains(k1);
    }
    
    @Override
    public boolean add(T t)
    {
        this.modifyMonitor.onModify();
        boolean result = this.delegate.add(this.function.apply(t));
        this.modifyMonitor.markDirty();
        return result;
    }
    
    @Override
    public boolean remove(Object o)
    {
        U k1;
        try
        {
            k1 = this.function.apply(RuntimeUtil.cast(o));
        }
        catch(ClassCastException ignored)
        {
            return false;
        }
        this.modifyMonitor.onModify();
        boolean result = this.delegate.remove(k1);
        this.modifyMonitor.markDirty();
        return result;
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
    public Iterator<T> iterator()
    {
        return new IteratorProxy<>(this.delegate.iterator(), this.function.inverse(), this.modifyMonitor);
    }
}
