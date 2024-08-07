package mz.mzlib.mc.bukkit.nbt;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.bukkit.delegator.DelegatorBukkitClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftMethod;
import mz.mzlib.mc.nbt.NBTCompound;
import mz.mzlib.mc.nbt.NBTElement;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorConstructor;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorBukkitClass({@VersionName(end=1700,name="nms.NBTTagCompound"),@VersionName(begin=1700,name="net.minecraft.nbt.NBTTagCompound")})
public interface NBTCompoundBukkit extends NBTCompound,NBTElementBukkit, Delegator
{
	@SuppressWarnings("deprecation")
	@DelegatorCreator
	static NBTCompoundBukkit create(Object delegate)
	{
		return Delegator.create(NBTCompoundBukkit.class,delegate);
	}
	static NBTCompoundBukkit newInstance()
	{
		return create(null).staticNewInstance();
	}
	@DelegatorConstructor
	NBTCompoundBukkit staticNewInstance();
	
	@DelegatorMinecraftMethod({@VersionName(name="get"),@VersionName(name="@0")})
	NBTElementBukkit get0(String name);
	@Override
	default NBTElementBukkit get(String name)
	{
		return NBTElementBukkit.autoDelegator.cast(this.get0(name));
	}
	
	@Override
	@DelegatorMinecraftMethod(@VersionName(name="set"))
	void set(String name,NBTElement value);
}
