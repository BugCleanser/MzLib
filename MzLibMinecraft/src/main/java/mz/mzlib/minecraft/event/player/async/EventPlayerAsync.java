package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.minecraft.event.player.EventPlayer;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.module.MzModule;

import java.util.concurrent.CompletableFuture;

public class EventPlayerAsync extends EventPlayer
{
    PacketEvent packetEvent;
    public EventPlayerAsync(PacketEvent packetEvent)
    {
        super(packetEvent.getPlayer());
        this.packetEvent=packetEvent;
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
    
    public CompletableFuture<Void> sync()
    {
        return this.packetEvent.sync();
    }
    
    @Override
    public void call()
    {
        super.call();
    }
    
    public static class Module extends MzModule
    {
        public static Module instance=new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventPlayerAsync.class);
            
            this.register(EventPlayerChatAsync.Module.instance);
            this.register(EventPlayerMoveAsync.Module.instance);
            
            this.register(EventPlayerCloseWindowAsync.Module.instance);
            this.register(EventPlayerWindowAnvilSetNameAsync.Module.instance);
            
            this.register(EventPlayerDisplayItemAsync.class);
            this.register(EventPlayerDisplayItemInWindowAsync.Module.instance);
        }
    }
}
