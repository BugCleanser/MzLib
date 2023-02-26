package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.ITileInventory",maxVer=17),@VersionalName(value="net.minecraft.world.ITileInventory",minVer=17)})
public interface NmsITileInventory extends VersionalWrappedObject
{
	@VersionalWrappedMethod(@VersionalName(value="getScoreboardDisplayName",minVer=13))
	NmsIChatBaseComponent getScoreboardDisplayNameV13();
}
