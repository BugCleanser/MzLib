package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.ResourceKey",maxVer=17),@VersionName(value="net.minecraft.resources.ResourceKey",minVer=17)})
public interface NmsResourceKey extends WrappedBukkitObject
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
	@WrappedBukkitMethod(@VersionName(value="a",minVer=13))
	NmsResourceKey staticFromKeyV13(NmsMinecraftKey key);
	@WrappedBukkitMethod(@VersionName(value="a",minVer=13))
	NmsResourceKey staticFromKeyV13(NmsResourceKey rKey,NmsMinecraftKey key);
}
