package mz.mzlib.minecraft.event.player.displayentity;

import mz.mzlib.minecraft.entity.data.EntityDataType;
import mz.mzlib.minecraft.entity.display.DisplayEntity;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntityData;
import mz.mzlib.util.wrapper.WrapperObject;

import javax.annotation.Nullable;
import java.util.function.Function;

public class EventDisplayEntityDataAsync extends EventDisplayEntityAsync
{
    public PacketS2cEntityData packet;
    public EventDisplayEntityDataAsync(DisplayEntity displayEntity, PacketEvent packetEvent, PacketS2cEntityData packet)
    {
        super(displayEntity, packetEvent);
        this.packet = packet;
    }
    
    @Override
    public void runLater(Runnable runnable)
    {
        this.futureTasks.schedule(runnable);
    }
    
    public @Nullable Object getNewData0(EntityDataType type)
    {
        return this.packet.getData0(type);
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
    
    public void putNewData0(EntityDataType type, Object value)
    {
        this.packet.putData0(type, value);
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
