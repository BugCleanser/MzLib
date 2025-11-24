package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.SimpleRegistry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@VersionRange(begin = 1200, end = 1300)
public class RegistrarRecipeV1200_1300 extends RegistrarRecipeV_1300
{
    public static RegistrarRecipeV1200_1300 instance;

    @Override
    protected void updateOriginal()
    {
        super.updateOriginal();
        Map<RecipeType, Map<Identifier, Recipe>> result = new HashMap<>(this.originalRecipes);
        HashMap<Identifier, Recipe> craftingRecipes = new HashMap<>();
        SimpleRegistry registry = RecipeManager.getRegistryV1200_1300();
        for(Identifier id : registry.getIdsV_1300())
        {
            craftingRecipes.put(id, registry.get(id).as(RecipeVanilla.FACTORY).autoCast());
        }
        result.put(RecipeType.CRAFTING, Collections.unmodifiableMap(craftingRecipes));
        this.originalRecipes = Collections.unmodifiableMap(result);
    }

    @Override
    public synchronized void flush()
    {
        super.flush();
        RecipeManager.setRegistryV1200_1300(SimpleRegistry.ofV_1600());
        boolean ignored = RecipeManager.setupV1200_1300(); // reload
    }

    @Override
    protected void onReloadEnd()
    {
        this.updateOriginal();
        for(Map.Entry<Identifier, Recipe> e : this.getRegisteredRecipes().get(RecipeType.CRAFTING).entrySet())
        {
            RecipeManager.registerV1200_1300(e.getKey(), (RecipeVanilla) e.getValue());
        }
    }
}
