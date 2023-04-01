package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.*;
import mz.lib.wrapper.*;
import org.bukkit.*;

@VersionalWrappedClass({@VersionalName(value="nms.Chunk",maxVer=17),@VersionalName(value="net.minecraft.world.level.chunk.Chunk",minVer=17)})
public interface NmsChunk extends VersionalWrappedObject
{
	@VersionalWrappedFieldAccessor(@VersionalName("bukkitChunk"))
	Chunk getBukkitChunk();
}
