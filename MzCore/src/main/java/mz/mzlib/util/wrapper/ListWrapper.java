package mz.mzlib.util.wrapper;

import mz.mzlib.util.RuntimeUtil;

import java.util.AbstractList;
import java.util.List;
import java.util.function.Function;

public class ListWrapper<T extends WrapperObject> extends AbstractList<T>
{
    public List<?> delegate;
    public Function<Object,T> wrapperCreator;
    public ListWrapper(List<?> delegate, Function<Object,T> wrapperCreator)
    {
        this.delegate = delegate;
        this.wrapperCreator = wrapperCreator;
    }

    @Override
    public T get(int index)
    {
        return this.wrapperCreator.apply(this.delegate.get(index));
    }

    @Override
    public T set(int index, T element)
    {
        return this.wrapperCreator.apply(this.delegate.set(index, RuntimeUtil.cast(element.getWrapped())));
    }

    @Override
    public void add(int index, T element)
    {
        this.delegate.add(index, RuntimeUtil.cast(element.getWrapped()));
    }

    @Override
    public T remove(int index)
    {
        return this.wrapperCreator.apply(this.delegate.remove(index));
    }

    @Override
    public int size()
    {
        return this.delegate.size();
    }
}
