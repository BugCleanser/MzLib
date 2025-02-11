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
    
    public static <T> Editor<T> of(Supplier<T> getter, Consumer<T> setter)
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
    public static <T, H> Editor<T> of(H holder, Function<H, T> getter, BiConsumer<H, T> setter)
    {
        return of(ThrowableSupplier.constant(holder).thenApply(getter), ThrowableBiConsumer.of(setter).bindFirst(ThrowableSupplier.constant(holder)));
    }
    
    public static <T> Editor<Ref<T>> ofRef(Supplier<T> getter, Consumer<T> setter)
    {
        return of(ThrowableSupplier.of(getter).thenApply(StrongRef::new), ThrowableFunction.<Ref<T>, T, RuntimeException>of(Ref::get).thenAccept(setter));
    }
    public static <T, H> Editor<Ref<T>> ofRef(H holder, Function<H, T> getter, BiConsumer<H, T> setter)
    {
        return ofRef(ThrowableSupplier.constant(holder).thenApply(getter), ThrowableBiConsumer.of(setter).bindFirst(ThrowableSupplier.constant(holder)));
    }
}
