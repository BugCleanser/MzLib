package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass({@VersionName(value="nms.IMaterial",minVer=13,maxVer=17),@VersionName(value="net.minecraft.world.level.IMaterial",minVer=17)})
public interface NmsIMaterialV13 extends WrappedBukkitObject
{
}
