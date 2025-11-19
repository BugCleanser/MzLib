package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.module.MzModule;

public class ModuleRecipe extends MzModule
{
    public static ModuleRecipe instance = new ModuleRecipe();

    @Override
    public void onLoad()
    {
        if(MinecraftPlatform.instance.getVersion() < 1800)
        {
            this.register(new RegistrarRecipeV_2005());
            this.register(RegistrarRecipeV_2005.NothingRecipeManager.class);
        }
        else if(MinecraftPlatform.instance.getVersion() < 2005)
        {
            this.register(new RegistrarRecipeV1800_2005());
            this.register(RegistrarRecipeV_2005.NothingRecipeManager.class);
            this.register(RegistrarRecipeV1800_2005.NothingRecipeManager.class);
        }
        else if(MinecraftPlatform.instance.getVersion() < 2102)
        {
            this.register(new RegistrarRecipeV2005_2102());
            this.register(RegistrarRecipeV2005_2102.NothingRecipeManager.class);
        }
        else
        {
            this.register(new RegistrarRecipeV2102());
            this.register(RegistrarRecipeV2102.NothingRecipeManager.class);
        }
    }
}
