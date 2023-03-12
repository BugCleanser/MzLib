package mz.lib.minecraft.recipe;

import mz.lib.minecraft.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.wrapper.WrappedClass;
import mz.mzlib.*;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

@WrappedClass("org.bukkit.inventory.FurnaceRecipe")
public interface WrappedFurnaceRecipe extends VersionalWrappedObject
{
	@VersionalWrappedFieldAccessor(@VersionalName(value="ingredient",maxVer=13))
	void setIngredientV_13(ItemStack ingredient);
	default void setIngredient(ItemStack ingredient)
	{
		if(Server.instance.v13)
			this.cast(WrappedCookingRecipeV13.class).setIngredient(ingredient);
		else
			setIngredientV_13(ingredient);
	}
	@VersionalWrappedFieldAccessor(@VersionalName(value="output",maxVer=13))
	void setResultV_13(ItemStack result);
	default void setResult(ItemStack result)
	{
		if(Server.instance.v13)
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
