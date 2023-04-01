package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.*;
import mz.lib.*;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.IChunkAccess",minVer=13,maxVer=17),@VersionalName(value="net.minecraft.world.level.chunk.IChunkAccess",minVer=17)})
public interface NmsIChunkAccessV13 extends VersionalWrappedObject
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
