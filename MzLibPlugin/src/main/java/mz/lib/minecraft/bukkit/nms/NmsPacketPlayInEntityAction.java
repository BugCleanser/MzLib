package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInEntityAction",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInEntityAction",minVer=17)})
public interface NmsPacketPlayInEntityAction extends NmsPacket
{
}
