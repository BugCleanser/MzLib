package mz.lib.minecraft.bukkit.wrappedmojang;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass(@VersionName(value = "com.mojang.serialization.Lifecycle",minVer = 16))
public interface WrappedLifecycleV16 extends WrappedBukkitObject
{
}
