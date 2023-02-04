package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayInCustomPayload",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayInCustomPayload",minVer=17)})
public interface NmsPacketPlayInCustomPayload extends NmsPacket
{
	@WrappedBukkitFieldAccessor(value=@VersionName(maxVer=13, value="a"))
	String getChannelNameV_13();
	@WrappedBukkitFieldAccessor(value=@VersionName(maxVer=13, value="b"))
	NmsPacketDataSerializer getDataV_13();
}
