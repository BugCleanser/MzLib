package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedObject;
import mz.lib.wrapper.WrappedMethod;

import java.io.DataInput;

@VersionalWrappedClass({@VersionalName(value="nms.NBTTagType",minVer=15,maxVer=17),@VersionalName(value="net.minecraft.nbt.NBTTagType",minVer=17)})
public interface NmsNBTTagTypeV15 extends VersionalWrappedObject
{
	@WrappedMethod("b")
	NmsNBTBase read(DataInput s,int depth,NmsNBTReadLimiter limiter);
}
