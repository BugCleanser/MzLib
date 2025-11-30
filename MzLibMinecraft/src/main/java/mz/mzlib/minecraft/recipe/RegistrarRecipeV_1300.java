package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.recipe.smelting.RecipeFurnace;
import mz.mzlib.minecraft.recipe.smelting.RecipeFurnaceV_1300;
import mz.mzlib.minecraft.recipe.smelting.SmeltingManagerV_1300;
import mz.mzlib.util.RuntimeUtil;

import java.util.Collection;
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
        updateOriginal(SmeltingManagerV_1300.of()); // dirty
        super.flush();
        Map<Object, Object> results0 = new HashMap<>();
        Map<Object, Float> experiences0 = new HashMap<>();
        for(RecipeFurnaceV_1300 recipe : RuntimeUtil.<Collection<RecipeFurnaceV_1300>>cast(
            this.getEnabledRecipes().getOrDefault(RecipeType.FURNACE, Collections.emptyMap()).values()))
        {
            results0.put(recipe.getIngredient().getWrapped(), recipe.getResult().getWrapped());
            experiences0.put(recipe.getResult().getWrapped(), recipe.getExperience());
        }
        SmeltingManagerV_1300.getInstance().setExperiences0(experiences0);
        SmeltingManagerV_1300.getInstance().setResults0(results0);
    }
}
