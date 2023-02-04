package mz.lib.minecraft.bukkit.recipelegacy;

import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.gui.inventory.ListEditor;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.message.TextMessageComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.LinkedList;

public class RecipeSelector<T extends Recipe> extends ListEditor<EditableRecipe<T>>
{
	public RecipeEditor<T> recipeEditor;
	
	public RecipeSelector(RecipeEditor editor)
	{
		super(editor,"RecipeEditorUI",new LinkedList<>());
		this.recipeEditor=editor;
		this.titleModifier=(player,title)->
		{
			title.set(new TextMessageComponent(StringUtil.replaceStrings(LangUtil.getTranslated(player,"mzlib.recipesEditor.kindRecipe.title"),new MapEntry<>("%\\{type\\}",editor.getTypeName(player)))).toNms());
		};
	}
	
	@Override
	public ItemStack getIcon(int index,HumanEntity player)
	{
		return recipeEditor.getIcon(list.get(index),player);
	}
	@Override
	public EditableRecipe<T> newElement(HumanEntity player)
	{
		return new EditableRecipe<>(EditableRecipe.RecipeType.FIXED,recipeEditor.newRecipe());
	}
	@Override
	public void regElement(int index)
	{
		recipeEditor.register(this.list.get(index));
	}
	@Override
	public void unregElement(int index)
	{
		recipeEditor.unregister(this.list.get(index));
	}
	@Override
	public void editElement(int index,HumanEntity player)
	{
		recipeEditor.edit(player,this.list.get(index));
	}
	
	@Override
	public void refresh()
	{
		super.refresh();
		setButton(8,p->ItemStackBuilder.enderEye().setName(LangUtil.getTranslated(p,"mzlib.recipesEditor.refreshActives.name")).setLore(LangUtil.getTranslated(p,"mzlib.recipesEditor.refreshActives.lore").split("\n")).get(),(t,p)->
		{
			recipeEditor.refreshActives();
		});
	}
}
