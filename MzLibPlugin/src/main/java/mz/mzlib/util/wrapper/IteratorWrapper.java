package mz.mzlib.util.wrapper;

import java.util.Iterator;
import java.util.function.Function;

public class IteratorWrapper<T extends WrapperObject> implements Iterator<T>
{
    public Iterator<?> delegate;
    public Function<Object,T> wrapperCreator;
    public IteratorWrapper(Iterator<?> iterator, Function<Object,T> wrapperCreator)
    {
        this.delegate =iterator;
        this.wrapperCreator=wrapperCreator;
    }

    @Override
    public boolean hasNext()
    {
        return delegate.hasNext();
    }

    @Override
    public T next()
    {
        return wrapperCreator.apply(delegate.next());
    }
}
