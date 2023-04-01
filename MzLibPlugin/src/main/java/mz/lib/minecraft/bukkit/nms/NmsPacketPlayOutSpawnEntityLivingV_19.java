package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkitlegacy.entity.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.*;
import mz.lib.wrapper.*;
import org.bukkit.entity.*;

import java.util.*;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutSpawnEntityLiving",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving",minVer=17,maxVer=19)})
public interface NmsPacketPlayOutSpawnEntityLivingV_19 extends NmsPacket
{
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	int getEntityId();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsPacketPlayOutSpawnEntityLivingV_19 setEntityId(int id);
	
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	UUID getUUID();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsPacketPlayOutSpawnEntityLivingV_19 setUUID(UUID id);
	
	default EntityType getEntityType()
	{
		return EntityTypeUtil.fromId(getEntityType0());
	}
	@VersionalWrappedFieldAccessor(@VersionalName("@1"))
	int getEntityType0();
}
