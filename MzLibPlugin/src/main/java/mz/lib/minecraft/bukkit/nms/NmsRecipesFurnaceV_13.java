package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.nothing.*;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import mz.lib.Optional;
import mz.lib.minecraft.nothing.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.nothing.*;
import mz.lib.wrapper.WrappedFieldAccessor;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import mz.lib.wrapper.*;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Optional
@VersionalWrappedClass(@VersionalName(value="nms.RecipesFurnace",maxVer=13))
public interface NmsRecipesFurnaceV_13 extends VersionalWrappedObject, VersionalNothing
{
	@WrappedMethod("getInstance")
	NmsRecipesFurnaceV_13 staticGetInstance();
	static NmsRecipesFurnaceV_13 getInstance()
	{
		return WrappedObject.getStatic(NmsRecipesFurnaceV_13.class).staticGetInstance();
	}
	
	@WrappedFieldAccessor("recipes")
	Map<Object,Object> getRecipes();
	@WrappedFieldAccessor("experience")
	Map<Object,Float> getRecipesExperience();
	
	@WrappedFieldAccessor("customRecipes")
	Map<Object,Object> getCustomRecipes();
	@WrappedFieldAccessor("customExperience")
	Map<Object,Float> getCustomRecipesExperience();
	
	@NothingInject(name = "a", args = {NmsItemStack.class,NmsItemStack.class}, location = NothingLocation.FRONT)
	default java.util.Optional<Boolean> test(@LocalVar(1) NmsItemStack is,@LocalVar(2) NmsItemStack raw)
	{
		return java.util.Optional.of(test(ObcItemStack.asBukkitCopy(is),ObcItemStack.asBukkitCopy(raw)));
	}
	default boolean test(ItemStack is,ItemStack raw)
	{
		if(raw.getDurability()==32767)
		{
			raw=new ItemStack(raw);
			raw.setDurability(is.getDurability());
		}
		return raw.isSimilar(is);
	}
	
	static void regRecipe(ItemStack raw,ItemStack result,float experience)
	{
		getInstance().getCustomRecipes().put(ObcItemStack.asNMSCopy(raw).getRaw(),ObcItemStack.asNMSCopy(result).getRaw());
		getInstance().getCustomRecipesExperience().put(ObcItemStack.asNMSCopy(raw).getRaw(),experience);
	}
	static void regRecipe(FurnaceRecipe recipe)
	{
		regRecipe(recipe.getInput(),recipe.getResult(),recipe.getExperience());
	}
	static void unregRecipe(ItemStack raw)
	{
		getInstance().getRecipes().remove(ObcItemStack.asNMSCopy(raw).getRaw());
		getInstance().getCustomRecipes().remove(ObcItemStack.asNMSCopy(raw).getRaw());
	}
}
