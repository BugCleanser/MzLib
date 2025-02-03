package mz.mzlib.minecraft.fabric;

import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.permission.PermissionHelp;
import mz.mzlib.minecraft.vanilla.MzLibInitializer;
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
        this.register(MinecraftPlatformFabric.instance);
        this.register(MzLibInitializer.instance);
        this.register(new PermissionHelp()
        {
            @Override
            public boolean check(CommandSource object, String permission)
            {
                return true;
            }
            @Override
            public boolean check(EntityPlayer player, String permission)
            {
                return true;
            }
            @Override
            public void register(MzModule module, Permission object)
            {
            
            }
            @Override
            public void unregister(MzModule module, Permission object)
            {
            
            }
        });
    }
}
