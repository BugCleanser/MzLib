package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;

@WrappedBukkitClass({@VersionName(value="nms.Packet",maxVer=17),@VersionName(value="net.minecraft.network.protocol.Packet",minVer=17)})
public interface NmsPacket extends WrappedBukkitObject
{
}
