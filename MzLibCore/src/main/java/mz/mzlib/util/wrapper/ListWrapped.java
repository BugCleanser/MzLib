package mz.mzlib.util.wrapper;

import java.util.AbstractList;
import java.util.List;
import java.util.function.Function;

public class ListWrapped<T extends WrapperObject> extends AbstractList<Object>
{
    public List<T> delegate;
    public Function<Object,T> wrapperCreator;
    public ListWrapped(List<T> delegate, Function<Object,T> wrapperCreator)
    {
        this.delegate = delegate;
        this.wrapperCreator = wrapperCreator;
    }

    @Override
    public Object get(int index)
    {
        return this.delegate.get(index).getWrapped();
    }

    @Override
    public Object set(int index, Object element)
    {
        return this.delegate.set(index, wrapperCreator.apply(element)).getWrapped();
    }

    @Override
    public void add(int index, Object element)
    {
        this.delegate.add(index, wrapperCreator.apply(element));
    }

    @Override
    public Object remove(int index)
    {
        return this.delegate.remove(index).getWrapped();
    }

    @Override
    public int size()
    {
        return this.delegate.size();
    }
}
