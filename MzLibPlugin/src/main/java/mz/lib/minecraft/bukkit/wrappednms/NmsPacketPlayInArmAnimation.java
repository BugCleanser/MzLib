package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayInArmAnimation",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayInArmAnimation",minVer=17)})
public interface NmsPacketPlayInArmAnimation extends NmsPacket
{
}
