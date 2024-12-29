package mz.mzlib.minecraft.permission.dynamic;

import mz.mzlib.event.Event;
import mz.mzlib.minecraft.command.CommandSender;

public abstract class EventCheckPermission extends Event
{
    public CommandSender gameObject;
    public EventCheckPermission(CommandSender gameObject)
    {
        this.gameObject = gameObject;
    }
    
    public CommandSender getGameObject()
    {
        return this.gameObject;
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
