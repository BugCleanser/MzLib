package mz.mzlib.minecraft.network.packet;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.util.TaskList;

public class PacketEvent
{
    public EntityPlayer player;
    public boolean isCancelled = false;
    public PacketEvent(EntityPlayer player)
    {
        this.player = player;
    }
    
    public EntityPlayer getPlayer()
    {
        return player;
    }
    
    public TaskList syncTasks = null;
    public void sync(Runnable task)
    {
        if(this.syncTasks==null)
            this.syncTasks = new TaskList();
        this.syncTasks.schedule(task);
    }
    
    public void setCancelled(boolean cancelled)
    {
        this.isCancelled = cancelled;
    }
    public boolean isCancelled()
    {
        return this.isCancelled;
    }
}
