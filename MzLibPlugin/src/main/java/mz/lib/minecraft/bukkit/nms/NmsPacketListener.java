package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.PacketListener",maxVer=17),@VersionalName(value="net.minecraft.network.PacketListener",minVer=17)})
public interface NmsPacketListener extends VersionalWrappedObject
{
}
