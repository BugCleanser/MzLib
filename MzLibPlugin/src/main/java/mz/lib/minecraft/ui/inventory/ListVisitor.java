package mz.lib.minecraft.ui.inventory;

import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.MinecraftLanguages;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkitlegacy.module.IModule;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.function.*;

public abstract class ListVisitor<T> extends Menu
{
	public List<T> list;
	public int page=0;
	
	public ListVisitor(IModule module,String title,List<T> list)
	{
		super(module,54,title);
		this.list=list;
	}
	public ListVisitor(IModule module,Function<Player,NmsIChatBaseComponent> titleGetter,List<T> list)
	{
		super(module,54,titleGetter);
		this.list=list;
	}
	
	@Override
	public void refresh()
	{
		this.clear();
		
		if(this.page>getMaxPage())
			this.page=getMaxPage();
		this.setRetButton(0);
		this.setExtra(ItemStackBuilder.blueStainedGlassPane().setName("§0").get(),1,2,3,4,5,6,7,8,45,46,47,48,49,50,51,52,53);
		if(page>0)
			this.setButton(46,p->ItemStackBuilder.leftArrow().setName(MinecraftLanguages.get(p,"mzlib.menu.pageUp")).get(),(t,p)->
			{
				page--;
				refresh();
			});
		if(page<getMaxPage())
			this.setButton(52,p->ItemStackBuilder.rightArrow().setName(MinecraftLanguages.get(p,"mzlib.menu.pageDown")).get(),(t,p)->
			{
				page++;
				refresh();
			});
		int i;
		for(i=0;i<36&&i+page*36<list.size();i++)
		{
			int index=i+page*36;
			this.setButton(i+9,p->generaldutyIconDecorate(p,getIcon(index,p)),(t,p)->onClickElement(index,t,p));
		}
	}
	public int getMaxPage()
	{
		return (list.size()-1)/36;
	}
	
	public abstract ItemStack getIcon(int index,HumanEntity player);
	public ItemStack generaldutyIconDecorate(HumanEntity player,ItemStack icon)
	{
		return icon;
	}
	public abstract void onClickElement(int index,ClickType t,HumanEntity player);
}
