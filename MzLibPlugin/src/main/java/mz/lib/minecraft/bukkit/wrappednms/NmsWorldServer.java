package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;

@WrappedBukkitClass({@VersionName(value="nms.WorldServer",maxVer=17),@VersionName(value={"net.minecraft.world.level.WorldServer","net.minecraft.server.level.WorldServer"},minVer=17)})
public interface NmsWorldServer extends WrappedBukkitObject
{
}
