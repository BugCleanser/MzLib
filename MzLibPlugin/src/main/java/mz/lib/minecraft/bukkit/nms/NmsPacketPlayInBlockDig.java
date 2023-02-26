package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInBlockDig",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInBlockDig",minVer=17)})
public interface NmsPacketPlayInBlockDig extends NmsPacket
{
}
