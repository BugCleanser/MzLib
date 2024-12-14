package mz.mzlib.minecraft.network.packet;

import mz.mzlib.minecraft.entity.player.EntityPlayer;

import java.util.concurrent.CompletableFuture;

public class PacketEvent
{
    public EntityPlayer player;
    public boolean isCancelled=false;
    public CompletableFuture<Void> future=new CompletableFuture<>();
    public PacketEvent(EntityPlayer player)
    {
        this.player=player;
    }
    
    public EntityPlayer getPlayer()
    {
        return player;
    }
    
    public CompletableFuture<Void> synchronizer=null;
    public CompletableFuture<Void> sync()
    {
        if(this.synchronizer==null)
            this.synchronizer=new CompletableFuture<>();
        return this.synchronizer;
    }

    /**
     * Execute when the operation corresponding to the event ends or is canceled.
     */
    public void runLater(Runnable runnable)
    {
        this.future.whenComplete((r,e)->runnable.run());
    }

    public void setCancelled(boolean cancelled)
    {
        this.isCancelled =cancelled;
    }
    public boolean isCancelled()
    {
        return this.isCancelled;
    }
}
