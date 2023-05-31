package mz.lib.minecraft.bukkit.recipe;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.wrapper.WrappedFieldAccessor;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

@WrappedBukkitClass(@VersionName(value="org.bukkit.inventory.CookingRecipe",minVer=13))
public interface WrappedCookingRecipeV13 extends WrappedObject
{
	@WrappedFieldAccessor("key")
	void setKey(NamespacedKey key);
	
	@WrappedFieldAccessor("output")
	void setResult(ItemStack result);
	
	@WrappedFieldAccessor("ingredient")
	void setIngredient(RecipeChoice ingredient);
	@SuppressWarnings("deprecation")
	default void setIngredient(ItemStack ingredient)
	{
		setIngredient(new RecipeChoice.ExactChoice(ingredient));
	}
	
	@Override
	CookingRecipe<?> getRaw();
}
