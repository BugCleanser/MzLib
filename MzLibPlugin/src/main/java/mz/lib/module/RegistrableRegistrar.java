package mz.lib.module;

public class RegistrableRegistrar implements IRegistrar<Registrable>
{
	public static RegistrableRegistrar instance=new RegistrableRegistrar();
	
	@Override
	public Class<Registrable> getType()
	{
		return Registrable.class;
	}
	@Override
	public boolean register(MzModule module,Registrable obj)
	{
		obj.onReg(module);
		return true;
	}
	@Override
	public void unregister(MzModule module,Registrable obj)
	{
		obj.onUnreg(module);
	}
}
