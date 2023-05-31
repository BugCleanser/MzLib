package mz.lib.minecraft.bukkit.wrappedobc;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappednms.NmsIInventory;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedMethod;
import org.bukkit.inventory.Inventory;

@WrappedBukkitClass(@VersionName("obc.inventory.CraftInventory"))
public interface ObcInventory extends WrappedBukkitObject
{
	@WrappedMethod("getInventory")
	NmsIInventory getNms();
	
	@Override
	Inventory getRaw();
}
