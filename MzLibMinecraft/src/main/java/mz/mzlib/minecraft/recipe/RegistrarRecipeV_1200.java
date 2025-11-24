package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionRange;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@VersionRange(end = 1200)
public class RegistrarRecipeV_1200 extends RegistrarRecipeV_1300
{
    public static RegistrarRecipeV_1200 instance;

    @Override
    protected void onReloadEnd()
    {
    }

    @Override
    protected void updateOriginal()
    {
        this.updateOriginal(RecipeManager.getInstanceV_1200());
    }
    protected void updateOriginal(RecipeManager recipeManager)
    {
        super.updateOriginal();
        Map<RecipeType, Map<Identifier, Recipe>> result = new HashMap<>(this.originalRecipes);
        HashMap<Identifier, Recipe> craftingRecipes = new HashMap<>();
        for(RecipeVanilla recipe : recipeManager.getRecipesV_1200())
        {
            recipe = recipe.autoCast();
            craftingRecipes.put(recipe.calcIdV_1200(), recipe);
        }
        result.put(RecipeType.CRAFTING, Collections.unmodifiableMap(craftingRecipes));
        this.originalRecipes = Collections.unmodifiableMap(result);
    }
    @Override
    public synchronized void flush()
    {
        super.flush();
        RecipeManager.getInstanceV_1200().setRecipes0V_1200(RecipeManager.ofV_1200().getRecipes0V_1200()); // reload
    }
    protected void onReloadEnd(RecipeManager recipeManager)
    {
        this.updateOriginal(recipeManager);
        List<RecipeVanilla> recipes = recipeManager.getRecipesV_1200();
        for(Map.Entry<Identifier, Recipe> e : this.getRegisteredRecipes()
            .getOrDefault(RecipeType.CRAFTING, Collections.emptyMap()).entrySet())
        {
            recipes.add((RecipeVanilla) e.getValue());
        }
    }
}
