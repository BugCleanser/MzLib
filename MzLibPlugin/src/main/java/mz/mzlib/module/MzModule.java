package mz.mzlib.module;

import mz.mzlib.util.ClassUtil;
import mz.mzlib.util.RuntimeUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MzModule
{
    public boolean isLoaded = false;
    public Set<MzModule> submodules = ConcurrentHashMap.newKeySet();
    public Map<Object, Stack<IRegistrar<?>>> registeredObjects = new ConcurrentHashMap<>();

    public void register(Object object)
    {
        if (!isLoaded)
        {
            throw new IllegalStateException("Try to register an object but the module is not loaded: " + this + ".");
        }
        if (object instanceof MzModule)
        {
            if (((MzModule) object).isLoaded)
            {
                throw new IllegalStateException("Try to load the module but it has been loaded: " + object + ".");
            }
            ((MzModule) object).isLoaded = true;
            submodules.add((MzModule) object);
            ((MzModule) object).onLoad();
        }
        if (registeredObjects.containsKey(object))
        {
            throw new IllegalStateException("Try to register the object but it has been registered: " + object + ".");
        }
        Set<IRegistrar<?>> registrars = new HashSet<>();
        ClassUtil.forEachSuperUnique(object.getClass(), c ->
        {
            if (RegistrarRegistrar.instance.registrars.containsKey(c))
            {
                for (IRegistrar<?> i : RegistrarRegistrar.instance.registrars.get(c).toArray(new IRegistrar[0]))
                {
                    if (i.isRegistrable(RuntimeUtil.cast(object)))
                    {
                        registrars.add(i);
                    }
                }
            }
        });
        if (registrars.isEmpty() && !(object instanceof MzModule))
        {
            throw new UnsupportedOperationException("Try to register the object but found no registrar: " + object + ".");
        }

        Stack<IRegistrar<?>> workedRegistrarsRecord = new Stack<>();

        Set<IRegistrar<?>> untreatedRegistrars = new HashSet<>(registrars);
        Set<IRegistrar<?>> processLater = new HashSet<>();
        Set<IRegistrar<?>> workedRegistrars = new HashSet<>();
        try
        {
            int cnt = 0;
            while (!untreatedRegistrars.isEmpty())
            {
                Iterator<IRegistrar<?>> it = untreatedRegistrars.iterator();
                IRegistrar<?> now = it.next();
                it.remove();

                if (workedRegistrars.containsAll(now.getDependencies()))
                {
                    now.register(this, RuntimeUtil.cast(object));
                    workedRegistrars.add(now);
                    workedRegistrarsRecord.push(now);
                    cnt++;
                }
                else
                {
                    processLater.add(now);
                }

                if (untreatedRegistrars.isEmpty())
                {
                    if (cnt == 0)
                    {
                        throw new IllegalStateException("Circular dependency or nonexistent dependencies: " + processLater + ".");
                    }
                    cnt = 0;
                    untreatedRegistrars = processLater;
                    processLater = new HashSet<>();
                }
            }
        }
        finally
        {
            this.registeredObjects.put(object, workedRegistrarsRecord);
        }
    }

    public void unregister(Object object)
    {
        if (!isLoaded)
        {
            throw new IllegalStateException("Try to unregister an object but the module has been unloaded: " + this + ".");
        }
        if (object instanceof MzModule)
        {
            if (!((MzModule) object).isLoaded)
            {
                throw new IllegalStateException("Try to unload the module but it's not loaded: " + object + ".");
            }
            if (!submodules.contains(object))
            {
                throw new IllegalStateException("Try to unload the module(" + object + ") but it's not loaded by this module(" + this + ").");
            }
            for (MzModule i : new HashSet<>(((MzModule) object).submodules))
            {
                ((MzModule) object).unregister(i);
            }
            for (Object i : new HashSet<>(((MzModule) object).registeredObjects.keySet()))
            {
                ((MzModule) object).unregister(i);
            }
            ((MzModule) object).onUnload();
            ((MzModule) object).isLoaded = false;
            submodules.remove(object);
        }
        Stack<IRegistrar<?>> removed = registeredObjects.remove(object);
        if (removed == null)
        {
            throw new IllegalStateException("Try to unregister the object but it's not registered: " + object + ".");
        }
        while (!removed.isEmpty())
        {
            IRegistrar<?> i = removed.pop();
            Set<IRegistrar<?>> key = RegistrarRegistrar.instance.registrars.get(i.getType());
            if (key == null || !key.contains(i))
            {
                throw new IllegalStateException("Try to unregister an object(" + object + ") but the registrar(" + i + ") has been unloaded.");
            }
            i.unregister(this, RuntimeUtil.cast(object));
        }
    }

    public void onLoad()
    {
    }

    public void onUnload()
    {
    }
}
