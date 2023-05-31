package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass({@VersionName(value="nms.EnumInteractionResult",maxVer=17),@VersionName(value="net.minecraft.world.EnumInteractionResult",minVer=17)})
public interface NmsEnumInteractionResult extends WrappedBukkitObject
{
}
