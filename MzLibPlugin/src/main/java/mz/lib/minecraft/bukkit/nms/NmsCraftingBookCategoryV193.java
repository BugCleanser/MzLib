package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.*;
import mz.mzlib.*;
import mz.mzlib.wrapper.*;

@VersionalWrappedClass(@VersionalName(value="net.minecraft.world.item.crafting.CraftingBookCategory",minVer=19.3f))
public interface NmsCraftingBookCategoryV193 extends VersionalWrappedObject
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
	
	@VersionalWrappedFieldAccessor(@VersionalName("#0"))
	NmsCraftingBookCategoryV193 staticGetBuilding();
	@VersionalWrappedFieldAccessor(@VersionalName("#1"))
	NmsCraftingBookCategoryV193 staticGetRedstone();
	@VersionalWrappedFieldAccessor(@VersionalName("#2"))
	NmsCraftingBookCategoryV193 staticGetEquipment();
	@VersionalWrappedFieldAccessor(@VersionalName("#3"))
	NmsCraftingBookCategoryV193 staticGetMisc();
}
