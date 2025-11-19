package mz.mzlib.minecraft.recipe;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@VersionRange(begin = 2005, end = 2102)
public class RegistrarRecipeV2005_2102 extends RegistrarRecipe
{
    public static RegistrarRecipeV2005_2102 instance;

    Map<Object, Object> rawIdRecipes0;
    Multimap<Object, Object> rawTypeRecipes0;

    public Map<Object, Object> applyIdRecipes0()
    {
        Map<Object, Object> result = new HashMap<>(this.rawIdRecipes0);
        for(RecipeRegistration recipe : this.recipes)
        {
            result.put(recipe.getId().getWrapped(), RecipeEntryV2002.of(recipe).getWrapped());
        }
        return Collections.unmodifiableMap(result);
    }
    public Multimap<Object, Object> applyTypeRecipes0()
    {
        ImmutableMultimap.Builder<Object, Object> builder = ImmutableMultimap.builder();
        builder.putAll(this.rawTypeRecipes0);
        for(RecipeRegistration recipe : this.recipes)
        {
            builder.put(recipe.getRecipe().getType().getWrapped(), RecipeEntryV2002.of(recipe).getWrapped());
        }
        return builder.build();
    }

    @Override
    protected void setRaw()
    {
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManager();
        this.rawIdRecipes0 = recipeManager.getIdRecipes0V1800_2102();
        this.rawTypeRecipes0 = recipeManager.getTypeRecipes0V2005_2102();
    }

    @Override
    public void flush()
    {
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManager();
        if(this.rawIdRecipes0 == null)
            this.rawIdRecipes0 = recipeManager.getIdRecipes0V1800_2102();
        if(this.rawTypeRecipes0 == null)
            this.rawTypeRecipes0 = recipeManager.getTypeRecipes0V2005_2102();
        recipeManager.setIdRecipes0V1800_2102(this.applyIdRecipes0());
        recipeManager.setTypeRecipes0V2005_2102(this.applyTypeRecipes0());
    }
}
