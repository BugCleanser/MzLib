package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInUseEntity",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInUseEntity",minVer=17)})
public interface NmsPacketPlayInUseEntity extends NmsPacket
{
}
