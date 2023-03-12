package mz.lib.minecraft.recipe;

import org.bukkit.inventory.Recipe;

public class ShownRecipe<T extends Recipe>
{
	public ShownRecipeType type;
	public T data;
	
	public ShownRecipe(ShownRecipeType type,T data)
	{
		this.type=type;
		this.data=data;
	}
}
