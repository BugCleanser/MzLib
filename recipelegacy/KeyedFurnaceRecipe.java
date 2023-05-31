package mz.lib.minecraft.bukkit.recipelegacy;

import mz.lib.StringUtil;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.UUID;

public class KeyedFurnaceRecipe implements Keyed, Recipe
{
	public FurnaceRecipe recipe;
	
	public KeyedFurnaceRecipe(FurnaceRecipe recipe)
	{
		this.recipe=recipe;
	}
	public KeyedFurnaceRecipe(ItemStack result,ItemStack source,float experience)
	{
		this(new FurnaceRecipe(result,ItemStackBuilder.getData(source),experience));
		WrappedObject.wrap(WrappedFurnaceRecipe.class,recipe).setIngredient(source);
	}
	
	@Override
	public NamespacedKey getKey()
	{
		if(recipe instanceof Keyed)
			return ((Keyed)recipe).getKey();
		return NamespacedKey.minecraft(UUID.nameUUIDFromBytes(new ItemStackBuilder(recipe.getInput()).toString().getBytes(StringUtil.UTF8)).toString());
	}
	
	@Override
	public ItemStack getResult()
	{
		return recipe.getResult();
	}
}
