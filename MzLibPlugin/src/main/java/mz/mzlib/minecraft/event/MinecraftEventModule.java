package mz.mzlib.minecraft.event;

import mz.mzlib.minecraft.event.entity.EntityEventModule;
import mz.mzlib.minecraft.event.player.PlayerEventModule;
import mz.mzlib.module.MzModule;

public class MinecraftEventModule extends MzModule
{
    public static MinecraftEventModule instance = new MinecraftEventModule();

    @Override
    public void onLoad()
    {
        this.register(EntityEventModule.instance);
        this.register(PlayerEventModule.instance);
    }
}
