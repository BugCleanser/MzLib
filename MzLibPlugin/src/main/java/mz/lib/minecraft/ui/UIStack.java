package mz.lib.minecraft.ui;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UIStack
{
	public static Map<HumanEntity,UIStack> viewLists;
	public static UIStack get(HumanEntity player)
	{
		UIStack r=viewLists.get(player);
		if(r==null)
		{
			r=new UIStack(player);
			viewLists.put(player,r);
		}
		return r;
	}
	
	public HumanEntity player;
	public List<UI> views=new LinkedList<>();
	
	public UIStack(HumanEntity player)
	{
		this.player=player;
	}
	
	public UIStack start(UI UI)
	{
		views.clear();
		return go(UI);
	}
	public UIStack go(UI UI)
	{
		views.add(UI);
		UI.open(player);
		return this;
	}
	public UI ret()
	{
		UI last=views.remove(views.size()-1);
		UI UI=null;
		if(!views.isEmpty())
		{
			UI=views.get(views.size()-1);
			UI.open(player);
		}
		else
			last.close(player);
		return UI;
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
		for(UIStack vl:viewLists.values())
		{
			for(UI g:vl.views)
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
		for(UIStack vl:viewLists.values())
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
