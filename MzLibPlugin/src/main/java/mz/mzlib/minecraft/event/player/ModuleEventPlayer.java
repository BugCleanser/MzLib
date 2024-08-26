package mz.mzlib.minecraft.event.player;

import mz.mzlib.module.MzModule;

public class ModuleEventPlayer extends MzModule
{
    public static ModuleEventPlayer instance=new ModuleEventPlayer();

    @Override
    public void onLoad()
    {
        this.register(EventChat.Module.instance);
        this.register(EventPlayerMoveAsync.Module.instance);
    }
}
