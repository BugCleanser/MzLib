package mz.mzlib.module;

import org.jetbrains.annotations.ApiStatus;

import java.util.function.BiConsumer;

@ApiStatus.Experimental
public class RegistrarSimple<T> implements IRegistrar<T>
{
    public Class<T> type;
    public BiConsumer<MzModule, T> registerMethod;
    public BiConsumer<MzModule, T> unregisterMethod;

    public RegistrarSimple(
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
