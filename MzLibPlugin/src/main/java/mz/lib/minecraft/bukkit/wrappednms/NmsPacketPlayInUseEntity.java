package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayInUseEntity",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayInUseEntity",minVer=17)})
public interface NmsPacketPlayInUseEntity extends NmsPacket
{
}
