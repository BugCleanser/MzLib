package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedObject;

import java.util.Map;

@WrappedBukkitClass(@VersionName(value="nms.RegistrySimple",maxVer=13))
public interface NmsRegistrySimpleV_13 extends WrappedBukkitObject
{
	@WrappedBukkitFieldAccessor(@VersionName("c"))
	Map<Object,Object> getMap();
	
	@WrappedBukkitMethod(@VersionName("get"))
	WrappedObject get(WrappedObject key);
}
