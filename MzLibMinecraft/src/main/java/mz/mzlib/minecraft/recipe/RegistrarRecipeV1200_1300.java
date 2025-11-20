package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.SimpleRegistry;

@VersionRange(begin = 1200, end = 1300)
public class RegistrarRecipeV1200_1300 extends RegistrarRecipe
{
    public static RegistrarRecipeV1200_1300 instance;

    @Override
    protected void setRaw()
    {
    }

    @Override
    public void flush()
    {
        RecipeManager.setRegistryV1200_1300(SimpleRegistry.ofV_1600());
        boolean ignored = RecipeManager.setupV1200_1300();
    }

    @Override
    public void onReloadEnd()
    {
        for(RecipeRegistration recipe : this.recipes)
        {
            RecipeManager.registerV1200_1300(recipe.getId(), recipe.getRecipe());
        }
    }
}
