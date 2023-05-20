package mz.mzlib.module;

import mz.mzlib.javautil.Instance;

public class RegistrableRegistrar implements IRegistrar<Registrable>, Instance
{
	public static RegistrarRegistrar instance=new RegistrarRegistrar();
	
	@Override
	public Class<Registrable> getType()
	{
		return Registrable.class;
	}
	@Override
	public void register(MzModule module,Registrable object)
	{
		object.onRegister(module);
	}
	@Override
	public void unregister(MzModule module,Registrable object)
	{
		object.onUnregister(module);
	}
}
