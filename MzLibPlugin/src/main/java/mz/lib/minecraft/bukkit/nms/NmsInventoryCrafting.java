package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;

@VersionalWrappedClass({@VersionalName(value="nms.InventoryCrafting",maxVer=17),@VersionalName(value="net.minecraft.world.inventory.InventoryCrafting",minVer=17)})
public interface NmsInventoryCrafting extends NmsIInventory
{
}
