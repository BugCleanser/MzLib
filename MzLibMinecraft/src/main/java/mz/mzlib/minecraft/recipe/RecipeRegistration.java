package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;

public class RecipeRegistration
{
    Identifier id;
    Recipe recipe;

    public RecipeRegistration(Identifier id, Recipe recipe)
    {
        this.id = id;
        this.recipe = recipe;
    }

    public static RecipeRegistration of(Identifier id, Recipe recipe)
    {
        return new RecipeRegistration(id, recipe);
    }

    public Identifier getId()
    {
        return this.id;
    }

    public Recipe getRecipe()
    {
        return this.recipe;
    }

    RecipeVanilla getRecipeV1300()
    {
        return (RecipeVanilla) this.getRecipe();
    }
}
