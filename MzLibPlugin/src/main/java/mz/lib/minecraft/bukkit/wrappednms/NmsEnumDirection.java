package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.EnumDirection",maxVer=17),@VersionName(value="net.minecraft.core.EnumDirection",minVer=17)})
public interface NmsEnumDirection extends WrappedBukkitObject
{
}
