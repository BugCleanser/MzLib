package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;

@WrappedBukkitClass({@VersionName(value="nms.ContainerRecipeBook",maxVer=17),@VersionName(value="net.minecraft.world.inventory.ContainerRecipeBook",minVer=17)})
public interface NmsContainerRecipeBookV13 extends NmsContainer
{
	@WrappedBukkitMethod({@VersionName(value="@0",maxVer=17),@VersionName(value="@1",minVer=17)})
	int getWidth();
	@WrappedBukkitMethod({@VersionName(value="@1",maxVer=17),@VersionName(value="@2",minVer=17)})
	int getHeight();
	@WrappedBukkitMethod({@VersionName(value="@2",maxVer=17),@VersionName(value="@3",minVer=17)})
	int getSlotsNum();
}
