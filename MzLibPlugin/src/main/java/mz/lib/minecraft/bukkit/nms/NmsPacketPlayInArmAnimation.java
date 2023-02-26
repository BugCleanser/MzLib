package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInArmAnimation",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInArmAnimation",minVer=17)})
public interface NmsPacketPlayInArmAnimation extends NmsPacket
{
}
