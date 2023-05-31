package mz.lib.minecraft.bukkit.wrappedobc;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappednms.NmsIInventory;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedMethod;

@WrappedBukkitClass(@VersionName("obc.inventory.CraftInventoryCustom"))
public interface ObcInventoryCustom extends WrappedBukkitObject
{
	@WrappedBukkitClass(@VersionName("obc.inventory.CraftInventoryCustom$MinecraftInventory"))
	interface MinecraftInventory extends NmsIInventory
	{
		@WrappedMethod("getTitle")
		String getTitle();
	}
}
