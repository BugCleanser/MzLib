package mz.mzlib.mc.bukkit;

import mz.mzlib.mc.MzLibMinecraft;
import mz.mzlib.mc.bukkit.item.ItemFactoryBukkit;
import mz.mzlib.mc.bukkit.item.ItemStackFactoryBukkit;
import mz.mzlib.module.MzModule;

public class MzLibBukkit extends MzModule
{
    public static MzLibBukkit instance = new MzLibBukkit();

    @Override
    public void onLoad()
    {
        if (MinecraftPlatformBukkit.instance.getVersion() < 1400 && MinecraftPlatformBukkit.instance.getVersion() != 1202 && MinecraftPlatformBukkit.instance.getVersion() != 1302)
            throw new IllegalStateException("MzLib is unsupported on your MC version: " + MinecraftPlatformBukkit.instance.getVersionString());

        this.register(MinecraftPlatformBukkit.instance);

        this.register(CraftServer.create(Bukkit.getServer()).getServer());
        this.register(MinecraftMainThreadRunnerBukkit.instance);
        this.register(ItemFactoryBukkit.instance);
        this.register(ItemStackFactoryBukkit.instance);

        this.register(MzLibMinecraft.instance);
    }
}
