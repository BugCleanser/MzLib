package mz.mzlib.util.proxy;

import jakarta.annotation.Nonnull;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.ModifyMonitor;
import mz.mzlib.util.RuntimeUtil;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public interface CollectionProxy<T, U> extends Collection<T>
{
    Collection<U> getDelegate();
    FunctionInvertible<U, T> getFunction();
    ModifyMonitor getModifyMonitor();
    
    @Override
    default int size()
    {
        return this.getDelegate().size();
    }

    @Override
    default boolean isEmpty()
    {
        return this.getDelegate().isEmpty();
    }

    @Override
    default boolean contains(Object o)
    {
        U k1;
        try
        {
            k1 = this.getFunction().inverse().apply(RuntimeUtil.cast(o));
        }
        catch(ClassCastException ignored)
        {
            return false;
        }
        return this.getDelegate().contains(k1);
    }

    @Override
    default boolean add(T t)
    {
        this.getModifyMonitor().onModify();
        boolean result = this.getDelegate().add(this.getFunction().inverse().apply(t));
        this.getModifyMonitor().markDirty();
        return result;
    }

    @Override
    default boolean remove(Object o)
    {
        U k1;
        try
        {
            k1 = this.getFunction().inverse().apply(RuntimeUtil.cast(o));
        }
        catch(ClassCastException ignored)
        {
            return false;
        }
        this.getModifyMonitor().onModify();
        boolean result = this.getDelegate().remove(k1);
        this.getModifyMonitor().markDirty();
        return result;
    }

    @Override
    default boolean addAll(Collection<? extends T> c)
    {
        this.getModifyMonitor().onModify();
        boolean result = false;
        for(T t : c)
        {
            this.getDelegate().add(this.getFunction().inverse().apply(t));
            result = true;
        }
        this.getModifyMonitor().markDirty();
        return result;
    }

    @Override
    default void clear()
    {
        this.getModifyMonitor().onModify();
        this.getDelegate().clear();
        this.getModifyMonitor().markDirty();
    }

    @Override
    @Nonnull
    default Iterator<T> iterator()
    {
        return new IteratorProxy<>(this.getDelegate().iterator(), this.getFunction(), this.getModifyMonitor());
    }

    static <T, U> CollectionProxy<T, U> of(Collection<U> delegate, FunctionInvertible<U, T> function, ModifyMonitor modifyMonitor)
    {
        return new Impl<>(delegate, function, modifyMonitor);
    }
    static <T, U> CollectionProxy<T, U> of(Collection<U> delegate, FunctionInvertible<U, T> function)
    {
        return of(delegate, function, ModifyMonitor.Empty.instance);
    }
    class Impl<T, U> extends AbstractCollection<T> implements CollectionProxy<T, U>
    {
        Collection<U> delegate;
        FunctionInvertible<U, T> function;
        ModifyMonitor modifyMonitor;
        public Impl(Collection<U> delegate, FunctionInvertible<U, T> function, ModifyMonitor modifyMonitor)
        {
            this.delegate = delegate;
            this.function = function;
            this.modifyMonitor = modifyMonitor;
        }
        public Impl(Collection<U> delegate, FunctionInvertible<U, T> function)
        {
            this(delegate, function, ModifyMonitor.Empty.instance);
        }

        @Override
        public Collection<U> getDelegate()
        {
            return this.delegate;
        }
        @Override
        public FunctionInvertible<U, T> getFunction()
        {
            return this.function;
        }
        @Override
        public ModifyMonitor getModifyMonitor()
        {
            return this.modifyMonitor;
        }
        @Override
        public int size()
        {
            return CollectionProxy.super.size();
        }
        @Override
        public boolean isEmpty()
        {
            return CollectionProxy.super.isEmpty();
        }
        @Override
        public boolean contains(Object o)
        {
            return CollectionProxy.super.contains(o);
        }
        @Override
        public boolean add(T t)
        {
            return CollectionProxy.super.add(t);
        }
        @Override
        public boolean remove(Object o)
        {
            return CollectionProxy.super.remove(o);
        }
        @Override
        public boolean addAll(@Nonnull Collection<? extends T> c)
        {
            return CollectionProxy.super.addAll(c);
        }
        @Override
        public void clear()
        {
            CollectionProxy.super.clear();
        }
        @Override
        @Nonnull
        public Iterator<T> iterator()
        {
            return CollectionProxy.super.iterator();
        }
    }
}
