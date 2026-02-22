package mz.mzlib.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

@ApiStatus.Experimental
public interface Ref<T extends @Nullable Object>
{
    T get();

    void set(T value);

    static <T extends @Nullable Object> T getOrSet(Ref<Option<T>> ref, Supplier<T> supplier)
    {
        for(T value : ref.get())
        {
            return value;
        }
        T value = supplier.get();
        ref.set(Option.some(value));
        return value;
    }

    default <U> Ref<U> map(Function<? super T, ? extends U> action)
    {
        return new RefStrong<>(action.apply(get()));
    }
}
