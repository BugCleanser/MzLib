package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import mz.lib.minecraft.wrapper.*;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass(@VersionalName(value="it.unimi.dsi.fastutil.objects.Object2IntMap",minVer=18))
public interface WrappedObject2IntMapV18 extends VersionalWrappedObject
{
	@Override
	Object2IntMap<Object> getRaw();
}
