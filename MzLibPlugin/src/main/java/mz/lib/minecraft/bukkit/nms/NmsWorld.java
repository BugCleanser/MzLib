package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.World",maxVer=17),@VersionalName(value="net.minecraft.world.level.World",minVer=17)})
public interface NmsWorld extends VersionalWrappedObject
{
}
