package mz.mzlib.util.wrapper;

import mz.mzlib.util.RuntimeUtil;

import java.util.Iterator;
import java.util.function.Function;

public class IteratorWrapped<T> implements Iterator<T>
{
    public Iterator<? extends WrapperObject> delegate;
    public Function<Object, T> wrapperCreator;
    
    public IteratorWrapped(Iterator<? extends WrapperObject> delegate, Function<Object, T> wrapperCreator)
    {
        this.delegate = delegate;
        this.wrapperCreator = wrapperCreator;
    }
    
    @Override
    public boolean hasNext()
    {
        return delegate.hasNext();
    }
    @Override
    public T next()
    {
        return RuntimeUtil.cast(delegate.next().getWrapped());
    }
    
    public static <T> Iterable<T> iterable(Iterable<? extends WrapperObject> delegate, Function<Object, T> wrapperCreator)
    {
        return ()->new IteratorWrapped<>(delegate.iterator(), wrapperCreator);
    }
}
