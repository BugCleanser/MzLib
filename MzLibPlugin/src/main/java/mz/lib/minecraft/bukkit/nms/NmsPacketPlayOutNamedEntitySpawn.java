package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.*;
import mz.lib.wrapper.*;

import java.util.*;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutNamedEntitySpawn",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn",minVer=17)})
public interface NmsPacketPlayOutNamedEntitySpawn extends NmsPacket
{
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	int getEntityId();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsPacketPlayOutNamedEntitySpawn setEntityId(int id);
	
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	UUID getUUID();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsPacketPlayOutNamedEntitySpawn setUUID(UUID id);
}
