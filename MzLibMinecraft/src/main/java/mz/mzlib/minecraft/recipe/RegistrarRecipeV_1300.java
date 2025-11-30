package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.recipe.smelting.RecipeFurnace;
import mz.mzlib.minecraft.recipe.smelting.RecipeFurnaceV_1300;
import mz.mzlib.minecraft.recipe.smelting.SmeltingManagerV_1300;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@VersionRange(end = 1300)
public abstract class RegistrarRecipeV_1300 extends RegistrarRecipe
{
    public static RegistrarRecipeV_1300 instance;

    @Override
    protected void updateOriginal()
    {
        this.updateOriginal(SmeltingManagerV_1300.getInstance());
    }
    protected void updateOriginal(SmeltingManagerV_1300 smeltingManager)
    {
        HashMap<RecipeType, Map<Identifier, Recipe>> result =
            this.originalRecipes == null ? new HashMap<>() : new HashMap<>(this.originalRecipes);
        HashMap<Identifier, Recipe> smeltingRecipes = new HashMap<>();
        Map<ItemStack, Float> xps = smeltingManager.getExperiences();
        for(Map.Entry<ItemStack, ItemStack> e : smeltingManager.getResults().entrySet())
        {
            RecipeFurnaceV_1300 recipe = (RecipeFurnaceV_1300) RecipeFurnace.builder().ingredient(e.getKey())
                .result(e.getValue()).experience(xps.get(e.getValue())).build();
            smeltingRecipes.put(recipe.calcId(), recipe);
        }
        result.put(RecipeType.FURNACE, Collections.unmodifiableMap(smeltingRecipes));
        this.originalRecipes = Collections.unmodifiableMap(result);
    }
    @Override
    public synchronized void flush()
    {
        super.flush();
        SmeltingManagerV_1300 next = SmeltingManagerV_1300.of(); // reload
        SmeltingManagerV_1300.getInstance().setExperiences0(next.getExperiences0());
        SmeltingManagerV_1300.getInstance().setResults0(next.getResults0());
    }
    protected void onReloadEnd(SmeltingManagerV_1300 smeltingManager)
    {
        updateOriginal(smeltingManager);
        for(Map.Entry<Identifier, Recipe> e : this.getRegisteredRecipes().get(RecipeType.FURNACE).entrySet())
        {
            RecipeFurnaceV_1300 recipe = (RecipeFurnaceV_1300) e.getValue();
            smeltingManager.register(recipe.getIngredient(), recipe.getResult(), recipe.getExperience());
        }
    }
}
