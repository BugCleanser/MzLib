package mz.mzlib.minecraft.event.server;

import mz.mzlib.minecraft.MinecraftServer;

public class EventServerStop extends EventServer
{
    public EventServerStop(MinecraftServer server)
    {
        super(server);
    }
    
    @Override
    public void setCancelled(boolean cancelled)
    {
        if(cancelled)
            throw new UnsupportedOperationException();
    }
    
    @Override
    public void call()
    {
        super.call();
    }
}
