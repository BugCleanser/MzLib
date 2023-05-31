package mz.lib.minecraft.bukkit.wrappednms;

import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import mz.lib.Optional;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@Optional
@WrappedBukkitClass(@VersionName(value="it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap",minVer=13))
public interface WrappedReference2IntOpenHashMapV13 extends WrappedBukkitObject
{
	@Override
	Reference2IntOpenHashMap<Object> getRaw();
}
