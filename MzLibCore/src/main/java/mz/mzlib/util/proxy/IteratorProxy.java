package mz.mzlib.util.proxy;

import mz.mzlib.util.ModifyMonitor;

import java.util.Iterator;
import java.util.function.Function;

public class IteratorProxy<T, U> implements Iterator<T>
{
    Iterator<U> delegate;
    Function<U, T> function;
    ModifyMonitor modifyMonitor;
    public IteratorProxy(Iterator<U> delegate, Function<U, T> function, ModifyMonitor modifyMonitor)
    {
        this.delegate = delegate;
        this.function = function;
        this.modifyMonitor = modifyMonitor;
    }
    
    @Override
    public boolean hasNext()
    {
        return this.delegate.hasNext();
    }
    
    @Override
    public T next()
    {
        return this.function.apply(this.delegate.next());
    }
    
    @Override
    public void remove()
    {
        this.modifyMonitor.onModify();
        this.delegate.remove();
        this.modifyMonitor.markDirty();
    }
}
