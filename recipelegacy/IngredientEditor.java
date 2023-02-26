package mz.lib.minecraft.bukkitlegacy.recipelegacy;

import mz.lib.minecraft.bukkitlegacy.LangUtil;
import mz.lib.minecraft.bukkitlegacy.gui.inventory.Menu;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkitlegacy.message.TextMessageComponent;
import mz.lib.minecraft.bukkitlegacy.module.IModule;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IngredientEditor extends Menu
{
	public NmsRecipeItemStack ingredient;
	public Consumer<NmsRecipeItemStack> onSave;
	
	public IngredientEditor(IModule module,NmsRecipeItemStack ingredient,Consumer<NmsRecipeItemStack> onSave)
	{
		super(module,36,"IngredientEditor");
		this.ingredient=ingredient;
		this.onSave=onSave;
		this.titleModifier=(p,t)->
		{
			t.set(new TextMessageComponent(LangUtil.getTranslated(p,"mzlib.recipesEditor.ingredientEditor.title")).toNms());
		};
	}
	@Override
	public void setSlots(HumanEntity player,NmsContainer container)
	{
		for(int i=0;i<9;i++)
		{
			container.setSlot(i,NmsSlot.showOnly(container.getSlot(i)));
			container.setSlot(i+27,NmsSlot.showOnly(container.getSlot(i+27)));
		}
	}
	@Override
	public void refresh()
	{
		this.clear();
		
		for(int i=0;i<9;i++)
		{
			getInventory().setItem(i,ItemStackBuilder.blueStainedGlassPane().setName("§0").get());
			getInventory().setItem(i+27,ItemStackBuilder.blueStainedGlassPane().setName("§0").get());
		}
		setRetButton(0);
		setItem(35,p->ItemStackBuilder.checkmark().setName(LangUtil.getTranslated(p,"mzlib.inputBox.OK")).get());
		if(ingredient!=null)
		{
			List<ItemStack> choices=ingredient.getChoices();
			for(int i=0;i<choices.size();i++)
			{
				getInventory().setItem(i+9,choices.get(i));
			}
		}
	}
	@Override
	public void onClick(ClickType type,HumanEntity player,int rawSlot)
	{
		super.onClick(type,player,rawSlot);
		if(rawSlot==35)
		{
			List<ItemStack> l=new ArrayList<>();
			for(int i=0;i<18;i++)
			{
				if(!ItemStackBuilder.isAir(getInventory().getItem(i+9)))
				{
					ItemStack is=getInventory().getItem(i+9);
					is.setAmount(1);
					l.add(is);
				}
			}
			onSave.accept(NmsRecipeItemStack.hybrid(l.toArray(new ItemStack[0])));
			ret(player);
		}
	}
}
