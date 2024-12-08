package mz.mzlib.util;

import java.util.Optional;
import java.util.function.Supplier;

public class CachedValue<T>
{
    public Supplier<T> supplier;
    @SuppressWarnings("all")
    public Optional<T> value;
    public CachedValue(Supplier<T> supplier)
    {
        this.supplier = supplier;
    }

    @SuppressWarnings("all")
    public synchronized T get()
    {
        if(value == null)
            value = Optional.ofNullable(supplier.get());
        return value.get();
    }
}
