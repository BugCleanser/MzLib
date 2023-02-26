package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedMethod;

@VersionalWrappedClass({@VersionalName(value="nms.PlayerInventory",maxVer=17),@VersionalName(value="net.minecraft.world.entity.player.PlayerInventory",minVer=17)})
public interface NmsPlayerInventory extends NmsIInventory
{
	@VersionalWrappedMethod({@VersionalName("pickup"),@VersionalName(value="@0",minVer=17)})
	boolean pickUp(NmsItemStack item);
}
