package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

import java.util.UUID;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutNamedEntitySpawn",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn",minVer=17)})
public interface NmsPacketPlayOutNamedEntitySpawn extends NmsPacket
{
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	int getEntityId();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutNamedEntitySpawn setEntityId(int id);
	
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	UUID getUUID();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutNamedEntitySpawn setUUID(UUID id);
}
