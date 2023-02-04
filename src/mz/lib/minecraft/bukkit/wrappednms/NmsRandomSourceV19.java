package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import net.minecraft.util.RandomSource;

@WrappedBukkitClass(@VersionName(value="net.minecraft.util.RandomSource",minVer=19))
public interface NmsRandomSourceV19 extends WrappedBukkitObject
{
	@Override
	RandomSource getRaw();
	
	@WrappedBukkitMethod(@VersionName("@0"))
	float nextFloat();
}
