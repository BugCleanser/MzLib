package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;

@WrappedBukkitClass({@VersionName(value="nms.ContainerPlayer",maxVer=17),@VersionName(value="net.minecraft.world.inventory.ContainerPlayer",minVer=17)})
public interface NmsContainerPlayer extends NmsContainer
{
}
