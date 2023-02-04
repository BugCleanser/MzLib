package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.*;
import mz.lib.wrapper.WrappedObject;

@WrappedBukkitClass({@VersionName(value="nms.Recipes",minVer=14,maxVer=17),@VersionName(value="net.minecraft.world.item.crafting.Recipes",minVer=17)})
public interface NmsRecipesV14 extends WrappedBukkitObject
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
	@WrappedBukkitFieldAccessor({@VersionName("CRAFTING"),@VersionName(minVer=17, value="a")})
	NmsRecipesV14 staticCrafting();
	@WrappedBukkitFieldAccessor({@VersionName("SMELTING"),@VersionName(minVer=17, value="b")})
	NmsRecipesV14 staticSmelting();
	@WrappedBukkitFieldAccessor({@VersionName("BLASTING"),@VersionName(minVer=17, value="c")})
	NmsRecipesV14 staticBlasting();
	@WrappedBukkitFieldAccessor({@VersionName("SMOKING"),@VersionName(minVer=17, value="d")})
	NmsRecipesV14 staticSmoking();
	@WrappedBukkitFieldAccessor({@VersionName("CAMPFIRE_COOKING"),@VersionName(minVer=17, value="e")})
	NmsRecipesV14 staticCampfireCooking();
	@WrappedBukkitFieldAccessor({@VersionName("STONECUTTING"),@VersionName(minVer=17, value="f")})
	NmsRecipesV14 staticStonecutting();
	@WrappedBukkitFieldAccessor({@VersionName(value="SMITHING",minVer=16),@VersionName(minVer=17, value="g")})
	NmsRecipesV14 staticSmithingV16();
}
