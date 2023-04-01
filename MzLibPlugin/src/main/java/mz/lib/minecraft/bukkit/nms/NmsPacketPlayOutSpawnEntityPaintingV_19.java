package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.*;
import mz.lib.wrapper.*;

import java.util.*;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutSpawnEntityPainting",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityPainting",minVer=17,maxVer=19)})
public interface NmsPacketPlayOutSpawnEntityPaintingV_19 extends NmsPacket
{
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	int getEntityId();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsPacketPlayOutSpawnEntityPaintingV_19 setEntityId(int id);
	
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	UUID getUUID();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsPacketPlayOutSpawnEntityPaintingV_19 setUUID(UUID id);
}
