package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.EnumHand",maxVer=17),@VersionName(value="net.minecraft.world.EnumHand",minVer=17)})
public interface NmsEnumHand extends WrappedBukkitObject
{
}
