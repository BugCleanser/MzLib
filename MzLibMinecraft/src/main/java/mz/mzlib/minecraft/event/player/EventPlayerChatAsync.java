package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sChatMessage;
import mz.mzlib.module.MzModule;

import java.util.concurrent.CompletableFuture;

public class EventPlayerChatAsync extends EventPlayer
{
    public PacketEvent packetEvent;
    public PacketC2sChatMessage packet;
    public EventPlayerChatAsync(EntityPlayer player, PacketEvent packetEvent, PacketC2sChatMessage packet)
    {
        super(player);
        this.packetEvent=packetEvent;
        this.packet=packet;
    }
    
    @Override
    public boolean isCancelled()
    {
        return this.packetEvent.isCancelled();
    }
    @Override
    public void setCancelled(boolean cancelled)
    {
        this.packetEvent.setCancelled(cancelled);
    }

    public String getMessage()
    {
        return this.packet.getMessage();
    }
    public void setMessage(String value)
    {
        this.packet.setMessage(value);
    }

    @Override
    public void call()
    {
    }
    
    public CompletableFuture<Void> sync()
    {
        return this.packetEvent.sync();
    }

    public static class Module extends MzModule
    {
        public static Module instance=new Module();

        @Override
        public void onLoad()
        {
            this.register(EventPlayerChatAsync.class);
            this.register(new PacketListener<>(PacketC2sChatMessage.class, (e, p)->
            {
                EventPlayerChatAsync event=new EventPlayerChatAsync(e.getPlayer(), e, p);
                event.setCancelled(e.isCancelled());
                event.call();
                e.runLater(event::complete);
            }));
        }
    }
}
