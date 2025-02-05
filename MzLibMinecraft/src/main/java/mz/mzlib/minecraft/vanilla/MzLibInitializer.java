package mz.mzlib.minecraft.vanilla;

import mz.mzlib.MzLib;
import mz.mzlib.minecraft.event.server.EventServer;
import mz.mzlib.module.MzModule;

/**
 * Initializer in the mod environment.
 */
public class MzLibInitializer extends MzModule
{
    public static MzLibInitializer instance = new MzLibInitializer();
    
    @Override
    public void onLoad()
    {
        this.register(MzLib.instance);
        this.register(EventServer.Module.instance);
        this.register(ServerModule.Module.instance);
        this.register(new ServerModule(MzLibVanilla.instance));
    }
}
