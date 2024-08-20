package mz.mzlib.util.wrapper;

import mz.mzlib.util.RuntimeUtil;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

@WrapClass(Object.class)
public interface WrapperObject
{
    @WrapperCreator
    static WrapperObject create(Object wrapped)
    {
        return create(WrapperObject.class, wrapped);
    }

    Object getWrapped();

    void setWrapped(Object wrapped);

    default void setWrappedFrom(WrapperObject wrapper)
    {
        this.setWrapped(wrapper.getWrapped());
    }

    /**
     * @deprecated slow
     */
    @Deprecated
    static <T extends WrapperObject> T create(Class<T> type, Object wrapped)
    {
        try
        {
            return RuntimeUtil.cast((WrapperObject) WrapperClassInfo.get(type).getConstructor().invokeExact((Object) wrapped));
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    static CallSite getConstructorCallSite(MethodHandles.Lookup caller, String invokedName, MethodType invokedType, Class<? extends WrapperObject> wrapperClass)
    {
        return new ConstantCallSite(WrapperClassInfo.get(wrapperClass).getConstructor().asType(invokedType));
    }

    /**
     * @deprecated slow
     */
    @Deprecated
    static Class<?> getWrappedClass(Class<? extends WrapperObject> wrapperClass)
    {
        return WrapperClassInfo.get(wrapperClass).getWrappedClass();
    }

    Class<?> getWrappedClass();
    @WrapMethod("clone")
    WrapperObject clone0();
}
