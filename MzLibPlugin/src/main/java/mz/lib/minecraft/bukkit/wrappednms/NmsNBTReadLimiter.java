package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.NBTReadLimiter",maxVer=17),@VersionName(value="net.minecraft.nbt.NBTReadLimiter",minVer=17)})
public interface NmsNBTReadLimiter extends WrappedBukkitObject
{
	static NmsNBTReadLimiter newInstance(long l)
	{
		return WrappedObject.getStatic(NmsNBTReadLimiter.class).staticNewInstance(l);
	}
	@WrappedConstructor
	NmsNBTReadLimiter staticNewInstance(long l);
}
