package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;

@WrappedBukkitClass({@VersionName(value="nms.EnumItemSlot",maxVer=17),@VersionName(value="net.minecraft.world.entity.EnumItemSlot",minVer=17)})
public interface NmsEnumItemSlot extends WrappedBukkitObject
{
}
