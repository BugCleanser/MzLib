package mz.mzlib.minecraft.vanilla;

import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;

public class MzLibVanilla extends MzModule
{
    public static MzLibVanilla instance = new MzLibVanilla();
    
    @Override
    public void onLoad()
    {
        if(RuntimeUtil.runAndCatch(()->Class.forName("net.luckperms.api.LuckPerms"))==null)
            this.register(new PermissionHelpLuckPerms());
        else
            this.register(new PermissionRegistry());
        this.register(RegistrarCommandVanillaV1300.instance);
        
        this.register(MzLibMinecraft.instance);
    }
}
