package mz.mzlib.util.wrapper;

import mz.mzlib.util.RuntimeUtil;
import org.jetbrains.annotations.Nullable;

public class WrapperFactory<T extends WrapperObject>
{
    protected final T wrapperStatic;

    public WrapperFactory(T wrapperStatic)
    {
        this.wrapperStatic = wrapperStatic;
    }

    public T create(@Nullable Object wrapped)
    {
        return RuntimeUtil.cast(this.wrapperStatic.static$create(wrapped));
    }

    public T getStatic()
    {
        return this.wrapperStatic;
    }

    public Class<?> getWrappedClass()
    {
        return this.getStatic().static$getWrappedClass();
    }

    public boolean isInstance(WrapperObject wrapper)
    {
        return this.getStatic().static$isInstance(wrapper);
    }

    public T cast(WrapperObject wrapper)
    {
        return wrapper.as(this);
    }

    public static <T0 extends WrapperObject, T extends T0> WrapperFactory<T> of(Class<T0> wrapperClass)
    {
        return of(WrapperObject.create(wrapperClass, null));
    }
    public static <T extends WrapperObject> WrapperFactory<T> of(T value)
    {
        WrapperFactory<T> result = new WrapperFactory<>(value);
        if(value.isPresent())
            result = new WrapperFactory<>(result.create(null));
        return result;
    }

    /**
     * @see #of(Class) 
     */
    @Deprecated
    public static <T extends WrapperObject> WrapperFactory<T> find(Class<T> wrapperClass)
    {
        return of(wrapperClass);
    }
}
