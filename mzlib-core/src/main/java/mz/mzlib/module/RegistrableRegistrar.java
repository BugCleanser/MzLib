package mz.mzlib.module;

import org.jetbrains.annotations.UnknownNullability;

public class RegistrableRegistrar implements IRegistrar<Registrable>
{
    public static RegistrableRegistrar instance = new RegistrableRegistrar();

    @Override
    public Class<Registrable> getType()
    {
        return Registrable.class;
    }

    @Override
    public void register(@UnknownNullability MzModule module, Registrable object)
    {
        object.onRegister(module);
    }

    @Override
    public void unregister(@UnknownNullability MzModule module, Registrable object)
    {
        object.onUnregister(module);
    }
}
