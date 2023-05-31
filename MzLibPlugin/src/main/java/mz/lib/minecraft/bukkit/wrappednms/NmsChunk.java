package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import org.bukkit.Chunk;

@WrappedBukkitClass({@VersionName(value="nms.Chunk",maxVer=17),@VersionName(value="net.minecraft.world.level.chunk.Chunk",minVer=17)})
public interface NmsChunk extends WrappedBukkitObject
{
	@WrappedBukkitFieldAccessor(@VersionName("bukkitChunk"))
	Chunk getBukkitChunk();
}
