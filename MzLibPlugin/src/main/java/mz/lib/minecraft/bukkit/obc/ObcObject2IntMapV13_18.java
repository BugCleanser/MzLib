package mz.lib.minecraft.bukkit.obc;

import mz.lib.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2IntMap;

@Optional
@VersionalWrappedClass(@VersionalName(value="org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2IntMap",minVer=13))
public interface ObcObject2IntMapV13_18 extends VersionalWrappedObject
{
	@Override
	Object2IntMap<Object> getRaw();
}
