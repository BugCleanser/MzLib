package mz.mzlib.minecraft.fabric;

import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.vanilla.RegistrarCommandVanillaV1300;
import mz.mzlib.module.MzModule;

public class MzLibFabric extends MzModule
{
    public static MzLibFabric instance = new MzLibFabric();
    
    @Override
    public void onLoad()
    {
        this.register(RegistrarCommandVanillaV1300.instance);
        this.register(MzLibMinecraft.instance);
    }
}
