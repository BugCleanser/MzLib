package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;

@VersionalWrappedClass({@VersionalName(value="nms.NBTReadLimiter",maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTReadLimiter",minVer=17)})
public interface NmsNBTReadLimiter extends VersionalWrappedObject
{
	static NmsNBTReadLimiter newInstance(long l)
	{
		return WrappedObject.getStatic(NmsNBTReadLimiter.class).staticNewInstance(l);
	}
	@WrappedConstructor
	NmsNBTReadLimiter staticNewInstance(long l);
}
