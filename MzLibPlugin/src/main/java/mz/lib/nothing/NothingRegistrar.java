package mz.lib.nothing;

import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.module.*;

public class NothingRegistrar implements IRegistrar<Class<? extends Nothing>>
{
	public static NothingRegistrar instance=new NothingRegistrar();
	@Override
	public Class<Class<? extends Nothing>> getType()
	{
		return TypeUtil.cast(Class.class);
	}
	@Override
	public boolean register(MzModule module,Class<? extends Nothing> obj)
	{
		if(Nothing.class.isAssignableFrom(obj))
		{
			Nothing.install(TypeUtil.cast(obj));
			return true;
		}
		return false;
	}
	@Override
	public void unregister(MzModule module,Class<? extends Nothing> obj)
	{
		Nothing.uninstall(TypeUtil.cast(obj));
	}
}
