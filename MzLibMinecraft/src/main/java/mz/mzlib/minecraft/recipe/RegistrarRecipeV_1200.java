package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionRange;

import java.util.List;

@VersionRange(end = 1200)
public class RegistrarRecipeV_1200 extends RegistrarRecipe
{
    public static RegistrarRecipeV_1200 instance;

    @Override
    protected void setRaw()
    {
    }

    @Override
    public void onReloadEnd()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush()
    {
        RecipeManager.getInstanceV_1200().setRecipes0V_1200(RecipeManager.ofV_1200().getRecipes0V_1200());
    }
    public void onReloadEnd(RecipeManager recipeManager)
    {
        List<Recipe> recipes = recipeManager.getRecipesV_1200();
        for(RecipeRegistration recipe : this.recipes)
        {
            recipes.add(recipe.getRecipe());
        }
    }
}
