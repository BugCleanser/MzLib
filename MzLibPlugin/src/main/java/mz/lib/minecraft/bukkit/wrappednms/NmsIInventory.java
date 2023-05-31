package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitMethod;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

@WrappedBukkitClass({@VersionName(value="nms.IInventory",maxVer=17),@VersionName(value="net.minecraft.world.IInventory",minVer=17)})
public interface NmsIInventory extends WrappedBukkitObject
{
	@WrappedBukkitMethod(@VersionName("getOwner"))
	InventoryHolder getOwner();
	
	@WrappedBukkitMethod(@VersionName("getViewers"))
	List<HumanEntity> getViewers();
}
