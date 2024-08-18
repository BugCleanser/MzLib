package mz.mzlib.util;

import java.util.Optional;
import java.util.function.Supplier;

public class InitializableConstant<T>
{
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public Optional<T> value;
    public Supplier<T> initizer;
    public InitializableConstant(Supplier<T> initizer)
    {
        this.initizer = initizer;
    }

    @SuppressWarnings("OptionalAssignedToNull")
    public T get()
    {
        if (this.value != null)
            return this.value.orElse(null);
        synchronized (this)
        {
            if (this.value != null)
                return this.value.orElse(null);
            this.value = Optional.ofNullable(initizer.get());
            return this.value.orElse(null);
        }
    }
}
