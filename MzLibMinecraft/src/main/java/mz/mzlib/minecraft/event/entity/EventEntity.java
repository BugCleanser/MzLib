package mz.mzlib.minecraft.event.entity;

import mz.mzlib.event.Event;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.module.MzModule;

public abstract class EventEntity extends Event
{
    public Entity entity;

    public EventEntity(Entity entity)
    {
        this.entity = entity;
    }

    public Entity getEntity()
    {
        return this.entity;
    }
    
    @Override
    public void call()
    {
    }
    
    public static class Module extends MzModule
    {
        public static Module instance=new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventEntity.class);
            
            this.register(EventEntityLivingDamage.Module.instance);
        }
    }
}
