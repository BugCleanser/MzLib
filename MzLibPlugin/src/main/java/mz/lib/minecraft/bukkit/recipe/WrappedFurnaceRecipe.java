package mz.lib.minecraft.bukkit.recipe;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedClass;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

@WrappedClass("org.bukkit.inventory.FurnaceRecipe")
public interface WrappedFurnaceRecipe extends WrappedBukkitObject
{
	@WrappedBukkitFieldAccessor(@VersionName(value="ingredient",maxVer=13))
	void setIngredientV_13(ItemStack ingredient);
	default void setIngredient(ItemStack ingredient)
	{
		if(BukkitWrapper.v13)
			this.cast(WrappedCookingRecipeV13.class).setIngredient(ingredient);
		else
			setIngredientV_13(ingredient);
	}
	@WrappedBukkitFieldAccessor(@VersionName(value="output",maxVer=13))
	void setResultV_13(ItemStack result);
	default void setResult(ItemStack result)
	{
		if(BukkitWrapper.v13)
			this.cast(WrappedCookingRecipeV13.class).setResult(result);
		else
			setResultV_13(result);
	}
	
	default void setKeyV13(NamespacedKey key)
	{
		this.cast(WrappedCookingRecipeV13.class).setKey(key);
	}
	
	@Override
	FurnaceRecipe getRaw();
}
