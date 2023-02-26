package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInBlockPlace",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInBlockPlace",minVer=17)})
public interface NmsPacketPlayInBlockPlace extends NmsPacket
{
}
