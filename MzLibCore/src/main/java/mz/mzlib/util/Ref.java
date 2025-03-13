package mz.mzlib.util;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Ref<T>
{
    T get();

    void set(T value);
    
    static <T> T getOrSet(Ref<Option<T>> ref, Supplier<T> supplier)
    {
        for(T value: ref.get())
            return value;
        T value = supplier.get();
        ref.set(Option.some(value));
        return value;
    }
    
    default <U> Ref<U> map(Function<? super T, ? extends U> action)
    {
        return new StrongRef<>(action.apply(get()));
    }
}
