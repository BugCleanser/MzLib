package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;

import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiStatus.Experimental
public class ListIndexed<T> extends AbstractList<T>
{
    List<T> delegate;
    Map<T, Integer> indexes;
    
    public ListIndexed(List<T> delegate)
    {
        this.delegate = delegate;
        this.indexes = new HashMap<>();
        for(int i = 0; i < delegate.size(); i++)
        {
            if(this.indexes.put(delegate.get(i), i) != null)
                throw new IllegalArgumentException("Duplicate element: " + delegate);
        }
    }
    
    @Override
    public T get(int index)
    {
        return this.delegate.get(index);
    }
    
    @Override
    public T set(int index, T element)
    {
        T last = this.delegate.set(index, element);
        if(this.indexes.remove(last) == null)
            throw new IllegalStateException();
        if(this.indexes.put(element, index) != null)
            throw new IllegalStateException("Duplicate element: " + element);
        return last;
    }
    
    @Override
    public boolean add(T t)
    {
        this.indexes.put(t, this.size());
        this.delegate.add(t);
        return true;
    }
    
    @Override
    public void add(int index, T element)
    {
        int size = this.size();
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException(Integer.toString(index));
        for(;index < size ; index++)
        {
            element = this.set(index, element);
        }
        this.add(element);
    }
    
    @Override
    public int size()
    {
        return this.delegate.size();
    }
    
    @Override
    public int indexOf(Object o)
    {
        //noinspection SuspiciousMethodCalls
        Integer result = this.indexes.get(o);
        if(result != null)
            return result;
        else
            return -1;
    }
}
