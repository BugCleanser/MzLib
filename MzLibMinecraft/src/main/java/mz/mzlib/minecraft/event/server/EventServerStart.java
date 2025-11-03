package mz.mzlib.minecraft.event.server;

import mz.mzlib.event.Cancellable;
import mz.mzlib.minecraft.MinecraftServer;

public class EventServerStart extends EventServer implements Cancellable
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
