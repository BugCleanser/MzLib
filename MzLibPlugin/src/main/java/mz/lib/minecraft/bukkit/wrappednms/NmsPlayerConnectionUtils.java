package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;

@WrappedBukkitClass({@VersionName(value="nms.PlayerConnectionUtils",maxVer=17),@VersionName(value="net.minecraft.network.protocol.PlayerConnectionUtils",minVer=17)})
public interface NmsPlayerConnectionUtils extends WrappedBukkitObject
{

}
