package mz.mzlib.util.proxy;

import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.ModifyMonitor;
import mz.mzlib.util.RuntimeUtil;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class CollectionProxy<T, U> extends AbstractCollection<T>
{
    protected Collection<U> delegate;
    protected FunctionInvertible<U, T> function;
    protected ModifyMonitor modifyMonitor;

    public CollectionProxy(Collection<U> delegate, FunctionInvertible<U, T> function, ModifyMonitor modifyMonitor)
    {
        this.delegate = delegate;
        this.function = function;
        this.modifyMonitor = modifyMonitor;
    }
    public CollectionProxy(Collection<U> delegate, FunctionInvertible<U, T> function)
    {
        this(delegate, function, ModifyMonitor.Empty.instance);
    }

    public Collection<U> getDelegate()
    {
        return this.delegate;
    }

    public FunctionInvertible<U, T> getFunction()
    {
        return this.function;
    }

    @Override
    public int size()
    {
        return this.getDelegate().size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.getDelegate().isEmpty();
    }

    @Override
    public boolean contains(Object o)
    {
        U k1;
        try
        {
            k1 = this.getFunction().inverse().apply(RuntimeUtil.cast(o));
        }
        catch(ClassCastException ignored)
        {
            return false;
        }
        return this.getDelegate().contains(k1);
    }

    @Override
    public boolean add(T t)
    {
        this.modifyMonitor.onModify();
        boolean result = this.getDelegate().add(this.getFunction().inverse().apply(t));
        this.modifyMonitor.markDirty();
        return result;
    }

    @Override
    public boolean remove(Object o)
    {
        U k1;
        try
        {
            k1 = this.getFunction().inverse().apply(RuntimeUtil.cast(o));
        }
        catch(ClassCastException ignored)
        {
            return false;
        }
        this.modifyMonitor.onModify();
        boolean result = this.getDelegate().remove(k1);
        this.modifyMonitor.markDirty();
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        this.modifyMonitor.onModify();
        boolean result = false;
        for(T t : c)
        {
            this.getDelegate().add(this.getFunction().inverse().apply(t));
            result = true;
        }
        this.modifyMonitor.markDirty();
        return result;
    }

    @Override
    public void clear()
    {
        this.modifyMonitor.onModify();
        this.getDelegate().clear();
        this.modifyMonitor.markDirty();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Iterator<T> iterator()
    {
        return new IteratorProxy<>(this.getDelegate().iterator(), this.getFunction(), this.modifyMonitor);
    }

    @Override
    public int hashCode()
    {
        return this.getDelegate().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj instanceof CollectionProxy)
            return Objects.equals(this.getDelegate(), ((CollectionProxy<?, ?>) obj).getDelegate());
        return this.getDelegate().equals(obj);
    }
}
