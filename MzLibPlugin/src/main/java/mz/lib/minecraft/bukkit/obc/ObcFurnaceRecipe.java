package mz.lib.minecraft.bukkit.obc;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkitlegacy.nothing.*;
import mz.lib.minecraft.bukkitlegacy.recipe.*;
import mz.lib.minecraft.nothing.*;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.nothing.*;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@VersionalWrappedClass(@VersionalName("obc.inventory.CraftFurnaceRecipe"))
public interface ObcFurnaceRecipe extends WrappedFurnaceRecipe, VersionalNothing
{
	@WrappedConstructor
	ObcFurnaceRecipe staticNewInstanceV_13(ItemStack result,ItemStack source);
	static ObcFurnaceRecipe newInstanceV_13(ItemStack result,ItemStack source)
	{
		return WrappedObject.getStatic(ObcFurnaceRecipe.class).staticNewInstanceV_13(result,source);
	}
	
	@VersionalNothingInject(name = @VersionalName(maxVer = 13, value = "fromBukkitRecipe"), args = {FurnaceRecipe.class}, location = NothingLocation.FRONT)
	static Optional<ObcFurnaceRecipe> fromBukkitRecipeV_13(@LocalVar(0) FurnaceRecipe recipe)
	{
		if(WrappedObject.getRawClass(ObcFurnaceRecipe.class).isAssignableFrom(recipe.getClass()))
			return Optional.ofNullable(WrappedObject.wrap(ObcFurnaceRecipe.class,recipe));
		ObcFurnaceRecipe r=newInstanceV_13(recipe.getResult(),recipe.getInput());
		r.setIngredient(recipe.getInput());
		return Optional.ofNullable(r);
	}
}
