package mz.mzlib.minecraft.vanilla;

import mz.mzlib.minecraft.event.server.EventServer;
import mz.mzlib.module.MzModule;

/**
 * Initializer in the mod environment.
 */
public class MzLibMinecraftInitializer extends MzModule
{
    public static MzLibMinecraftInitializer instance = new MzLibMinecraftInitializer();
    
    @Override
    public void onLoad()
    {
        this.register(EventServer.Module.instance);
        this.register(ServerModule.Module.instance);
        this.register(new ServerModule(MzLibVanilla.instance));
    }
}
