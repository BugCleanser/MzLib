package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.*;

@WrappedBukkitClass({@VersionName(value="nms.WorldServer",maxVer=17),@VersionName(value={"net.minecraft.world.level.WorldServer","net.minecraft.server.level.WorldServer"},minVer=17)})
public interface NmsWorldServer extends VersionalWrappedObject
{
}
