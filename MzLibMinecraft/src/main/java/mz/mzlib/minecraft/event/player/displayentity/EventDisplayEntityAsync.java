package mz.mzlib.minecraft.event.player.displayentity;

import mz.mzlib.minecraft.entity.display.DisplayEntity;
import mz.mzlib.minecraft.event.player.EventPlayerByPacket;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.module.MzModule;

public class EventDisplayEntityAsync extends EventPlayerByPacket
{
    public DisplayEntity displayEntity;
    public EventDisplayEntityAsync(DisplayEntity displayEntity, PacketEvent packetEvent)
    {
        super(packetEvent);
        this.displayEntity = displayEntity;
    }
    
    public DisplayEntity getDisplayEntity()
    {
        return this.displayEntity;
    }
    
    @Override
    public void call()
    {
    }
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventDisplayEntityAsync.class);
            this.register(EventDisplayEntitySpawnAsync.class);
            this.register(EventDisplayEntityDestroyAsync.class);
            this.register(EventDisplayEntityDataAsync.class);
        }
    }
}
