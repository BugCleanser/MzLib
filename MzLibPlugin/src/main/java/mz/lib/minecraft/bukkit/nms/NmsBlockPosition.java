package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.BlockPosition",maxVer=17),@VersionalName(value="net.minecraft.core.BlockPosition",minVer=17)})
public interface NmsBlockPosition extends VersionalWrappedObject
{
}
