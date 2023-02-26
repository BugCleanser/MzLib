package mz.lib.minecraft.bukkitlegacy.event;

import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkit.nms.NmsEntityFishingHook;
import org.bukkit.entity.FishHook;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityEvent;

import java.util.LinkedList;
import java.util.List;

/**
 * @see NmsEntityFishingHook
 */
public class FishingHookClearEvent extends EntityEvent implements IFutureEvent, Cancellable
{
	public FishingHookClearReason reason;
	public FishingHookClearEvent(FishHook hook,FishingHookClearReason reason)
	{
		super(hook);
		this.reason=reason;
	}
	
	public enum FishingHookClearReason
	{
		@Deprecated NOPLAYER,
		NOROD,
		TOOFAR
	}
	public FishingHookClearReason getReason()
	{
		return reason;
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
