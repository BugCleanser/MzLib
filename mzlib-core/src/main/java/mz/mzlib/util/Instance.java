package mz.mzlib.util;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import org.jetbrains.annotations.Nullable;

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

        private <T extends Instance> void setInstance(Class<T> type, @Nullable T instance)
            throws NoSuchFieldException, IllegalAccessException
        {
            try
            {
                ClassUtil.findFieldSetter(type, true, "instance", type.getDeclaredField("instance").getType())
                    .asType(MethodType.methodType(void.class, Instance.class)).invoke(instance);
            }
            catch(NoSuchFieldException ignored)
            {
            }
            catch(Throwable e)
            {
                throw RuntimeUtil.sneakilyThrow(e);
            }
        }

        @Override
        public void register(MzModule module, Instance object)
        {
            ClassUtil.forEachSuperUnique(
                object.getClass(), c ->
                {
                    if(Instance.class.isAssignableFrom(c))
                    {
                        instances.computeIfAbsent(RuntimeUtil.cast(c), k -> new CopyOnWriteArrayList<>())
                            .add(object);
                        try
                        {
                            setInstance(RuntimeUtil.cast(c), object);
                        }
                        catch(NoSuchFieldException |
                              IllegalAccessException e)
                        {
                            throw RuntimeUtil.sneakilyThrow(e);
                        }
                    }
                }
            );
        }

        @Override
        public void unregister(MzModule module, Instance object)
        {
            ClassUtil.forEachSuperUnique(
                object.getClass(), c ->
                {
                    if(Instance.class.isAssignableFrom(c))
                    {
                        instances.computeIfPresent(
                            RuntimeUtil.cast(c), (k, v) ->
                            {
                                try
                                {
                                    v.remove(object);
                                    if(v.isEmpty())
                                    {
                                        setInstance(RuntimeUtil.cast(k), null);
                                        return null;
                                    }
                                    else
                                    {
                                        setInstance(RuntimeUtil.cast(k), v.get(v.size() - 1));
                                        return v;
                                    }
                                }
                                catch(NoSuchFieldException |
                                    IllegalAccessException e)
                                {
                                    throw RuntimeUtil.sneakilyThrow(e);
                                }
                            }
                        );
                    }
                }
            );
        }
    }
}
