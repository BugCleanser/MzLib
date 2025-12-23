package mz.mzlib.util;

import jakarta.annotation.Nonnull;

import java.util.*;

public class ListMerged<T> extends AbstractList<T>
{
    List<T> child0, child1;

    public ListMerged(List<T> child0, List<T> child1)
    {
        this.child0 = child0;
        this.child1 = child1;
    }
    public static <T> ListMerged<T> of(List<T> first, List<T> second)
    {
        if(first instanceof RandomAccess && second instanceof RandomAccess)
            return new OfRandomAccess<>(first, second);
        return new ListMerged<>(first, second);
    }
    @SafeVarargs
    public static <T> List<T> of(List<T> ...lists)
    {
        return of(Arrays.asList(lists));
    }
    public static <T> List<T> of(List<? extends List<T>> lists)
    {
        if(lists.isEmpty())
            return Collections.emptyList();
        if(lists.size() == 1)
            return lists.get(0);
        if(lists.size() == 2)
            return of(lists.get(0), lists.get(1));
        return of(lists.get(0), of(lists.subList(1, lists.size())));
    }

    public List<T> getChild0()
    {
        return this.child0;
    }
    public List<T> getChild1()
    {
        return this.child1;
    }

    @Override
    public T get(int index)
    {
        if (index < this.getChild0().size())
            return this.getChild0().get(index);
        else
            return this.getChild1().get(index - this.getChild0().size());
    }

    @Override
    public T set(int index, T element)
    {
        if (index < this.getChild0().size())
            return this.getChild0().set(index, element);
        else
            return this.getChild1().set(index - this.getChild0().size(), element);
    }

    @Override
    public int size()
    {
        return this.getChild0().size() + this.getChild1().size();
    }

    @Override
    public boolean contains(Object o)
    {
        return this.getChild0().contains(o) || this.getChild1().contains(o);
    }

    @Override
    public int indexOf(Object o)
    {
        int result = this.getChild0().indexOf(o);
        if(result != -1)
            return result;
        result = this.getChild1().indexOf(o);
        if(result != -1)
            return this.getChild0().size() + result;
        return -1;
    }

    @Override
    public int lastIndexOf(Object o)
    {
        int result = this.getChild1().lastIndexOf(o);
        if(result != -1)
            return this.getChild0().size() + result;
        return this.getChild0().lastIndexOf(o);
    }

    @Override
    @Nonnull
    public Iterator<T> iterator()
    {
        return new Itr<>(this.getChild0().iterator(), this.getChild1().iterator());
    }

    @Override
    @Nonnull
    public ListIterator<T> listIterator(int index)
    {
        int child0Size = this.getChild0().size();
        if(index < child0Size)
            return new ListItr<>(this.getChild0().listIterator(index), this.getChild1().listIterator(), child0Size);
        else
            return new ListItr<>(this.getChild0().listIterator(child0Size), this.getChild1().listIterator(index - child0Size), child0Size);
    }

    static class Itr<T, I extends Iterator<T>> implements Iterator<T>
    {
        final I child0;
        final I child1;
        public Itr(I child0, I child1)
        {
            this.child0 = child0;
            this.child1 = child1;
        }
        @Override
        public boolean hasNext()
        {
            return this.child0.hasNext() || this.child1.hasNext();
        }
        @Override
        public T next()
        {
            if(this.child0.hasNext())
                return this.child0.next();
            else
                return this.child1.next();
        }
    }
    static class ListItr<T> extends Itr<T, ListIterator<T>> implements ListIterator<T>
    {
        int child0Size;
        public ListItr(ListIterator<T> first, ListIterator<T> second, int child0Size)
        {
            super(first, second);
            this.child0Size = child0Size;
        }
        @Override
        public boolean hasPrevious()
        {
            return this.child0.hasPrevious() || this.child1.hasPrevious();
        }
        @Override
        public T previous()
        {
            if(this.child0.hasNext())
                return this.child0.previous();
            else
                return this.child1.previous();
        }
        @Override
        public int nextIndex()
        {
            if(this.child0.hasNext())
                return this.child0.nextIndex();
            else
                return this.child0Size + this.child1.nextIndex();
        }
        @Override
        public int previousIndex()
        {
            if(this.child0.hasNext())
                return this.child0.previousIndex();
            else
                return this.child0Size + this.child1.previousIndex();
        }
        @Override
        public void set(T e)
        {
            if(this.child0.hasNext())
                this.child0.set(e);
            else
                this.child1.set(e);
        }
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        @Override
        public void add(T t)
        {
            throw new UnsupportedOperationException();
        }
    }

    static class OfRandomAccess<T> extends ListMerged<T> implements RandomAccess
    {
        public OfRandomAccess(List<T> first, List<T> second)
        {
            super(first, second);
        }
    }
}
