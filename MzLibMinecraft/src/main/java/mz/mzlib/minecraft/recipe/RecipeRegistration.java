package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;

public class RecipeRegistration<T extends Recipe>
{
    Identifier id;
    T recipe;

    public RecipeRegistration(Identifier id, T recipe)
    {
        this.id = id;
        this.recipe = recipe;
    }

    public static <T extends Recipe> RecipeRegistration<T> of(Identifier id, T recipe)
    {
        return new RecipeRegistration<>(id, recipe);
    }

    public Identifier getId()
    {
        return this.id;
    }

    public T getRecipe()
    {
        return this.recipe;
    }

    RecipeMojang getRecipeV1300()
    {
        return (RecipeMojang) this.getRecipe();
    }
}
