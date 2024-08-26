package mz.mzlib.minecraft.event;

import mz.mzlib.minecraft.event.entity.ModuleEventEntity;
import mz.mzlib.minecraft.event.player.ModuleEventPlayer;
import mz.mzlib.module.MzModule;

public class MinecraftEventModule extends MzModule
{
    public static MinecraftEventModule instance = new MinecraftEventModule();

    @Override
    public void onLoad()
    {
        this.register(ModuleEventEntity.instance);
        this.register(ModuleEventPlayer.instance);
    }
}
