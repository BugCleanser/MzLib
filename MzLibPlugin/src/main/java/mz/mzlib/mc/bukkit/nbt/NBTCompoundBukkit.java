package mz.mzlib.mc.bukkit.nbt;

import mz.mzlib.mc.nbt.NBTCompound;
import mz.mzlib.mc.nbt.NBTElement;
import mz.mzlib.util.delegator.*;

@DelegatorClassForName("nms.NBTTagCompound")
public interface NBTCompoundBukkit extends NBTCompound, Delegator
{
	static NBTCompoundBukkit newInstance()
	{
		return Delegator.createStatic(NBTCompoundBukkit.class).staticNewInstance();
	}
	@DelegatorConstructor
	NBTCompoundBukkit staticNewInstance();
	
	@DelegatorMethod("get")
	@Override
	NBTElement get(String name);
	
	@DelegatorMethod("set")
	@Override
	void set(String name,NBTElement value);
}
