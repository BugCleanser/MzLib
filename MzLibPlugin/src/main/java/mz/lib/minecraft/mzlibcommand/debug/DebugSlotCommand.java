package mz.lib.minecraft.mzlibcommand.debug;

import mz.lib.*;
import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkit.nms.*;
import mz.lib.minecraft.bukkitlegacy.*;
import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.command.AbsLastCommandProcessor;
import mz.lib.minecraft.command.CommandHandler;
import mz.lib.minecraft.slot.CursorSlot;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.module.ISimpleModule;
import mz.lib.minecraft.bukkit.module.ModuleData;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.LinkedList;
import java.util.List;

public class DebugSlotCommand extends AbsLastCommandProcessor implements ISimpleModule
{
	public ModuleData moduleData=new ModuleData(MzLib.instance);
	public static DebugSlotCommand instance=new DebugSlotCommand();
	public DebugSlotCommand()
	{
		super(false,null,"slot");
	}
	
	List<Player> debugPlayers=new LinkedList<>();
	boolean forcePlace;
	
	@CommandHandler
	void execute(Player sender)
	{
		MzLib.sendPluginMessage(sender,MzLib.instance,StringUtil.replaceStrings(MinecraftLanguages.get(sender,"mzlib.command.debug.slot.success"),new MapEntry<>("%\\{state\\}",MinecraftLanguages.get(sender,ListUtil.toggle(debugPlayers,sender)?"mzlib.value.on":"mzlib.value.off"))));
	}
	
	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
	public void onInventoryClick(InventoryClickEvent event)
	{
		if(debugPlayers.contains(event.getWhoClicked()))
		{
			MzLib.sendPluginMessage(event.getWhoClicked(),MzLib.instance,event.getAction().name());
			MzLib.sendPluginMessage(event.getWhoClicked(),MzLib.instance,StringUtil.replaceStrings(MinecraftLanguages.get(event.getWhoClicked(),"mzlib.command.debug.slot.rawSlot"),new MapEntry<>("%\\{rawSlot\\}",event.getRawSlot()+"")));
			MzLib.sendPluginMessage(event.getWhoClicked(),MzLib.instance,StringUtil.replaceStrings(MinecraftLanguages.get(event.getWhoClicked(),"mzlib.command.debug.slot.slotNumber"),new MapEntry<>("%\\{slotNumber\\}",event.getSlot()+"")));
			if(event.getSlot()>=0&&(!new CursorSlot(event.getWhoClicked()).isEmpty()))
			{
				if(event.getClick()==ClickType.RIGHT||event.getClick()==ClickType.LEFT)
				{
					if(!NmsSlot.getClickedSlot(event).isAllowed(ObcItemStack.asNMSCopy(event.getWhoClicked().getItemOnCursor())))
					{
						WrappedObject.wrap(WrappedInventoryClickEvent.class,event).setAction(event.getClick()==ClickType.RIGHT? InventoryAction.PLACE_ONE:(ItemStackBuilder.isAir(event.getClickedInventory().getItem(event.getSlot()))||event.getClickedInventory().getItem(event.getSlot()).getAmount()+event.getWhoClicked().getItemOnCursor().getAmount()<=event.getWhoClicked().getItemOnCursor().getType().getMaxStackSize()?InventoryAction.PLACE_ALL:InventoryAction.PLACE_SOME));
						forcePlace=true;
					}
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void afterInventoryClick(InventoryClickEvent event)
	{
		if(forcePlace)
		{
			if(!event.isCancelled())
			{
				event.setCancelled(true);
				MzLib.sendPluginMessage(event.getWhoClicked(),MzLib.instance,MinecraftLanguages.get(event.getWhoClicked(),"mzlib.command.debug.slot.forcePlace"));
				switch(event.getAction())
				{
					case PLACE_ONE:
						if(ItemStackBuilder.isAir(event.getClickedInventory().getItem(event.getSlot())))
							event.getClickedInventory().setItem(event.getSlot(),new ItemStackBuilder(event.getWhoClicked().getItemOnCursor()).setCount(1).get());
						else
							event.getClickedInventory().setItem(event.getSlot(),new ItemStackBuilder(event.getClickedInventory().getItem(event.getSlot())).add(1).get());
						event.getWhoClicked().setItemOnCursor(new ItemStackBuilder(event.getWhoClicked().getItemOnCursor()).add(-1).get());
						break;
					case PLACE_ALL:
						event.getClickedInventory().setItem(event.getSlot(),new ItemStackBuilder(event.getWhoClicked().getItemOnCursor()).add(ItemStackBuilder.getCount(event.getClickedInventory().getItem(event.getSlot()))).get());
						event.getWhoClicked().setItemOnCursor(ItemStackBuilder.air().get());
						break;
					case PLACE_SOME:
						event.getWhoClicked().setItemOnCursor(new ItemStackBuilder(event.getWhoClicked().getItemOnCursor()).add(ItemStackBuilder.getCount(event.getClickedInventory().getItem(event.getSlot()))-event.getWhoClicked().getItemOnCursor().getType().getMaxStackSize()).get());
						event.getClickedInventory().setItem(event.getSlot(),new ItemStackBuilder(event.getWhoClicked().getItemOnCursor()).setCount(event.getWhoClicked().getItemOnCursor().getType().getMaxStackSize()).get());
						break;
					default:
						break;
				}
			}
			forcePlace=false;
		}
	}
	
	@Override
	public ModuleData getEnabledRef()
	{
		return moduleData;
	}
}
