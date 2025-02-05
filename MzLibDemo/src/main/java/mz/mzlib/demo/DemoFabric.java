package mz.mzlib.demo;

import mz.mzlib.minecraft.vanilla.ServerModule;
import mz.mzlib.module.MzModule;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class DemoFabric extends MzModule implements ModInitializer
{
    public static DemoFabric instance;
    {
        instance=this;
    }
    
    @Override
    public void onInitialize()
    {
        Demo.instance.jar = FabricLoader.getInstance().getModContainer("mzlib_demo").orElseThrow(AssertionError::new).getOrigin().getPaths().get(0).toFile();
        Demo.instance.dataFolder = new File(Demo.instance.jar.getParentFile(), "MzLibDemo");
        this.load();
    }
    
    @Override
    public void onLoad()
    {
        this.register(new ServerModule(Demo.instance));
    }
}
