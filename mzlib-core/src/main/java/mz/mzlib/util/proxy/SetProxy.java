package mz.mzlib.util.proxy;

import jakarta.annotation.Nonnull;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.ModifyMonitor;

import java.util.*;

public class SetProxy<T, U> extends AbstractSet<T> implements CollectionProxy<T, U>
{
    Set<U> delegate;
    FunctionInvertible<U, T> function;
    ModifyMonitor modifyMonitor;
    public SetProxy(Set<U> delegate, FunctionInvertible<U, T> function, ModifyMonitor modifyMonitor)
    {
        this.delegate = delegate;
        this.function = function;
        this.modifyMonitor = modifyMonitor;
    }
    public SetProxy(Set<U> delegate, FunctionInvertible<U, T> function)
    {
        this(delegate, function, ModifyMonitor.Empty.instance);
    }

    @Override
    public Set<U> getDelegate()
    {
        return this.delegate;
    }
    @Override
    public FunctionInvertible<U, T> getFunction()
    {
        return this.function;
    }
    @Override
    public ModifyMonitor getModifyMonitor()
    {
        return this.modifyMonitor;
    }
    @Override
    public int size()
    {
        return CollectionProxy.super.size();
    }
    @Override
    public boolean isEmpty()
    {
        return CollectionProxy.super.isEmpty();
    }
    @Override
    public boolean contains(Object o)
    {
        return CollectionProxy.super.contains(o);
    }
    @Override
    public boolean add(T t)
    {
        return CollectionProxy.super.add(t);
    }
    @Override
    public boolean remove(Object o)
    {
        return CollectionProxy.super.remove(o);
    }
    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        return CollectionProxy.super.addAll(c);
    }
    @Override
    public void clear()
    {
        CollectionProxy.super.clear();
    }
    @Override
    @Nonnull
    public Iterator<T> iterator()
    {
        return CollectionProxy.super.iterator();
    }
}
