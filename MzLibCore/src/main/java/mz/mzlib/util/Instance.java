package mz.mzlib.util;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;

import java.lang.invoke.MethodType;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Instance
{
    class Registrar implements IRegistrar<Instance>
    {
        public static Registrar instance = new Registrar();
        Map<Class<? extends Instance>, List<Instance>> instances = new ConcurrentHashMap<>();

        @Override
        public Class<Instance> getType()
        {
            return Instance.class;
        }

        public <T extends Instance> void setInstance(Class<T> type, T instance) throws NoSuchFieldException, IllegalAccessException
        {
            try
            {
                ClassUtil.findFieldSetter(type, true, "instance", type.getDeclaredField("instance").getType()).asType(MethodType.methodType(void.class, Instance.class)).invoke(instance);
            }
            catch (NoSuchFieldException ignored)
            {
            }
            catch (Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }

        @Override
        public void register(MzModule module, Instance object)
        {
            ClassUtil.forEachSuperUnique(object.getClass(), c ->
            {
                if (Instance.class.isAssignableFrom(c))
                {
                    instances.computeIfAbsent(RuntimeUtil.cast(c), k -> new CopyOnWriteArrayList<>()).add(0, object);
                    try
                    {
                        setInstance(RuntimeUtil.cast(c), object);
                    }
                    catch (NoSuchFieldException |
                           IllegalAccessException e)
                    {
                        throw RuntimeUtil.sneakilyThrow(e);
                    }
                }
            });
        }

        @Override
        public void unregister(MzModule module, Instance object)
        {
            ClassUtil.forEachSuperUnique(object.getClass(), c ->
            {
                if (Instance.class.isAssignableFrom(c))
                {
                    instances.computeIfPresent(RuntimeUtil.cast(c), (k, v) ->
                    {
                        v.remove(object);
                        if (v.isEmpty())
                        {
                            return null;
                        }
                        else
                        {
                            try
                            {
                                setInstance(RuntimeUtil.cast(k), v.get(0));
                            }
                            catch (NoSuchFieldException |
                                   IllegalAccessException e)
                            {
                                throw RuntimeUtil.sneakilyThrow(e);
                            }
                            return v;
                        }
                    });
                }
            });
        }
    }
}
