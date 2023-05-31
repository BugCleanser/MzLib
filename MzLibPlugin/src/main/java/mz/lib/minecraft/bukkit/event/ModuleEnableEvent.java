package mz.lib.minecraft.bukkit.event;

import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.module.IModule;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.LinkedList;
import java.util.List;

/**
 * @see IModule
 */
public class ModuleEnableEvent extends Event implements IFutureEvent, Cancellable
{
	public final IModule module;
	
	public ModuleEnableEvent(IModule module)
	{
		this.module=module;
	}
	
	public static HandlerList handlers=new HandlerList();
	public boolean cancelled=false;
	public List<TypeUtil.Runnable> tasks=new LinkedList<>();
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	public HandlerList getHandlers()
	{
		return getHandlerList();
	}
	public boolean isCancelled()
	{
		return cancelled;
	}
	public void setCancelled(boolean cancelled)
	{
		this.cancelled=cancelled;
	}
	public List<TypeUtil.Runnable> getTasks()
	{
		return tasks;
	}
}
