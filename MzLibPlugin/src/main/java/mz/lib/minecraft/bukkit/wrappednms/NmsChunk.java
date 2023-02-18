package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.*;
import mz.lib.minecraft.bukkit.wrapper.*;
import org.bukkit.*;

@WrappedBukkitClass({@VersionName(value="nms.Chunk",maxVer=17),@VersionName(value="net.minecraft.world.level.chunk.Chunk",minVer=17)})
public interface NmsChunk extends WrappedBukkitObject
{
	@WrappedBukkitFieldAccessor(@VersionName("bukkitChunk"))
	Chunk getBukkitChunk();
}
