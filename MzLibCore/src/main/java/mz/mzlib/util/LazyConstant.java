package mz.mzlib.util;

import java.util.Optional;
import java.util.function.Supplier;

public class LazyConstant<T>
{
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public Optional<T> value;
    public Supplier<T> initizer;
    public LazyConstant(Supplier<T> initizer)
    {
        this.initizer = initizer;
    }

    public void init()
    {
        this.value = Optional.ofNullable(initizer.get());
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
            this.init();
            return this.value.orElse(null);
        }
    }
}
