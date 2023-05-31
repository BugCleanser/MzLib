package mz.lib.minecraft.bukkitlegacy.gui.inventory;

import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.bukkitlegacy.LangUtil;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkitlegacy.module.IModule;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.*;

public abstract class ListEditor<T> extends ListVisitor<T>
{
	public ListEditor(IModule module,String title,List<T> list)
	{
		super(module,title,list);
	}
	public ListEditor(IModule module,Function<Player,NmsIChatBaseComponent> titleGetter,List<T> list)
	{
		super(module,titleGetter,list);
	}
	
	@Override
	public void refresh()
	{
		super.refresh();
		
		if(page==getMaxPage())
			setButton(list.size()%36+9,p->new ItemStackBuilder(Material.NETHER_STAR).setName(LangUtil.getTranslated(p,"mzlib.listEditor.add")).get(),(t,p)->
			{
				T e=newElement(p);
				list.add(list.size(),e);
				regElement(list.size()-1);
				refresh();
			});
	}
	
	@Override
	public int getMaxPage()
	{
		return list.size()/36;
	}
	@Override
	public ItemStack generaldutyIconDecorate(HumanEntity player,ItemStack icon)
	{
		return new ItemStackBuilder(icon.clone()).addLore(LangUtil.getTranslated(player,"mzlib.listEditor.edit"),LangUtil.getTranslated(player,"mzlib.listEditor.insertBefore"),LangUtil.getTranslated(player,"mzlib.listEditor.remove")).get();
	}
	public abstract T newElement(HumanEntity player);
	public abstract void regElement(int index);
	public abstract void unregElement(int index);
	public abstract void editElement(int index,HumanEntity player);
	
	@Override
	public void onClickElement(int index,ClickType t,HumanEntity player)
	{
		switch(t)
		{
			case LEFT:
				editElement(index,player);
				break;
			case RIGHT:
				add(index,newElement(player));
				break;
			case SHIFT_LEFT:
				remove(index);
				break;
			default:
				break;
		}
	}
	
	public void add(int index,T element)
	{
		list.add(index,element);
		regElement(index);
		refresh();
	}
	public void remove(int index)
	{
		unregElement(index);
		list.remove(index);
		refresh();
		for(HumanEntity p:this.getInventory().getViewers())
		{
			if(p instanceof Player)
				((Player)p).updateInventory();
		}
	}
}
