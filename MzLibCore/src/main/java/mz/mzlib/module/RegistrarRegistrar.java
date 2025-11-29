package mz.mzlib.module;

import mz.mzlib.util.RuntimeUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RegistrarRegistrar implements IRegistrar<IRegistrar<?>>
{
    public static RegistrarRegistrar instance = new RegistrarRegistrar();

    public final Map<Class<?>, Set<IRegistrar<?>>> registrars = new ConcurrentHashMap<>();

    {
        this.register(null, this);
        this.register(null, RegistrableRegistrar.instance);
    }

    @Override
    public Class<IRegistrar<?>> getType()
    {
        return RuntimeUtil.castClass(IRegistrar.class);
    }

    @Override
    public void register(MzModule module, IRegistrar<?> object)
    {
        synchronized(registrars)
        {
            registrars.computeIfAbsent(object.getType(), t -> ConcurrentHashMap.newKeySet()).add(object);
        }
    }

    @Override
    public void unregister(MzModule module, IRegistrar<?> object)
    {
        synchronized(registrars)
        {
            registrars.get(object.getType()).remove(object);
        }
    }
}
