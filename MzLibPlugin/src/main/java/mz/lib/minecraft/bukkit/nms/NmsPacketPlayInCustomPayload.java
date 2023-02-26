package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInCustomPayload",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInCustomPayload",minVer=17)})
public interface NmsPacketPlayInCustomPayload extends NmsPacket
{
	@VersionalWrappedFieldAccessor(value=@VersionalName(maxVer=13, value="a"))
	String getChannelNameV_13();
	@VersionalWrappedFieldAccessor(value=@VersionalName(maxVer=13, value="b"))
	NmsPacketDataSerializer getDataV_13();
}
