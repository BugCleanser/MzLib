package mz.mzlib.util.proxy;

import mz.mzlib.util.ModifyMonitor;

import java.util.Iterator;
import java.util.function.Function;

public class IteratorProxy<T, U> implements Iterator<T>
{
    Iterator<U> delegate;
    Function<? super U, ? extends T> function;
    ModifyMonitor modifyMonitor;
    public IteratorProxy(Iterator<U> delegate, Function<? super U, ? extends T> function, ModifyMonitor modifyMonitor)
    {
        this.delegate = delegate;
        this.function = function;
        this.modifyMonitor = modifyMonitor;
    }
    public IteratorProxy(Iterator<U> delegate, Function<? super U, ? extends T> function)
    {
        this(delegate, function, ModifyMonitor.Empty.instance);
    }
    
    public Iterator<? extends U> getDelegate()
    {
        return this.delegate;
    }
    public Function<? super U, ? extends T> getFunction()
    {
        return this.function;
    }
    
    @Override
    public boolean hasNext()
    {
        return this.getDelegate().hasNext();
    }
    
    @Override
    public T next()
    {
        return this.getFunction().apply(this.getDelegate().next());
    }
    
    @Override
    public void remove()
    {
        this.modifyMonitor.onModify();
        this.getDelegate().remove();
        this.modifyMonitor.markDirty();
    }
    
    public static <T, U> Iterable<T> iterable(Iterable<U> iterator, Function<? super U, ? extends T> function, ModifyMonitor modifyMonitor)
    {
        return ()->new IteratorProxy<>(iterator.iterator(), function, modifyMonitor);
    }
    public static <T, U> Iterable<T> iterable(Iterable<U> iterator, Function<? super U, ? extends T> function)
    {
        return iterable(iterator, function, ModifyMonitor.Empty.instance);
    }
}
