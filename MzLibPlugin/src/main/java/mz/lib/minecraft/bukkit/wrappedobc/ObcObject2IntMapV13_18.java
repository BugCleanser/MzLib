package mz.lib.minecraft.bukkit.wrappedobc;

import mz.lib.Optional;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2IntMap;

@Optional
@WrappedBukkitClass(@VersionName(value="org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2IntMap",minVer=13))
public interface ObcObject2IntMapV13_18 extends WrappedBukkitObject
{
	@Override
	Object2IntMap<Object> getRaw();
}
