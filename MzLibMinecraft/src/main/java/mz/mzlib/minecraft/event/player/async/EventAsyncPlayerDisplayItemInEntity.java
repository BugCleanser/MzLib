package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.entity.EntityItem;
import mz.mzlib.minecraft.entity.display.DisplayEntity;
import mz.mzlib.minecraft.event.player.async.displayentity.EventAsyncDisplayEntityData;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntityData;
import mz.mzlib.module.MzModule;

public abstract class EventAsyncPlayerDisplayItemInEntity extends EventAsyncPlayerDisplayItem implements EventAsyncByPacket<PacketS2cEntityData>
{
    public EventAsyncDisplayEntityData eventDisplayEntityData;
    public EventAsyncPlayerDisplayItemInEntity(EventAsyncDisplayEntityData eventDisplayEntityData, ItemStack original)
    {
        super(eventDisplayEntityData.getPacketEvent().getPlayer().unwrap(), original);
        this.eventDisplayEntityData = eventDisplayEntityData;
    }

    public EventAsyncDisplayEntityData getEventDisplayEntityData()
    {
        return this.eventDisplayEntityData;
    }

    @Override
    public PacketEvent.Specialized<? extends PacketS2cEntityData> getPacketEvent()
    {
        return this.getEventDisplayEntityData().getPacketEvent();
    }

    @Override
    public void sync(Runnable task)
    {
        EventAsyncByPacket.super.sync(task);
    }

    public DisplayEntity getDisplayEntity()
    {
        return this.getEventDisplayEntityData().getDisplayEntity();
    }

    @Override
    public void runLater(Runnable runnable)
    {
        this.getEventDisplayEntityData().runLater(runnable);
    }

    @Override
    public void call()
    {
        super.call();
    }

    public static class InEntityItem extends EventAsyncPlayerDisplayItemInEntity
    {
        public InEntityItem(EventAsyncDisplayEntityData eventDisplayEntityData, ItemStack original)
        {
            super(eventDisplayEntityData, original);
        }
        @Override
        public ItemStack getItemStack()
        {
            return this.eventDisplayEntityData.getPacket().getData(EntityItem.DATA_ADAPTER_ITEM).unwrap();
        }
        @Override
        public void setItemStack(ItemStack value)
        {
            this.eventDisplayEntityData.getPacket().putData(EntityItem.DATA_ADAPTER_ITEM, value);
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
            this.register(EventAsyncPlayerDisplayItemInEntity.class);
            this.register(InEntityItem.class);
            this.register(new EventListener<>(
                EventAsyncDisplayEntityData.class,
                event ->
                {
                    if(!EntityItem.ENTITY_TYPE.equals(event.getDisplayEntity().type))
                        return;
                    for(ItemStack original : event.getPacket().getData(EntityItem.DATA_ADAPTER_ITEM))
                    {
                        synchronized(event.getDisplayEntity())
                        {
                            new InEntityItem(event, original).call();
                        }
                    }
                }
            ));
        }
    }
}
