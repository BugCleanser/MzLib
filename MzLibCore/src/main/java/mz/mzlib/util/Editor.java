package mz.mzlib.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Editor<T> implements AutoCompletable<T>
{
    public abstract T get();
    
    public abstract void set(T value);
    
    T value;
    @Override
    public T start()
    {
        if(this.value!=null)
            throw new IllegalStateException("Editor is already started");
        return this.value = this.get();
    }
    @Override
    public void complete()
    {
        if(this.value==null)
            throw new IllegalStateException("Editor is not started");
        this.set(this.value);
    }
    
    public <U> Editor<U> map(InvertibleFunction<T, U> function)
    {
        return of(ThrowableSupplier.of(this::get).thenApply(function), function.inverse().thenAccept(this::set));
    }
    
    public <U> Editor<U> then(Function<T, U> getter, BiConsumer<T, U> setter)
    {
        return new Editor<U>()
        {
            @Override
            public U get()
            {
                return getter.apply(Editor.this.start());
            }
            
            @Override
            public void set(U value)
            {
                setter.accept(Editor.this.value, value);
                Editor.this.complete();
            }
        };
    }
    
    public static <T> Editor<T> of(Supplier<? extends T> getter, Consumer<? super T> setter)
    {
        return new Editor<T>()
        {
            @Override
            public T get()
            {
                return getter.get();
            }
            @Override
            public void set(T value)
            {
                setter.accept(value);
            }
        };
    }
    public static <T, H> Editor<T> of(H holder, Function<? super H, ? extends T> getter, BiConsumer<? super H, ? super T> setter)
    {
        return of(ThrowableSupplier.constant(holder).thenApply(getter), ThrowableBiConsumer.of(setter).bindFirst(ThrowableSupplier.constant(holder)));
    }
    
    public static <T> Editor<Ref<T>> ofRef(Supplier<? extends T> getter, Consumer<? super T> setter)
    {
        return Editor.<T>of(getter, setter).map(InvertibleFunction.ref());
    }
    public static <T, H> Editor<Ref<T>> ofRef(H holder, Function<H, T> getter, BiConsumer<H, T> setter)
    {
        return Editor.of(holder, getter, setter).map(InvertibleFunction.ref());
    }
}
