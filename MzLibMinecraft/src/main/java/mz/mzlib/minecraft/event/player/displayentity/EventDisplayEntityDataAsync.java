package mz.mzlib.minecraft.event.player.displayentity;

import mz.mzlib.minecraft.entity.data.EntityDataType;
import mz.mzlib.minecraft.entity.display.DisplayEntity;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntityData;
import mz.mzlib.util.wrapper.WrapperObject;

import javax.annotation.Nullable;
import java.util.function.Function;

public class EventDisplayEntityDataAsync extends EventDisplayEntityAsync<PacketS2cEntityData>
{
    public EventDisplayEntityDataAsync(DisplayEntity displayEntity, PacketEvent.Specialized<PacketS2cEntityData> packetEvent)
    {
        super(displayEntity, packetEvent);
    }
    
    @Override
    public void runLater(Runnable runnable)
    {
        this.futureTasks.schedule(runnable);
    }
    
    public @Nullable Object getNewData0(EntityDataType type)
    {
        return this.getPacket().getData0(type);
    }
    public <T extends WrapperObject> T getNewData(EntityDataType type, Function<Object, T> wrapperCreator)
    {
        return wrapperCreator.apply(this.getNewData0(type));
    }
    
    public @Nullable Object getData0(EntityDataType type)
    {
        Object result = getNewData0(type);
        if(result!=null)
            return result;
        return this.getDisplayEntity().getData0(type);
    }
    public <T extends WrapperObject> T getData(EntityDataType type, Function<Object, T> wrapperCreator)
    {
        return wrapperCreator.apply(this.getData0(type));
    }
    
    public void removeNewData(EntityDataType type)
    {
        this.packetEvent.ensureCopied();
        this.getPacket().removeData(type);
    }
    
    public void putNewData0(EntityDataType type, Object value)
    {
        this.packetEvent.ensureCopied();
        this.getPacket().putData0(type, value);
    }
    public void putNewData(EntityDataType type, WrapperObject value)
    {
        this.putNewData0(type, value.getWrapped());
    }
    
    @Override
    public void call()
    {
        super.call();
    }
}
