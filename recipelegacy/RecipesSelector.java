package mz.lib.minecraft.bukkit.recipelegacy;

import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.gui.ViewList;
import mz.lib.minecraft.bukkit.gui.inventory.ListVisitor;
import mz.lib.minecraft.bukkit.message.TextMessageComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;

public class RecipesSelector extends ListVisitor<RecipeEditor>
{
	public static RecipesSelector instance=new RecipesSelector();
	public RecipesSelector()
	{
		super(RecipeEditorRegistrar.instance,"RecipesSelector",new LinkedList<>());
		this.titleModifier=(player,title)->
		{
			title.set(new TextMessageComponent(LangUtil.getTranslated(player,"mzlib.recipesEditor.types.title")).toNms());
		};
	}
	
	@Override
	public ItemStack getIcon(int index,HumanEntity player)
	{
		return list.get(index).getIcon(player);
	}
	
	@Override
	public void onClickElement(int index,ClickType t,HumanEntity player)
	{
		ViewList.get(player).go(list.get(index).selector);
	}
}
