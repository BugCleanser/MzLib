package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;

@WrappedBukkitClass({@VersionName(value="nms.InventoryCrafting",maxVer=17),@VersionName(value="net.minecraft.world.inventory.InventoryCrafting",minVer=17)})
public interface NmsInventoryCrafting extends NmsIInventory
{
}
