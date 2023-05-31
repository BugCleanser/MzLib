package mz.lib.minecraft.bukkit.wrappednms;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass(@VersionName(value="it.unimi.dsi.fastutil.objects.Object2IntMap",minVer=18))
public interface WrappedObject2IntMapV18 extends WrappedBukkitObject
{
	@Override
	Object2IntMap<Object> getRaw();
}
