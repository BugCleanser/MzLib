package mz.mzlib.mc.packet;

import mz.mzlib.mc.entity.EntityPlayer;

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
