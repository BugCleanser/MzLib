package mz.mzlib.util.proxy;

import jakarta.annotation.Nonnull;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.ModifyMonitor;
import mz.mzlib.util.RuntimeUtil;

import java.util.*;

public class ListProxy<T, U> extends AbstractList<T> implements CollectionProxy<T, U>
{
    List<U> delegate;
    FunctionInvertible<U, T> function;
    ModifyMonitor modifyMonitor;
    public ListProxy(List<U> delegate, FunctionInvertible<U, T> function, ModifyMonitor modifyMonitor)
    {
        this.delegate = delegate;
        this.function = function;
        this.modifyMonitor = modifyMonitor;
    }
    public ListProxy(List<U> delegate, FunctionInvertible<U, T> function)
    {
        this(delegate, function, ModifyMonitor.Empty.instance);
    }

    @Override
    public List<U> getDelegate()
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

    @Override
    public T get(int index)
    {
        return this.getFunction().apply(this.getDelegate().get(index));
    }

    @Override
    public T set(int index, T element)
    {
        this.getModifyMonitor().onModify();
        T result = this.getFunction().apply(this.getDelegate().set(index, this.getFunction().inverse().apply(element)));
        this.getModifyMonitor().markDirty();
        return result;
    }

    @Override
    public void add(int index, T element)
    {
        this.getModifyMonitor().onModify();
        this.getDelegate().add(index, this.getFunction().inverse().apply(element));
        this.getModifyMonitor().markDirty();
    }

    @Override
    public T remove(int index)
    {
        this.getModifyMonitor().onModify();
        T result = this.getFunction().apply(this.getDelegate().remove(index));
        this.getModifyMonitor().markDirty();
        return result;
    }

    @Override
    public int indexOf(Object o)
    {
        U u;
        try
        {
            u = this.getFunction().inverse().apply(RuntimeUtil.cast(o));
        }
        catch(ClassCastException ignored)
        {
            return -1;
        }
        return this.getDelegate().indexOf(u);
    }

    @Override
    public int lastIndexOf(Object o)
    {
        U u;
        try
        {
            u = this.getFunction().inverse().apply(RuntimeUtil.cast(o));
        }
        catch(ClassCastException ignored)
        {
            return -1;
        }
        return this.getDelegate().lastIndexOf(u);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c)
    {
        rangeCheckForAdd(index);
        boolean modified = false;
        for(T e : c)
        {
            this.getDelegate().add(index++, this.getFunction().inverse().apply(e));
            modified = true;
        }
        return modified;
    }

    @Override
    @Nonnull
    public ListIterator<T> listIterator()
    {
        return this.listIterator(0);
    }

    @Override
    @Nonnull
    public ListIterator<T> listIterator(int index)
    {
        return new ListIteratorProxy<>(this.getDelegate().listIterator(index), this.getFunction(), this.getModifyMonitor());
    }

    @Override
    @Nonnull
    public List<T> subList(int fromIndex, int toIndex)
    {
        return new ListProxy<>(this.getDelegate().subList(fromIndex, toIndex), this.getFunction(), this.getModifyMonitor());
    }

    private void rangeCheckForAdd(int index)
    {
        if(index < 0 || index > size())
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index)
    {
        return "Index: " + index + ", Size: " + size();
    }
}
