package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass(@VersionName(value="net.minecraft.util.RandomSource",minVer=19))
public interface NmsRandomSourceV19 extends WrappedBukkitObject
{
	@WrappedBukkitMethod(@VersionName("@0"))
	float nextFloat();
}
