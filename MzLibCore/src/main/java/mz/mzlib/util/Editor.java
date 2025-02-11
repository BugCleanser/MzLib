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
        return this.value = this.get();
    }
    @Override
    public void complete()
    {
        this.set(this.value);
    }
    
    public <U> Editor<U> map(InvertibleFunction<T, U> function)
    {
        return of(ThrowableSupplier.of(this::get).thenApply(function), function.inverse().thenAccept(this::set));
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
