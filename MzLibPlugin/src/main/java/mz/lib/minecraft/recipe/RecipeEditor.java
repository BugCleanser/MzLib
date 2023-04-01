package mz.lib.minecraft.recipe;

import mz.lib.*;
import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.ui.*;
import mz.lib.minecraft.bukkitlegacy.itemstack.*;
import mz.lib.minecraft.message.*;
import mz.lib.module.*;
import mz.lib.minecraft.ui.*;
import mz.lib.minecraft.ui.inventory.*;
import mz.lib.minecraft.message.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.*;

public abstract class RecipeEditor<T extends Recipe> extends ListEditor<T> implements IRegistrar<T>
{
	public RecipeEditor(MzModule module)
	{
		super(module,"",new ArrayList<>());
		this.titleModifier=(p,c)->c.set(new TextMessageComponent(StringUtil.replaceStrings(MinecraftLanguages.get(p,"mzlib.recipesEditor.kindRecipe.title"),new MapEntry<>("%\\{type}",getDisplayName(p)))).toNms());
	}
	
	public abstract ItemStack getIcon();
	
	public abstract List<T> getActiveRecipes();
	
	public abstract String getName();
	
	public abstract NamespacedKey getId(T recipe);
	public abstract void unregister(NamespacedKey id);
	
	@Override
	public void unregister(T obj)
	{
		unregister(getId(obj));
	}
	
	@Override
	public void regElement(int index)
	{
		this.register(module,list.get(index));
	}
	@Override
	public void unregElement(int index)
	{
		this.unregister(list.get(index));
	}
	
	@Override
	public ItemStack getIcon(int index,HumanEntity player)
	{
		return list.get(index).getResult().clone();
	}
	
	public String getDisplayName(HumanEntity player)
	{
		return MinecraftLanguages.get(player,"mzlib.recipesEditor.types."+getName());
	}
	
	@Override
	public void refresh()
	{
		this.list=getActiveRecipes();
		super.refresh();
	}
	
	public abstract T clone(T recipe);
	
	public int getCaseSize()
	{
		return 54;
	}
	
	public abstract void initCase(RecipeEditorCase recipeEditorCase);
	
	public void setCaseSlots(RecipeEditorCase recipeEditorCase,HumanEntity player,NmsContainer container)
	{
		for(int i=0;i<36;i++)
			container.setReadWrite(recipeEditorCase.size+i);
	}
	
	public abstract void refreshCase(RecipeEditorCase recipeEditorCase);
	
	public abstract void save(RecipeEditorCase recipeEditorCase,HumanEntity player);
	
	public class RecipeEditorCase extends Menu
	{
		public T recipe;
		public RecipeEditorCase(T recipe)
		{
			super(RecipeEditor.this.module,getCaseSize(),p->
			{
				Ref<NmsIChatBaseComponent> r=new Ref<>(null);
				RecipeEditor.this.titleModifier.accept(p,r);
				return r.get();
			});
			this.recipe=RecipeEditor.this.clone(recipe);
			initCase(this);
		}
		
		@Override
		public void setSlots(HumanEntity player,NmsContainer container)
		{
			super.setSlots(player,container);
			RecipeEditor.this.setCaseSlots(this,player,container);
		}
		
		@Override
		public void refresh()
		{
			RecipeEditor.this.refreshCase(this);
		}
		
		public void setSaveButton(int index)
		{
			setButton(index,p->new ItemStackBuilder(Material.END_CRYSTAL).setName(MinecraftLanguages.get(p,"mzlib.recipesEditor.save")).get(),(t,p)->
			{
				save(this,p);
				unregister(getId(recipe));
				register(null,recipe);
				ret(p);
			});
		}
	}
	
	@Override
	public void editElement(int index,HumanEntity player)
	{
		UIStack.get(player).go(new RecipeEditorCase(list.get(index)));
	}
	
	public static NmsRecipeItemStack getRecipeItemStack(ItemStack item)
	{
		if(ItemStackBuilder.isAir(item))
			return NmsRecipeItemStack.air();
		return NmsRecipeItemStack.hybrid(item);
	}
	public static ItemStack fromRecipeItemStack(NmsRecipeItemStack ris)
	{
		List<ItemStack> cs=ris.getChoices();
		if(cs.size()==0)
			return new ItemStack(Material.AIR);
		return cs.get(0);
	}
}
