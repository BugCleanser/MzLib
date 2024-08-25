package mz.mzlib.minecraft.event.entity;

import mz.mzlib.event.Event;
import mz.mzlib.minecraft.entity.Entity;

public abstract class EntityEvent extends Event
{
    public Entity entity;

    public EntityEvent(Entity entity)
    {
        this.entity = entity;
    }

    public Entity getEntity()
    {
        return this.entity;
    }
}
