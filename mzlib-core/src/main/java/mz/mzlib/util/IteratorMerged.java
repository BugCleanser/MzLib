package mz.mzlib.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class IteratorMerged<T> implements Iterator<T>
{
    List<Iterator<T>> children;
    IteratorMerged(List<Iterator<T>> children)
    {
        this.children = children;
    }

    public static <T> IteratorMerged<T> of(List<Iterator<T>> children)
    {
        return new IteratorMerged<>(children);
    }
    @SafeVarargs
    public static <T> IteratorMerged<T> of(Iterator<T> ...children)
    {
        return of(Arrays.asList(children));
    }
    public static <T> Iterable<T> iterable(List<Iterable<T>> children)
    {
        return () -> of(children.stream().map(Iterable::iterator).collect(Collectors.toList()));
    }
    @SafeVarargs
    public static <T> Iterable<T> iterable(Iterable<T> ...children)
    {
        return iterable(Arrays.asList(children));
    }

    @Override
    public boolean hasNext()
    {
        for(Iterator<T> child : this.children)
        {
            if(child.hasNext())
                return true;
        }
        return false;
    }
    @Override
    public T next()
    {
        for(Iterator<T> child : this.children)
        {
            if(child.hasNext())
                return child.next();
        }
        throw new NoSuchElementException();
    }
}
