package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass({@VersionName(value="nms.ITileInventory",maxVer=17),@VersionName(value="net.minecraft.world.ITileInventory",minVer=17)})
public interface NmsITileInventory extends WrappedBukkitObject
{
	@WrappedBukkitMethod(@VersionName(value="getScoreboardDisplayName",minVer=13))
	NmsIChatBaseComponent getScoreboardDisplayNameV13();
}
