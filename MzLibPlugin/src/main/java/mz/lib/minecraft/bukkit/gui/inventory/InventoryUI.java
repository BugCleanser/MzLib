package mz.lib.minecraft.bukkit.gui.inventory;

import mz.lib.*;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.entity.PlayerUtil;
import mz.lib.minecraft.bukkit.event.ShowInventoryItemEvent;
import mz.lib.minecraft.bukkit.event.WindowOpenEvent;
import mz.lib.minecraft.bukkit.gui.ViewList;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.module.IModule;
import mz.lib.minecraft.bukkit.gui.GUI;
import mz.lib.minecraft.bukkit.wrappednms.NmsContainer;
import mz.lib.minecraft.bukkit.wrappednms.NmsIChatBaseComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * InventoryUI
 */
public abstract class InventoryUI extends GUI implements InventoryHolder
{
	public Inventory inv;
	public int size;
	public BiConsumer<Player,Ref<NmsIChatBaseComponent>> titleModifier;
	public BiConsumer<Player,ItemStackBuilder>[] itemShowModifiers;
	
	public InventoryUI(IModule module,int size)
	{
		super(module);
		this.size=size;
		itemShowModifiers=TypeUtil.cast(new BiConsumer[size]);
	}
	
	public void setItem(int slot,ItemStack is)
	{
		getInventory().setItem(slot,is);
		itemShowModifiers[slot]=null;
	}
	public void setItem(int slot,Function<HumanEntity,ItemStack> is)
	{
		this.itemShowModifiers[slot]=(p,i)->
		{
			i.item=new ItemStackBuilder(is.apply(p)).item;
		};
	}
	public void onOpen(HumanEntity player)
	{
		refresh();
	}
	public void onClick(ClickType type,HumanEntity player,int rawSlot)
	{
	}
	public void setSlots(HumanEntity player,NmsContainer container)
	{
	}
	public void onClose(HumanEntity player)
	{
	}
	
	public static Inventory openingInventory;
	@Override
	public void open(HumanEntity player)
	{
		onOpen(player);
		openingInventory=inv;
		player.openInventory(getInventory());
		openingInventory=null;
		if(player instanceof Player)
			setSlots(player,PlayerUtil.getOpenContainer((Player) player));
	}
	@Override
	public void close(HumanEntity player)
	{
		player.closeInventory();
	}
	public List<HumanEntity> getViewers()
	{
		return inv.getViewers();
	}
	
	public Inventory getInventory()
	{
		return inv;
	}
	public void clear()
	{
		inv.clear();
		Arrays.fill(itemShowModifiers,null);
	}
	
	public static class Module extends AbsModule
	{
		public static Module instance=new Module();
		
		public Module()
		{
			super(MzLib.instance,WindowOpenEvent.Module.instance,ViewList.Module.instance);
		}
		
		@EventHandler(priority=EventPriority.LOW)
		void onWindowOpen(WindowOpenEvent event)
		{
			if(openingInventory!=null)
			{
				if(((InventoryUI)openingInventory.getHolder()).titleModifier!=null)
				{
					Ref<NmsIChatBaseComponent> r=new Ref<>(event.title);
					((InventoryUI)openingInventory.getHolder()).titleModifier.accept(event.player,r);
					event.title=r.get();
				}
			}
		}
		
		@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
		void onInventoryClose(InventoryCloseEvent event)
		{
			if(event.getInventory().getHolder() instanceof InventoryUI)
			{
				((InventoryUI) event.getInventory().getHolder()).onClose(event.getPlayer());
			}
		}
		
		@EventHandler(priority=EventPriority.LOW)
		void onShowInventoryItem(ShowInventoryItemEvent event)
		{
			InventoryHolder holder=event.player.getOpenInventory().getTopInventory().getHolder();
			if(holder instanceof InventoryUI)
			{
				if(event.slot>=0&&event.slot<holder.getInventory().getSize())
				{
					BiConsumer<Player,ItemStackBuilder> modifier=((InventoryUI) holder).itemShowModifiers[event.slot];
					if(modifier!=null)
					{
						modifier.accept(event.player,event.item);
					}
				}
			}
		}
		
		@EventHandler
		void onInventoryDrag(InventoryDragEvent event)
		{
			if(event.getInventory().getHolder() instanceof InventoryUI)
			{
				InventoryUI i=(InventoryUI) event.getInventory().getHolder();
				ClickType type=event.getType()==DragType.SINGLE ? ClickType.RIGHT : ClickType.LEFT;
				for(int s: event.getRawSlots())
				{
					i.onClick(type,event.getWhoClicked(),s);
				}
			}
		}
		
		@EventHandler
		void onInventoryClick(InventoryClickEvent event)
		{
			if(event.getInventory().getHolder() instanceof InventoryUI)
			{
				InventoryUI i=(InventoryUI) event.getInventory().getHolder();
				i.onClick(event.getClick(),event.getWhoClicked(),event.getRawSlot());
			}
		}
	}
}
