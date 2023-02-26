package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayInAbilities",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayInAbilities",minVer=17)})
public interface NmsPacketPlayInAbilities extends NmsPacket
{
}
