package mz.mzlib.minecraft.fabric;

import mz.mzlib.minecraft.vanilla.MzLibInitializer;
import mz.mzlib.minecraft.vanilla.PermissionHelpLuckPerms;
import mz.mzlib.module.MzModule;
import net.fabricmc.api.ModInitializer;

public class MzLibFabricEntrypoint extends MzModule implements ModInitializer
{
    public static MzLibFabricEntrypoint instance;
    
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
        this.register(MinecraftPlatformFabric.instance);
        this.register(PermissionHelpLuckPerms.instance);
        this.register(MzLibInitializer.instance);
    }
}
