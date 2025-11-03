package mz.mzlib.minecraft.event.player.async.displayentity;

import mz.mzlib.minecraft.entity.display.DisplayEntity;
import mz.mzlib.minecraft.event.player.async.EventAsyncByPacket;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cEntitySpawn;

public class EventAsyncDisplayEntitySpawn extends EventAsyncDisplayEntity<PacketS2cEntitySpawn> implements EventAsyncByPacket.Cancellable
{
    public EventAsyncDisplayEntitySpawn(DisplayEntity displayEntity, PacketEvent.Specialized<PacketS2cEntitySpawn> packetEvent)
    {
        super(displayEntity, packetEvent);
    }
    
    @Override
    public void call()
    {
        super.call();
    }
}
