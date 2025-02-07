package mz.mzlib.minecraft.event.player.async.displayentity;

import mz.mzlib.minecraft.entity.data.EntityDataHolder;
import mz.mzlib.minecraft.entity.data.EntityDataKey;
import mz.mzlib.minecraft.entity.display.DisplayEntity;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntityData;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class EventAsyncDisplayEntityData extends EventAsyncDisplayEntity<PacketS2cEntityData> implements EntityDataHolder
{
    public EventAsyncDisplayEntityData(DisplayEntity displayEntity, PacketEvent.Specialized<PacketS2cEntityData> packetEvent)
    {
        super(displayEntity, packetEvent);
    }
    
    @Override
    public void runLater(Runnable runnable)
    {
        this.futureTasks.schedule(runnable);
    }
    
    @Override
    public Object getData(EntityDataKey key)
    {
        Object result = EventAsyncDisplayEntityData.this.getPacket().getData(key);
        if(result!=null)
            return result;
        return EventAsyncDisplayEntityData.this.getDisplayEntity().getData(key);
    }
    @Override
    public Object putData(EntityDataKey key, Object value)
    {
        this.getPacketEvent().ensureCopied();
        return EventAsyncDisplayEntityData.this.getPacket().putData(key, value);
    }
    @Override
    public Object removeData(EntityDataKey key)
    {
        this.getPacketEvent().ensureCopied();
        return EventAsyncDisplayEntityData.this.getPacket().removeData(key);
    }
    @Override
    public void forEachData(BiConsumer<EntityDataKey, Object> action)
    {
        Set<EntityDataKey> set = new HashSet<>();
        EventAsyncDisplayEntityData.this.getPacket().forEachData((k, v)->
        {
            set.add(k);
            action.accept(k, v);
        });
        EventAsyncDisplayEntityData.this.getDisplayEntity().forEachData((k, v)->
        {
            if(set.add(k))
                action.accept(k, v);
        });
    }
    
    @Override
    public void call()
    {
        super.call();
    }
}
