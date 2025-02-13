package mz.mzlib.util.proxy;

import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.ModifyMonitor;
import mz.mzlib.util.RuntimeUtil;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class ListProxy<T, U> extends CollectionProxy<T, U> implements List<T>
{
    public ListProxy(List<U> delegate, InvertibleFunction<U, T> function, ModifyMonitor modifyMonitor)
    {
        super(delegate, function, modifyMonitor);
    }
    public ListProxy(List<U> delegate, InvertibleFunction<U, T> function)
    {
        super(delegate, function);
    }
    
    public List<U> getDelegate()
    {
        return (List<U>)super.getDelegate();
    }
    
    @Override
    public T get(int index)
    {
        return this.function.apply(this.getDelegate().get(index));
    }
    
    @Override
    public T set(int index, T element)
    {
        this.modifyMonitor.onModify();
        T result = this.function.apply(this.getDelegate().set(index, this.function.inverse().apply(element)));
        this.modifyMonitor.markDirty();
        return result;
    }
    
    @Override
    public void add(int index, T element)
    {
        this.modifyMonitor.onModify();
        this.getDelegate().add(index, this.function.inverse().apply(element));
        this.modifyMonitor.markDirty();
    }
    
    @Override
    public T remove(int index)
    {
        this.modifyMonitor.onModify();
        T result = this.function.apply(this.getDelegate().remove(index));
        this.modifyMonitor.markDirty();
        return result;
    }
    
    @Override
    public int indexOf(Object o)
    {
        U u;
        try
        {
            u = this.function.inverse().apply(RuntimeUtil.cast(o));
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
            u = this.function.inverse().apply(RuntimeUtil.cast(o));
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
        for(T e: c)
        {
            this.getDelegate().add(index++, this.function.inverse().apply(e));
            modified = true;
        }
        return modified;
    }
    
    @Override
    public ListIterator<T> listIterator()
    {
        return this.listIterator(0);
    }
    
    @Override
    public ListIterator<T> listIterator(int index)
    {
        return new ListIteratorProxy<>(this.getDelegate().listIterator(index), this.function, this.modifyMonitor);
    }
    
    @Override
    public List<T> subList(int fromIndex, int toIndex)
    {
        return new ListProxy<>(this.getDelegate().subList(fromIndex, toIndex), this.function, this.modifyMonitor);
    }
    
    private void rangeCheckForAdd(int index)
    {
        if(index<0 || index>size())
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
    
    private String outOfBoundsMsg(int index)
    {
        return "Index: "+index+", Size: "+size();
    }
}
