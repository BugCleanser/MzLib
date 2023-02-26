package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.mzlib.*;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.ContainerRecipeBook",maxVer=17),@VersionalName(value="net.minecraft.world.inventory.ContainerRecipeBook",minVer=17)})
public interface NmsContainerRecipeBookV13 extends NmsContainer
{
	@VersionalWrappedMethod({@VersionalName(value="@0",maxVer=17),@VersionalName(value="@1",minVer=17)})
	int getWidth();
	@VersionalWrappedMethod({@VersionalName(value="@1",maxVer=17),@VersionalName(value="@2",minVer=17)})
	int getHeight();
	@VersionalWrappedMethod({@VersionalName(value="@2",maxVer=17),@VersionalName(value="@3",minVer=17)})
	int getSlotsNum();
}
