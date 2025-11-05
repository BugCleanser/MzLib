package mz.mzlib.util.wrapper;

import mz.mzlib.util.RuntimeUtil;

import java.util.function.Function;

public class WrapperFactory<T extends WrapperObject>
{
    protected final T wrapperStatic;
    
    public WrapperFactory(T wrapperStatic)
    {
        this.wrapperStatic = wrapperStatic;
    }
    public WrapperFactory(Function<Object, T> creator)
    {
        this(creator.apply(null));
    }
    
    public T create(Object wrapped)
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
    
    @SuppressWarnings("deprecation")
    public static <T extends WrapperObject> WrapperFactory<T> of(Class<T> wrapperClass)
    {
        return new WrapperFactory<>(WrapperObject.create(wrapperClass, null));
    }
    public static <T extends WrapperObject> WrapperFactory<T> of(T value)
    {
        return new WrapperFactory<>(value);
    }
    
    @Deprecated
    public static <T extends WrapperObject> WrapperFactory<T> find(Class<T> wrapperClass)
    {
        return of(wrapperClass);
    }
}
