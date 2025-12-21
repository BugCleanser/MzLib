package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

//TODO
public class EntityDropItemEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();
    private final Item drop;
    private boolean cancel = false;

    public EntityDropItemEvent(Entity entity, Item drop)
    {
        super(entity);
        this.drop = drop;
    }

    public Item getItemDrop()
    {
        return this.drop;
    }

    public boolean isCancelled()
    {
        return this.cancel;
    }

    public void setCancelled(boolean cancel)
    {
        this.cancel = cancel;
    }

    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
