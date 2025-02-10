package mz.mzlib.minecraft.fabric;

import mz.mzlib.MzLib;
import mz.mzlib.minecraft.vanilla.MzLibMinecraftInitializer;
import mz.mzlib.module.MzModule;
import net.fabricmc.api.ModInitializer;

public class MzLibFabricInitializer extends MzModule implements ModInitializer
{
    public static MzLibFabricInitializer instance;
    
    {
        instance = this;
    }
    
    public String MOD_ID = "mzlib";
    
    @Override
    public void onInitialize()
    {
        this.load();
    }
    
    @Override
    public void onLoad()
    {
        this.register(MzLib.instance);
        this.register(MinecraftPlatformFabric.instance);
        this.register(MzLibMinecraftInitializer.instance);
    }
}
