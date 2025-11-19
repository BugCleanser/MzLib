package mz.mzlib.minecraft.recipe;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@VersionRange(end = 2005)
public class RegistrarRecipeV_2005 extends RegistrarRecipe
{
    public static RegistrarRecipeV_2005 instance;

    Map<Object, Map<Object, Object>> rawRecipes0;

    Function<RecipeRegistration, WrapperObject> toData = MinecraftPlatform.instance.getVersion() < 2002 ?
        RecipeRegistration::getRecipe : RecipeEntryV2002::of;

    public Map<Object, Map<Object, Object>> applyRecipes0()
    {
        Map<Object, Map<Object, Object>> result = new HashMap<>(this.rawRecipes0);
        for(Map.Entry<Object, Map<Object, Object>> entry : result.entrySet())
        {
            entry.setValue(new Object2ObjectLinkedOpenHashMap<>(entry.getValue())); // adapt for Bukkit
        }
        for(RecipeRegistration recipe : this.recipes)
        {
            result.computeIfAbsent(recipe.getRecipe().getType().getWrapped(), k -> new HashMap<>())
                .put(recipe.getId().getWrapped(), toData.apply(recipe).getWrapped());
        }
        return Collections.unmodifiableMap(result);
    }

    @Override
    protected void setRaw()
    {
        this.rawRecipes0 = MinecraftServer.instance.getRecipeManager().getRecipes0V_2005();
    }

    @Override
    public void flush()
    {
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManager();
        if(this.rawRecipes0 == null)
            this.rawRecipes0 = recipeManager.getRecipes0V_2005();
        recipeManager.setRecipes0V_2005(this.applyRecipes0());
    }
}
