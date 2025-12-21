package mz.mzlib.util.proxy;

import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.ModifyMonitor;
import mz.mzlib.util.RuntimeUtil;

import java.util.ListIterator;

public class ListIteratorProxy<T, U> extends IteratorProxy<T, U> implements ListIterator<T>
{
    public ListIteratorProxy(ListIterator<U> delegate, FunctionInvertible<U, T> function, ModifyMonitor modifyMonitor)
    {
        super(delegate, function, modifyMonitor);
    }
    public ListIteratorProxy(ListIterator<U> delegate, FunctionInvertible<U, T> function)
    {
        super(delegate, function);
    }

    @Override
    public ListIterator<U> getDelegate()
    {
        return RuntimeUtil.cast(super.getDelegate());
    }
    @Override
    public FunctionInvertible<U, T> getFunction()
    {
        return RuntimeUtil.cast(super.getFunction());
    }

    @Override
    public boolean hasPrevious()
    {
        return this.getDelegate().hasPrevious();
    }
    @Override
    public T previous()
    {
        return this.function.apply(this.getDelegate().previous());
    }
    @Override
    public int nextIndex()
    {
        return this.getDelegate().nextIndex();
    }
    @Override
    public int previousIndex()
    {
        return this.getDelegate().previousIndex();
    }
    @Override
    public void set(T t)
    {
        this.modifyMonitor.onModify();
        this.getDelegate().set(this.getFunction().inverse().apply(t));
        this.modifyMonitor.markDirty();

    }
    @Override
    public void add(T t)
    {
        this.modifyMonitor.onModify();
        this.getDelegate().add(this.getFunction().inverse().apply(t));
        this.modifyMonitor.markDirty();
    }
}
