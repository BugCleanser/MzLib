package mz.lib.minecraft.bukkit.recipeold;

import mz.lib.StringUtil;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.wrappednms.NmsRecipeItemStack;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.UUID;

public class FuelRecipe implements Keyed,Recipe
{
	public NmsRecipeItemStack i;
	public int time;
	public FuelRecipe(NmsRecipeItemStack i,int time)
	{
		this.i=i;
		this.time=time;
	}
	@Override
	public NamespacedKey getKey()
	{
		StringBuilder sb=new StringBuilder();
		for(ItemStack r:i.getChoices())
		{
			sb.append(new ItemStackBuilder(getResult()));
		}
		return NamespacedKey.minecraft(UUID.nameUUIDFromBytes(sb.toString().getBytes(StringUtil.UTF8)).toString());
	}
	@Override
	public ItemStack getResult()
	{
		return i.getChoices().get(0);
	}
}
