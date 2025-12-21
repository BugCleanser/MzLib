package mz.mzlib.module;

public class RegistrableRegistrar implements IRegistrar<Registrable>
{
    public static RegistrableRegistrar instance = new RegistrableRegistrar();

    @Override
    public Class<Registrable> getType()
    {
        return Registrable.class;
    }

    @Override
    public void register(MzModule module, Registrable object)
    {
        object.onRegister(module);
    }

    @Override
    public void unregister(MzModule module, Registrable object)
    {
        object.onUnregister(module);
    }
}
