package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkit.mojang.MojLifecycleV16;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.IRegistryWritable",minVer=13,maxVer=17),@VersionalName(value="net.minecraft.core.IRegistryWritable",minVer=17)})
public interface NmsIRegistryWritableV13 extends VersionalWrappedObject
{
	@WrappedMethod("a")
	WrappedObject add(int id, NmsResourceKey resKey, WrappedObject obj, MojLifecycleV16 lifecycle);
}
