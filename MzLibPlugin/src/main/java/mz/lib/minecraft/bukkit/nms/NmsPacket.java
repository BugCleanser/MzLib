package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.Packet",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.Packet",minVer=17)})
public interface NmsPacket extends VersionalWrappedObject
{
}
