package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;

@WrappedBukkitClass({@VersionName(value="nms.World",maxVer=17),@VersionName(value="net.minecraft.world.level.World",minVer=17)})
public interface NmsWorld extends WrappedBukkitObject
{
}
