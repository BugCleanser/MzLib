package mz.lib.minecraft.bukkit.item;

import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.item.map.BitMap;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.module.IRegistrar;
import mz.lib.minecraft.bukkit.module.RegistrarRegistrar;
import mz.lib.mzlang.MzObject;

public class MzItemRegistrar extends AbsModule implements IRegistrar<Class<MzItem>>
{
	public static MzItemRegistrar instance=new MzItemRegistrar();
	public MzItemRegistrar()
	{
		super(MzLib.instance,RegistrarRegistrar.instance);
	}
	
	@Override
	public Class<Class<MzItem>> getType()
	{
		return TypeUtil.cast(Class.class);
	}
	@Override
	public boolean register(Class<MzItem> obj)
	{
		if(MzItem.class.isAssignableFrom(obj))
		{
			MzItem.mzItems.put(MzObject.newUninitializedInstance(obj).getKey(),obj);
			return true;
		}
		return false;
	}
	@Override
	public void unregister(Class<MzItem> obj)
	{
		if(MzItem.class.isAssignableFrom(obj))
			MzItem.mzItems.remove(MzObject.newUninitializedInstance(obj).getKey(),obj);
	}
	
	@Override
	public void onEnable()
	{
		reg(UnknownMzItem.class);
		reg(BitMap.class);
	}
}
