package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInUseItem",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInUseItem",minVer=17)})
public interface NmsPacketPlayInUseItem extends NmsPacket
{
}
