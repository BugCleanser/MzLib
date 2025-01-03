package mz.mzlib.minecraft.permission.dynamic;

import mz.mzlib.event.Event;
import mz.mzlib.minecraft.entity.Entity;

public abstract class EventCheckPermission extends Event
{
    public Entity entity;
    public EventCheckPermission(Entity entity)
    {
        this.entity = entity;
    }
    
    public Entity getEntity()
    {
        return this.entity;
    }
    public void deny()
    {
        this.setCancelled(true);
    }
    
    public boolean check()
    {
        this.call();
        return !this.isCancelled();
    }
    
    @Override
    public void call()
    {
    }
}
