package mz.mzlib.minecraft.event.player;

import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.entity.EntityItem;
import mz.mzlib.minecraft.entity.display.DisplayEntity;
import mz.mzlib.minecraft.event.player.displayentity.EventDisplayEntityDataAsync;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.module.MzModule;

public abstract class EventPlayerDisplayItemInEntity extends EventPlayerDisplayItem
{
    public EventDisplayEntityDataAsync eventDisplayEntityData;
    public EventPlayerDisplayItemInEntity(EventDisplayEntityDataAsync eventDisplayEntityData)
    {
        super(eventDisplayEntityData.packetEvent);
        this.eventDisplayEntityData = eventDisplayEntityData;
    }
    
    public EventDisplayEntityDataAsync getEventDisplayEntityData()
    {
        return this.eventDisplayEntityData;
    }
    
    public DisplayEntity getDisplayEntity()
    {
        return this.getEventDisplayEntityData().getDisplayEntity();
    }
    
    @Override
    public void call()
    {
        super.call();
    }
    
    public static class InEntityItem extends EventPlayerDisplayItemInEntity
    {
        public InEntityItem(EventDisplayEntityDataAsync eventDisplayEntityData)
        {
            super(eventDisplayEntityData);
        }
        @Override
        public ItemStack getItemStack()
        {
            return this.getEventDisplayEntityData().getNewData(EntityItem.dataTypeItem(), ItemStack::create);
        }
        @Override
        public void setItemStack(ItemStack value)
        {
            this.getEventDisplayEntityData().putNewData(EntityItem.dataTypeItem(), value);
        }
        
        @Override
        public void call()
        {
            super.call();
        }
    }
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventPlayerDisplayItemInEntity.class);
            this.register(InEntityItem.class);
            this.register(new EventListener<>(EventDisplayEntityDataAsync.class, event->
            {
                if(!event.getDisplayEntity().type.equals(EntityItem.ENTITY_TYPE))
                    return;
                if(event.getNewData0(EntityItem.dataTypeItem())==null)
                    return;
                event.sync(()->
                {
                    synchronized(event.getDisplayEntity())
                    {
                        new InEntityItem(event).call();
                    }
                });
            }));
        }
    }
}
