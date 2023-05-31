package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.*;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.*;

@WrappedBukkitClass(@VersionName(value="net.minecraft.world.item.crafting.CraftingBookCategory",minVer=19.3f))
public interface NmsCraftingBookCategoryV193 extends WrappedBukkitObject
{
	static NmsCraftingBookCategoryV193 getBuilding()
	{
		return WrappedObject.getStatic(NmsCraftingBookCategoryV193.class).staticGetBuilding();
	}
	static NmsCraftingBookCategoryV193 getRedstone()
	{
		return WrappedObject.getStatic(NmsCraftingBookCategoryV193.class).staticGetRedstone();
	}
	static NmsCraftingBookCategoryV193 getEquipment()
	{
		return WrappedObject.getStatic(NmsCraftingBookCategoryV193.class).staticGetEquipment();
	}
	static NmsCraftingBookCategoryV193 getMisc()
	{
		return WrappedObject.getStatic(NmsCraftingBookCategoryV193.class).staticGetMisc();
	}
	
	@WrappedBukkitFieldAccessor(@VersionName("#0"))
	NmsCraftingBookCategoryV193 staticGetBuilding();
	@WrappedBukkitFieldAccessor(@VersionName("#1"))
	NmsCraftingBookCategoryV193 staticGetRedstone();
	@WrappedBukkitFieldAccessor(@VersionName("#2"))
	NmsCraftingBookCategoryV193 staticGetEquipment();
	@WrappedBukkitFieldAccessor(@VersionName("#3"))
	NmsCraftingBookCategoryV193 staticGetMisc();
}
