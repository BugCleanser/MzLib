package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.IChunkAccess",minVer=13,maxVer=17),@VersionName(value="net.minecraft.world.level.chunk.IChunkAccess",minVer=17)})
public interface NmsIChunkAccessV13 extends WrappedBukkitObject
{
	default boolean isChunk()
	{
		return WrappedObject.getRawClass(NmsChunk.class).isAssignableFrom(getRaw().getClass());
	}
	default NmsChunk toChunk()
	{
		return cast(NmsChunk.class);
	}
}
