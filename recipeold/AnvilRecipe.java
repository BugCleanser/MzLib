package mz.lib.minecraft.bukkitlegacy.recipeold;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class AnvilRecipe implements Recipe, Keyed
{
	public NamespacedKey key;
	public ItemStack result;
	public NmsRecipeItemStack ingredient1;
	public NmsRecipeItemStack ingredient2;
	public int cost;
	
	public AnvilRecipe(NamespacedKey key,ItemStack result,NmsRecipeItemStack ingredient1,NmsRecipeItemStack ingredient2,int cost)
	{
		this.key=key;
		this.result=result;
		this.ingredient1=ingredient1;
		this.ingredient2=ingredient2;
		this.cost=cost;
	}
	
	public static Map<NamespacedKey,AnvilRecipe> recipes=new HashMap<>();
	public static void init()
	{
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler(ignoreCancelled=true,priority=EventPriority.LOW)
			void onPrepareAnvil(PrepareAnvilEvent event)
			{
				for(AnvilRecipe r:recipes.values())
				{
					if(r.ingredient1.test(event.getInventory().getItem(0))&&r.ingredient2.test(event.getInventory().getItem(1)))
					{
						event.setResult(r.getResult(event.getInventory().getItem(0),event.getInventory().getItem(1),event.getInventory().getRenameText()));
						Bukkit.getScheduler().runTask(MzLib.instance,()->event.getInventory().setRepairCost(r.cost));
						return;
					}
				}
			}
		},MzLib.instance);
	}
	public static void reg(AnvilRecipe recipe)
	{
		recipes.put(recipe.getKey(),recipe);
	}
	public static void unreg(NamespacedKey key)
	{
		recipes.remove(key);
	}
	
	@Override
	public NamespacedKey getKey()
	{
		return key;
	}
	@Override
	public ItemStack getResult()
	{
		return result.clone();
	}
	public ItemStack getResult(ItemStack ingredient1,ItemStack ingredient2,String rename)
	{
		ItemStack result=getResult();
		if(rename.equals("")||result.getItemMeta().hasDisplayName())
			return result;
		ItemMeta im=result.getItemMeta();
		im.setDisplayName(rename);
		result.setItemMeta(im);
		return result;
	}
}
