package mz.mzlib.minecraft.event.player.displayentity;

import mz.mzlib.minecraft.entity.display.DisplayEntity;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntitySpawn;

public class EventDisplayEntitySpawnAsync extends EventDisplayEntityAsync<PacketS2cEntitySpawn>
{
    public EventDisplayEntitySpawnAsync(DisplayEntity displayEntity, PacketEvent.Specialized<PacketS2cEntitySpawn> packetEvent)
    {
        super(displayEntity, packetEvent);
    }
    
    @Override
    public void call()
    {
        super.call();
    }
}
