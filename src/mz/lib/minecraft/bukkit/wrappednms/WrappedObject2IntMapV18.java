package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

@WrappedBukkitClass(@VersionName(value="it.unimi.dsi.fastutil.objects.Object2IntMap",minVer=18))
public interface WrappedObject2IntMapV18 extends WrappedBukkitObject
{
	@Override
	Object2IntMap<Object> getRaw();
}
