package mz.lib.minecraft.bukkit.gui.inventory;

import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.module.IModule;
import mz.lib.minecraft.bukkit.wrappednms.NmsContainer;
import mz.lib.minecraft.bukkit.wrappednms.NmsIChatBaseComponent;
import mz.lib.minecraft.bukkit.wrappednms.NmsSlot;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class Menu extends ChestUI
{
	public Map<Integer,BiConsumer<ClickType,HumanEntity>> buttons=new HashMap<>();
	
	public Menu(IModule module,int size,String title)
	{
		super(module,size,title);
	}
	public Menu(IModule module,int size,Function<Player,NmsIChatBaseComponent> titleGetter)
	{
		super(module,size,titleGetter);
	}
	
	public Menu setButton(int slot,ItemStack ico,BiConsumer<ClickType,HumanEntity> onClick)
	{
		buttons.put(slot,onClick);
		if(ico!=null)
			this.getInventory().setItem(slot,ico);
		return this;
	}
	public Menu setButton(int slot,Function<HumanEntity,ItemStack> ico,BiConsumer<ClickType,HumanEntity> onClick)
	{
		setButton(slot,new ItemStack(Material.AIR),onClick);
		setItem(slot,ico);
		return this;
	}
	public void setRetButton(int slot)
	{
		this.setButton(slot,p->ItemStackBuilder.returnArrow().setName(LangUtil.getTranslated(p,"mzlib.menu.return")).get(),(t,p)->ret(p));
	}
	@Override
	public void clear()
	{
		super.clear();
		this.buttons.clear();
	}
	@Override
	public void setSlots(HumanEntity player,NmsContainer container)
	{
		container.getSlots().replaceAll(raw->NmsSlot.showOnly(WrappedObject.wrap(NmsSlot.class,raw)).getRaw());
	}
	public Menu setExtra(ItemStack is,int... slots)
	{
		for(int slot: slots)
			setItem(slot,is);
		return this;
	}
	public Menu setExtra(Function<HumanEntity,ItemStack> is,int... slots)
	{
		for(int slot: slots)
		{
			setItem(slot,is);
		}
		return this;
	}
	
	@Override
	public void onClick(ClickType type,HumanEntity player,int slot)
	{
		if(buttons.containsKey(slot))
			buttons.get(slot).accept(type,player);
	}
}
