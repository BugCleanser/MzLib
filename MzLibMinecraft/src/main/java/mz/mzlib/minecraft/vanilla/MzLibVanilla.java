package mz.mzlib.minecraft.vanilla;

import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.module.MzModule;

public class MzLibVanilla extends MzModule
{
    public static MzLibVanilla instance = new MzLibVanilla();
    
    @Override
    public void onLoad()
    {
        this.register(RegistrarCommandVanillaV1300.instance);
        
        this.register(MzLibMinecraft.instance);
    }
}
