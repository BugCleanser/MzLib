package mz.lib.minecraft.bukkit.wrappednms;

import com.mojang.serialization.Lifecycle;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.IRegistryWritable",minVer=13,maxVer=17),@VersionName(value="net.minecraft.core.IRegistryWritable",minVer=17)})
public interface NmsIRegistryWritableV13 extends WrappedBukkitObject
{
	@WrappedMethod("a")
	WrappedObject add(int id,NmsResourceKey resKey,WrappedObject obj,Lifecycle lifecycle);
}
