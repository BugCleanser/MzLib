package mz.lib.minecraft.bukkit.wrappednms;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass(@VersionName(value="it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap",minVer=13))
public interface WrappedObject2ObjectLinkedOpenHashMapV13 extends WrappedBukkitObject
{
	@Override
	Object2ObjectLinkedOpenHashMap<Object,Object> getRaw();
}
