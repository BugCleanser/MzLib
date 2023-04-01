package mz.lib.minecraft.item;

import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.item.map.BitMap;
import mz.lib.module.MzModule;
import mz.lib.module.IRegistrar;
import mz.lib.module.RegistrarRegistrar;
import mz.lib.module.Registrar;
import mz.lib.mzlang.MzObject;

public class MzItemRegistrar extends MzModule implements IRegistrar<Class<MzItem>>
{
	public static MzItemRegistrar instance=new MzItemRegistrar();
	
	@Override
	public Class<Class<MzItem>> getType()
	{
		return TypeUtil.cast(Class.class);
	}
	@Override
	public boolean register(MzModule module,Class<MzItem> obj)
	{
		if(MzItem.class.isAssignableFrom(obj))
		{
			MzItem.mzItems.put(MzObject.newUninitializedInstance(obj).getKey(),obj);
			return true;
		}
		return false;
	}
	@Override
	public void unregister(MzModule module,Class<MzItem> obj)
	{
		if(MzItem.class.isAssignableFrom(obj))
			MzItem.mzItems.remove(MzObject.newUninitializedInstance(obj).getKey(),obj);
	}
	
	@Override
	public void onLoad()
	{
		reg(UnknownMzItem.class);
		reg(DebugFish.class);
		reg(BitMap.class);
	}
}
