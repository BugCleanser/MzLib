package mz.lib.minecraft.bukkitlegacy.recipelegacy;

import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.bukkitlegacy.LangUtil;
import mz.lib.minecraft.bukkitlegacy.gui.ViewList;
import mz.lib.minecraft.bukkitlegacy.gui.inventory.InputBox;
import mz.lib.minecraft.bukkitlegacy.gui.inventory.Menu;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkitlegacy.message.TextMessageComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;

public abstract class RecipeEditorUI<T extends Recipe> extends Menu
{
	public RecipeEditor recipeEditor;
	public EditableRecipe<T> lastRecipe;
	public EditableRecipe<T> recipe;
	public RecipeEditorUI(RecipeEditor<T> recipeEditor,int size,EditableRecipe<T> recipe)
	{
		super(recipeEditor,size,recipeEditor.getTypeName());
		this.recipeEditor=recipeEditor;
		this.lastRecipe=recipe;
		this.recipe=recipeEditor.clone(lastRecipe);
		this.titleModifier=(p,t)->
		{
			t.set(new TextMessageComponent(StringUtil.replaceStrings(LangUtil.getTranslated(p,"mzlib.recipesEditor.types.recipeTitle"),new MapEntry<>("%\\{type\\}",recipeEditor.getTypeName(p)),new MapEntry<>("%\\{id\\}",recipeEditor.getKey(recipe.data).toString()))).toNms());
		};
	}
	
	public void setKeySetButton(int slot)
	{
		setButton(slot,p->new ItemStackBuilder(Material.NAME_TAG).setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.fixedRecipe.setKey")).get(),(t,p)->
		{
			ViewList.get(p).go(new InputBox(this.module,pl->new TextMessageComponent(LangUtil.getTranslated(pl,"mzlib.recipesEditor.fixedRecipe.setKey")).toNms(),recipeEditor.getKey(recipe.data).toString())
			{
				@Override
				public void onEdit(Player player,String lastName)
				{
					try
					{
						NmsMinecraftKey.newInstance(getName());
						setErrMsg(null);
					}
					catch(Throwable e)
					{
						setErrMsg(p->LangUtil.getTranslated(p,"mzlib.recipesEditor.fixedRecipe.setKey.error"));
					}
				}
				@Override
				public void save(Player player)
				{
					NmsMinecraftKey key;
					try
					{
						key=NmsMinecraftKey.newInstance(getName());
					}
					catch(Throwable e)
					{
						return;
					}
					recipeEditor.setKey(recipe.data,key.toBukkit());
					ret(player);
				}
			});
		});
	}
	public void setDescription(int slot,String description)
	{
		setItem(slot,p->ItemStackBuilder.questionMark().setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.description")).setLore(LangUtil.getTranslated(p,description).split("\n")).get());
	}
	public void setSaveButton(int slot)
	{
		setButton(slot,p->ItemStackBuilder.checkmark().setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.save")).get(),(t,p)->
		{
			recipeEditor.remove(lastRecipe);
			try
			{
				recipeEditor.onSave(recipe);
				recipeEditor.add(recipe);
			}
			catch(Throwable e)
			{
				recipeEditor.add(lastRecipe);
				e.printStackTrace();
			}
			ret(p).refresh();
		});
	}
}
