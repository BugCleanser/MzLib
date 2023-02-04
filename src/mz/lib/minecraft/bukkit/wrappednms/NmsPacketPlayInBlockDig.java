package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayInBlockDig",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayInBlockDig",minVer=17)})
public interface NmsPacketPlayInBlockDig extends NmsPacket
{
}
