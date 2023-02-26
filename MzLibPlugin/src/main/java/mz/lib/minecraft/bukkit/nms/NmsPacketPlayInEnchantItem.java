package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInEnchantItem",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInEnchantItem",minVer=17)})
public interface NmsPacketPlayInEnchantItem extends NmsPacket
{
}
