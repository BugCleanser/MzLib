package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayInUseItem",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayInUseItem",minVer=17)})
public interface NmsPacketPlayInUseItem extends NmsPacket
{
}
