package mz.mzlib.minecraft.event.player.displayentity;

import mz.mzlib.minecraft.entity.display.DisplayEntity;
import mz.mzlib.minecraft.network.packet.PacketEvent;

public class EventDisplayEntitySpawnAsync extends EventDisplayEntityAsync
{
    public EventDisplayEntitySpawnAsync(DisplayEntity displayEntity, PacketEvent packetEvent)
    {
        super(displayEntity, packetEvent);
    }
    
    @Override
    public void call()
    {
        super.call();
    }
}
