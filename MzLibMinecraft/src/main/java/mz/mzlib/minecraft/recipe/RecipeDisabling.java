package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.util.Option;

public class RecipeDisabling
{
    RecipeType type;
    Identifier id;

    public RecipeDisabling(RecipeType type, Identifier id)
    {
        this.type = type;
        this.id = id;
    }
    public RecipeDisabling(RecipeType type, Identifier id, Recipe recipe)
    {
        this(type, id);
        this.setRecipe(recipe);
    }

    public RecipeType getType()
    {
        return this.type;
    }
    public Identifier getId()
    {
        return this.id;
    }

    Option<Recipe> recipe = Option.none();

    public Option<Recipe> getRecipe()
    {
        return this.recipe;
    }
    public void setRecipe(Recipe recipe)
    {
        this.recipe = Option.some(recipe);
    }
}
