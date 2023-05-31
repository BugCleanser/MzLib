package mz.lib.minecraft.bukkit.recipe;

import mz.lib.MapEntry;
import mz.lib.Ref;
import mz.lib.StringUtil;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.gui.ViewList;
import mz.lib.minecraft.bukkit.gui.inventory.ListEditor;
import mz.lib.minecraft.bukkit.gui.inventory.Menu;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.message.TextMessageComponent;
import mz.lib.minecraft.bukkit.module.IModule;
import mz.lib.minecraft.bukkit.module.IRegistrar;
import mz.lib.minecraft.bukkit.wrappednms.NmsContainer;
import mz.lib.minecraft.bukkit.wrappednms.NmsIChatBaseComponent;
import mz.lib.minecraft.bukkit.wrappednms.NmsRecipeItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.List;

public abstract class RecipeEditor<T extends Recipe> extends ListEditor<T> implements IRegistrar<T>
{
	public RecipeEditor(IModule module)
	{
		super(module,"",new ArrayList<>());
		this.titleModifier=(p,c)->c.set(new TextMessageComponent(StringUtil.replaceStrings(LangUtil.getTranslated(p,"mzlib.recipesEditor.kindRecipe.title"),new MapEntry<>("%\\{type}",getDisplayName(p)))).toNms());
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
		this.register(list.get(index));
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
		return LangUtil.getTranslated(player,"mzlib.recipesEditor.types."+getName());
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
			setButton(index,p->new ItemStackBuilder(Material.END_CRYSTAL).setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.save")).get(),(t,p)->
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
		ViewList.get(player).go(new RecipeEditorCase(list.get(index)));
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
