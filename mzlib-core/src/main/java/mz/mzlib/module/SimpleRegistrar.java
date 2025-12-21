package mz.mzlib.module;

import java.util.function.BiConsumer;

public class SimpleRegistrar<T> implements IRegistrar<T>
{
    public Class<T> type;
    public BiConsumer<MzModule, T> registerMethod;
    public BiConsumer<MzModule, T> unregisterMethod;

    public SimpleRegistrar(
        Class<T> type,
        BiConsumer<MzModule, T> registerMethod,
        BiConsumer<MzModule, T> unregisterMethod)
    {
        this.type = type;
        this.registerMethod = registerMethod;
        this.unregisterMethod = unregisterMethod;
    }

    @Override
    public Class<T> getType()
    {
        return type;
    }

    @Override
    public void register(MzModule module, T object)
    {
        registerMethod.accept(module, object);
    }

    @Override
    public void unregister(MzModule module, T object)
    {
        unregisterMethod.accept(module, object);
    }
}
