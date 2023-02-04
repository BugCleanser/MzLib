package mz.lib.minecraft.bukkit.wrappedobc;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.nothing.*;
import mz.lib.minecraft.bukkit.recipe.*;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.nothing.*;
import mz.lib.wrapper.WrappedConstructor;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@WrappedBukkitClass(@VersionName("obc.inventory.CraftFurnaceRecipe"))
public interface ObcFurnaceRecipe extends WrappedFurnaceRecipe, NothingBukkit
{
	@WrappedConstructor
	ObcFurnaceRecipe staticNewInstanceV_13(ItemStack result,ItemStack source);
	static ObcFurnaceRecipe newInstanceV_13(ItemStack result,ItemStack source)
	{
		return WrappedObject.getStatic(ObcFurnaceRecipe.class).staticNewInstanceV_13(result,source);
	}
	
	@NothingBukkitInject(name = @VersionName(maxVer = 13, value = "fromBukkitRecipe"), args = {FurnaceRecipe.class}, location = NothingLocation.FRONT)
	static Optional<ObcFurnaceRecipe> fromBukkitRecipeV_13(@LocalVar(0) FurnaceRecipe recipe)
	{
		if(WrappedObject.getRawClass(ObcFurnaceRecipe.class).isAssignableFrom(recipe.getClass()))
			return Optional.ofNullable(WrappedObject.wrap(ObcFurnaceRecipe.class,recipe));
		ObcFurnaceRecipe r=newInstanceV_13(recipe.getResult(),recipe.getInput());
		r.setIngredient(recipe.getInput());
		return Optional.ofNullable(r);
	}
}
