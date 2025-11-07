package mz.mzlib.util.nothing;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegistrarNothingClass implements IRegistrar<Class<? extends Nothing>>
{
    public static RegistrarNothingClass instance = new RegistrarNothingClass();

    public Map<Class<?>, NothingRegistration> registrations = new ConcurrentHashMap<>();

    @Override
    public Class<Class<? extends Nothing>> getType()
    {
        return RuntimeUtil.cast(Class.class);
    }

    @Override
    public boolean isRegistrable(Class<? extends Nothing> object)
    {
        return Nothing.class.isAssignableFrom(object);
    }

    @Override
    public void register(MzModule module, Class<? extends Nothing> object)
    {
        if(!WrapperObject.class.isAssignableFrom(object))
        {
            throw new IllegalArgumentException("Nothing class must extends WrapperObject.");
        }
        if(!WrapperObject.class.isInterface())
        {
            throw new IllegalArgumentException("Nothing class must be an interface.");
        }
        Class<?> wrappedClass = WrapperObject.getWrappedClass(RuntimeUtil.cast(object));
        registrations.computeIfAbsent(wrappedClass, k -> new NothingRegistration(wrappedClass)).add(object);
    }

    @Override
    public void unregister(MzModule module, Class<? extends Nothing> object)
    {
        Class<?> wrappedClass = WrapperObject.getWrappedClass(RuntimeUtil.<Class<WrapperObject>>cast(object));
        registrations.compute(
            wrappedClass, (k, v) ->
            {
                if(v == null)
                {
                    throw new IllegalArgumentException(
                        "Try to unregister a nothing class which has not been registered: " + object);
                }
                v.remove(object);
                if(v.isEmpty())
                {
                    return null;
                }
                return v;
            }
        );
    }
}
