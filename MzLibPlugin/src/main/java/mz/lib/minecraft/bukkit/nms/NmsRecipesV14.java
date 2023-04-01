package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;

@VersionalWrappedClass({@VersionalName(value="nms.Recipes",minVer=14,maxVer=17),@VersionalName(value="net.minecraft.world.item.crafting.Recipes",minVer=17)})
public interface NmsRecipesV14 extends VersionalWrappedObject
{
	static NmsRecipesV14 crafting()
	{
		return WrappedObject.getStatic(NmsRecipesV14.class).staticCrafting();
	}
	static NmsRecipesV14 smelting()
	{
		return WrappedObject.getStatic(NmsRecipesV14.class).staticSmelting();
	}
	static NmsRecipesV14 blasting()
	{
		return WrappedObject.getStatic(NmsRecipesV14.class).staticBlasting();
	}
	static NmsRecipesV14 smoking()
	{
		return WrappedObject.getStatic(NmsRecipesV14.class).staticSmoking();
	}
	static NmsRecipesV14 campfireCooking()
	{
		return WrappedObject.getStatic(NmsRecipesV14.class).staticCampfireCooking();
	}
	static NmsRecipesV14 stonecutting()
	{
		return WrappedObject.getStatic(NmsRecipesV14.class).staticStonecutting();
	}
	static NmsRecipesV14 smithingV16()
	{
		return WrappedObject.getStatic(NmsRecipesV14.class).staticSmithingV16();
	}
	@VersionalWrappedFieldAccessor({@VersionalName("CRAFTING"),@VersionalName(minVer=17, value="a")})
	NmsRecipesV14 staticCrafting();
	@VersionalWrappedFieldAccessor({@VersionalName("SMELTING"),@VersionalName(minVer=17, value="b")})
	NmsRecipesV14 staticSmelting();
	@VersionalWrappedFieldAccessor({@VersionalName("BLASTING"),@VersionalName(minVer=17, value="c")})
	NmsRecipesV14 staticBlasting();
	@VersionalWrappedFieldAccessor({@VersionalName("SMOKING"),@VersionalName(minVer=17, value="d")})
	NmsRecipesV14 staticSmoking();
	@VersionalWrappedFieldAccessor({@VersionalName("CAMPFIRE_COOKING"),@VersionalName(minVer=17, value="e")})
	NmsRecipesV14 staticCampfireCooking();
	@VersionalWrappedFieldAccessor({@VersionalName("STONECUTTING"),@VersionalName(minVer=17, value="f")})
	NmsRecipesV14 staticStonecutting();
	@VersionalWrappedFieldAccessor({@VersionalName(value="SMITHING",minVer=16),@VersionalName(minVer=17, value="g")})
	NmsRecipesV14 staticSmithingV16();
}
