package mz.lib.minecraft.bukkit.obc;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkit.nms.NmsIInventory;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.*;

@VersionalWrappedClass(@VersionalName("obc.inventory.CraftInventoryCustom"))
public interface ObcInventoryCustom extends VersionalWrappedObject
{
	@VersionalWrappedClass(@VersionalName("obc.inventory.CraftInventoryCustom$MinecraftInventory"))
	interface MinecraftInventory extends NmsIInventory
	{
		@WrappedMethod("getTitle")
		String getTitle();
	}
}
