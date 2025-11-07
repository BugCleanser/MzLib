package mz.mzlib.minecraft.permission.dynamic;

import mz.mzlib.event.Cancellable;
import mz.mzlib.event.Event;
import mz.mzlib.minecraft.entity.player.EntityPlayer;

public abstract class EventCheckPermission extends Event implements Cancellable
{
    public EntityPlayer player;
    public EventCheckPermission(EntityPlayer player)
    {
        this.player = player;
    }

    public EntityPlayer getPlayer()
    {
        return this.player;
    }
    public void deny()
    {
        this.setCancelled(true);
    }

    public boolean check()
    {
        this.call();
        return !this.isCancelled();
    }

    @Override
    public void call()
    {
    }
}
