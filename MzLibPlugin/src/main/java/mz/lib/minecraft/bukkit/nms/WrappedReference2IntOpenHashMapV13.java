package mz.lib.minecraft.bukkit.nms;

import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import mz.lib.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.mzlib.wrapper.*;

@Optional
@VersionalWrappedClass(@VersionalName(value="it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap",minVer=13))
public interface WrappedReference2IntOpenHashMapV13 extends VersionalWrappedObject
{
	@Override
	Reference2IntOpenHashMap<Object> getRaw();
}
