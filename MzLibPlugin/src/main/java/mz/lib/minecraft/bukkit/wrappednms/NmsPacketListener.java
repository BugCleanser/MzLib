package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;

@WrappedBukkitClass({@VersionName(value="nms.PacketListener",maxVer=17),@VersionName(value="net.minecraft.network.PacketListener",minVer=17)})
public interface NmsPacketListener extends WrappedBukkitObject
{
}
