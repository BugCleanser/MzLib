package mz.mzlib.minecraft.event.server;

import mz.mzlib.minecraft.MinecraftServer;

public class EventServerStart extends EventServer
{
    public EventServerStart(MinecraftServer server)
    {
        super(server);
    }
    public boolean successful = true;
    
    @Override
    public void call()
    {
        super.call();
    }
}
