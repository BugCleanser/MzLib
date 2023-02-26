package mz.lib.minecraft.bukkitlegacy.gui;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.lib.minecraft.bukkitlegacy.module.IModule;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ViewList
{
	public static Map<HumanEntity,ViewList> viewLists;
	public static ViewList get(HumanEntity player)
	{
		ViewList r=viewLists.get(player);
		if(r==null)
		{
			r=new ViewList(player);
			viewLists.put(player,r);
		}
		return r;
	}
	
	public HumanEntity player;
	public List<GUI> views=new LinkedList<>();
	
	public ViewList(HumanEntity player)
	{
		this.player=player;
	}
	
	public ViewList start(GUI gui)
	{
		views.clear();
		return go(gui);
	}
	public ViewList go(GUI gui)
	{
		views.add(gui);
		gui.open(player);
		return this;
	}
	public GUI ret()
	{
		GUI last=views.remove(views.size()-1);
		GUI gui=null;
		if(!views.isEmpty())
		{
			gui=views.get(views.size()-1);
			gui.open(player);
		}
		else
			last.close(player);
		return gui;
	}
	public void close()
	{
		if(views.size()>0)
		{
			views.remove(views.size()-1).close(player);
			views.clear();
		}
	}
	public static void close(IModule module)
	{
		for(ViewList vl:viewLists.values())
		{
			for(GUI g:vl.views)
			{
				if(g.getModule()==module)
				{
					vl.close();
					break;
				}
			}
		}
	}
	public static void closeAll()
	{
		for(ViewList vl:viewLists.values())
		{
			vl.close();
		}
	}
	
	public static class Module extends AbsModule
	{
		public static Module instance=new Module();
		public Module()
		{
			super(MzLib.instance);
		}
		
		@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
		public void onPlayerQuit(PlayerQuitEvent event)
		{
			viewLists.remove(event.getPlayer());
		}
		
		@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
		public void onModuleDisable(ModuleDisableEvent event)
		{
			close(event.module);
		}
		
		@Override
		public void onEnable()
		{
			viewLists=new ConcurrentHashMap<>();
		}
		
		@Override
		public void onDisable()
		{
			closeAll();
		}
	}
}
