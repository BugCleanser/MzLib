package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.ResourceKey",maxVer=17),@VersionalName(value="net.minecraft.resources.ResourceKey",minVer=17)})
public interface NmsResourceKey extends VersionalWrappedObject
{
	static NmsResourceKey fromKey0V13(NmsMinecraftKey key)
	{
		return WrappedObject.getStatic(NmsResourceKey.class).staticFromKeyV13(key);
	}
	static NmsResourceKey fromKeyV13(NmsResourceKey rKey,NmsMinecraftKey key)
	{
		return WrappedObject.getStatic(NmsResourceKey.class).staticFromKeyV13(rKey,key);
	}
	static NmsResourceKey fromKeyV13(NmsMinecraftKey key)
	{
		return fromKeyV13(fromKey0V13(key),key);
	}
	@VersionalWrappedMethod(@VersionalName(value="a",minVer=13))
	NmsResourceKey staticFromKeyV13(NmsMinecraftKey key);
	@VersionalWrappedMethod(@VersionalName(value="a",minVer=13))
	NmsResourceKey staticFromKeyV13(NmsResourceKey rKey,NmsMinecraftKey key);
}
