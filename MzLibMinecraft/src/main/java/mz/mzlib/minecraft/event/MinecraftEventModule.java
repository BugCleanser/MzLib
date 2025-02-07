package mz.mzlib.minecraft.event;

import mz.mzlib.minecraft.event.entity.EventEntity;
import mz.mzlib.minecraft.event.player.async.displayentity.EventAsyncDisplayEntity;
import mz.mzlib.minecraft.event.player.EventPlayer;
import mz.mzlib.minecraft.event.window.EventWindow;
import mz.mzlib.module.MzModule;

public class MinecraftEventModule extends MzModule
{
    public static MinecraftEventModule instance = new MinecraftEventModule();

    @Override
    public void onLoad()
    {
        this.register(EventEntity.Module.instance);
        this.register(EventAsyncDisplayEntity.Module.instance);
        this.register(EventPlayer.Module.instance);
        this.register(EventWindow.Module.instance);
    }
}
