package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.SimpleRegistry;

@VersionRange(begin = 1200, end = 1300)
public class RegistrarRecipeV1200_1300 extends RegistrarRecipeV_1300
{
    public static RegistrarRecipeV1200_1300 instance;

    @Override
    public void flush()
    {
        super.flush();
        RecipeManager.setRegistryV1200_1300(SimpleRegistry.ofV_1600());
        boolean ignored = RecipeManager.setupV1200_1300();
    }

    @Override
    public void onReloadEnd()
    {
        for(RecipeRegistration recipe : this.recipes)
        {
            RecipeType type = recipe.getRecipe().getType();
            if(type.equals(RecipeType.CRAFTING))
                RecipeManager.registerV1200_1300(recipe.getId(), (RecipeVanilla) recipe.getRecipe());
            else if(!type.equals(RecipeType.SMELTING)) // process on super
                throw new UnsupportedOperationException("Unknown recipe type: " + type);
        }
    }
}
