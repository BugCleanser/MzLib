package mz.mzlib.minecraft.event.player;

import mz.mzlib.module.MzModule;

public class PlayerEventModule extends MzModule
{
    public static PlayerEventModule instance=new PlayerEventModule();

    @Override
    public void onLoad()
    {
        this.register(EventChat.Module.instance);
        this.register(EventPlayerMoveAsync.Module.instance);
    }
}
