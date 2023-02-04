package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;

@WrappedBukkitClass({@VersionName(value="nms.PlayerInventory",maxVer=17),@VersionName(value="net.minecraft.world.entity.player.PlayerInventory",minVer=17)})
public interface NmsPlayerInventory extends NmsIInventory
{
	@WrappedBukkitMethod({@VersionName("pickup"),@VersionName(value="@0",minVer=17)})
	boolean pickUp(NmsItemStack item);
}
