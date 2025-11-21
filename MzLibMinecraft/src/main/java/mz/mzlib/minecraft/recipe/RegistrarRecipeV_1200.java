package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionRange;

import java.util.List;

@VersionRange(end = 1200)
public class RegistrarRecipeV_1200 extends RegistrarRecipeV_1300
{
    public static RegistrarRecipeV_1200 instance;

    @Override
    public void onReloadEnd()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush()
    {
        super.flush();
        RecipeManager.getInstanceV_1200().setRecipes0V_1200(RecipeManager.ofV_1200().getRecipes0V_1200());
    }
    public void onReloadEnd(RecipeManager recipeManager)
    {
        List<RecipeVanilla> recipes = recipeManager.getRecipesV_1200();
        for(RecipeRegistration recipe : this.recipes)
        {
            RecipeType type = recipe.getRecipe().getType();
            if(type.equals(RecipeType.CRAFTING))
                recipes.add((RecipeVanilla) recipe.getRecipe());
            else if(!type.equals(RecipeType.SMELTING)) // process on super
                throw new UnsupportedOperationException("Unknown recipe type: " + type);
        }
    }
}
