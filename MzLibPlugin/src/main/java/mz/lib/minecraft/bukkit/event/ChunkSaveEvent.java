package mz.lib.minecraft.bukkit.event;

import mz.lib.*;
import org.bukkit.event.*;

import java.util.*;

public class ChunkSaveEvent implements IFutureEvent
{
	
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
