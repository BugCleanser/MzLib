package mz.mzlib.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface AutoCompletable<T, D> extends Iterable<T>
{
    Pair<T, D> start();
    void complete(T element, D data);
    
    default void accept(Consumer<T> action)
    {
        for(T element: this)
        {
            action.accept(element);
        }
    }
    
    static <T, D> AutoCompletable<T, D> of(Supplier<Pair<T, D>> start, BiConsumer<T, D> complete)
    {
        return new AutoCompletable<T, D>()
        {
            @Override
            public Pair<T, D> start()
            {
                return start.get();
            }
            @Override
            public void complete(T element, D data)
            {
                complete.accept(element, data);
            }
        };
    }
    static <T> AutoCompletable<T, Void> of(Supplier<? extends T> start, Consumer<? super T> complete)
    {
        return of(()->Pair.of(start.get(), null), (e, d)->complete.accept(e));
    }
    
    @Override
    default Iterator<T> iterator()
    {
        return new Itr<>(this);
    }
    
    class Itr<T, D> implements Iterator<T>
    {
        AutoCompletable<T, D> owner;
        Pair<T, D> data;
        Itr(AutoCompletable<T, D> owner)
        {
            this.owner = owner;
            this.data = owner.start();
        }
        
        byte state = 0;
        @Override
        public boolean hasNext()
        {
            switch(this.state)
            {
                case 0:
                    return true;
                case 1:
                    this.state = 2;
                    this.owner.complete(this.data.getFirst(), this.data.getSecond());
                default:
                    return false;
            }
        }
        @Override
        public T next()
        {
            if(this.state!=0)
                throw new NoSuchElementException();
            this.state = 1;
            return this.data.getFirst();
        }
    }
}
