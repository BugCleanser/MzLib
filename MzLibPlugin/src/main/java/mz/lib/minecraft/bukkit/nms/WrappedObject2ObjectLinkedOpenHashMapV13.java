package mz.lib.minecraft.bukkit.nms;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass(@VersionalName(value="it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap",minVer=13))
public interface WrappedObject2ObjectLinkedOpenHashMapV13 extends VersionalWrappedObject
{
	@Override
	Object2ObjectLinkedOpenHashMap<Object,Object> getRaw();
}
