package mz.mzlib.util.wrapper;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.mzlib.util.RuntimeUtil;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;

public class AutoWrapper<T extends WrapperObject>
{
    public Map<Class<?>, CallSite> wrapperConstructors = new HashMap<>();

    @SuppressWarnings("deprecation")
    @SafeVarargs
    public AutoWrapper(Class<T> defaultWrapper, Class<? extends T>... extendingWrappers)
    {
        this.wrapperConstructors.put(WrapperObject.getWrappedClass(defaultWrapper), WrapperObject.getConstructorCallSite(Root.getTrusted(defaultWrapper), "create", MethodType.methodType(WrapperObject.class, Object.class), defaultWrapper));
        for (Class<? extends T> wrapper : extendingWrappers)
        {
            this.wrapperConstructors.put(WrapperObject.getWrappedClass(wrapper), WrapperObject.getConstructorCallSite(Root.getTrusted(wrapper), "create", MethodType.methodType(WrapperObject.class, Object.class), wrapper));
        }
    }

    public T wrap(Object wrapped)
    {
        Class<?> clazz = wrapped.getClass();
        CallSite constructor;
        while ((constructor = wrapperConstructors.get(clazz)) == null)
        {
            clazz = clazz.getSuperclass();
            if (clazz == null)
            {
                throw new ClassCastException("No wrapper found for class " + wrapped.getClass().getName() + ".");
            }
        }
        try
        {
            return RuntimeUtil.cast((WrapperObject) constructor.getTarget().invokeExact((Object) wrapped));
        }
        catch (Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }

    public T cast(T wrapper)
    {
        return this.wrap(wrapper.getWrapped());
    }
}
