package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass({@VersionName(value="nms.BlockPosition",maxVer=17),@VersionName(value="net.minecraft.core.BlockPosition",minVer=17)})
public interface NmsBlockPosition extends WrappedBukkitObject
{
}
