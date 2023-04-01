package mz.lib.minecraft.bukkit.obc;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkit.nms.NmsIInventory;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.*;
import org.bukkit.inventory.Inventory;

@VersionalWrappedClass(@VersionalName("obc.inventory.CraftInventory"))
public interface ObcInventory extends VersionalWrappedObject
{
	@WrappedMethod("getInventory")
	NmsIInventory getNms();
	
	@Override
	Inventory getRaw();
}
