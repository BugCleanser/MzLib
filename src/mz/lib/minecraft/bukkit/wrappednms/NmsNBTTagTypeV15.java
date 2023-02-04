package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedMethod;

import java.io.DataInput;

@WrappedBukkitClass({@VersionName(value="nms.NBTTagType",minVer=15,maxVer=17),@VersionName(value="net.minecraft.nbt.NBTTagType",minVer=17)})
public interface NmsNBTTagTypeV15 extends WrappedBukkitObject
{
	@WrappedMethod("b")
	NmsNBTBase read(DataInput s,int depth,NmsNBTReadLimiter limiter);
}
