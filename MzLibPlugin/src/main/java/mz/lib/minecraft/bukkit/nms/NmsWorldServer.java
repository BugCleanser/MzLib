package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.WorldServer",maxVer=17),@VersionalName(value="net.minecraft.world.level.WorldServer",minVer=17)})
public interface NmsWorldServer extends VersionalWrappedObject
{
}
