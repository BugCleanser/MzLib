package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.mzlib.wrapper.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.InventoryHolder;

import java.util.*;

@VersionalWrappedClass({@VersionalName(value="nms.IInventory",maxVer=17),@VersionalName(value="net.minecraft.world.IInventory",minVer=17)})
public interface NmsIInventory extends VersionalWrappedObject
{
	@VersionalWrappedMethod(@VersionalName("getOwner"))
	InventoryHolder getOwner();
	
	@VersionalWrappedMethod(@VersionalName("getViewers"))
	List<HumanEntity> getViewers();
}
