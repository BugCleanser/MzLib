package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.ContainerPlayer",maxVer=17),@VersionalName(value="net.minecraft.world.inventory.ContainerPlayer",minVer=17)})
public interface NmsContainerPlayer extends NmsContainer
{
}
