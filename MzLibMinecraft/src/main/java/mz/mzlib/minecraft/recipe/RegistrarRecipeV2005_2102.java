package mz.mzlib.minecraft.recipe;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@VersionRange(begin = 2005, end = 2102)
public class RegistrarRecipeV2005_2102 extends RegistrarRecipe
{
    public static RegistrarRecipeV2005_2102 instance;

    Map<Object, Object> rawIdRecipes0;
    Multimap<Object, Object> rawTypeRecipes0;

    @Override
    protected void updateOriginal()
    {
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManagerV1300();
        this.rawIdRecipes0 = recipeManager.getIdRecipes0V1800_2102();
        this.rawTypeRecipes0 = recipeManager.getTypeRecipes0V2005_2102();
        this.originalRecipes = this.rawTypeRecipes0.asMap().entrySet().stream()
            .collect(Collectors.toMap(
                entry -> RecipeTypeV1400.FACTORY.create(entry.getKey()),
                entry -> entry.getValue().stream().map(RecipeEntryV2002.FACTORY::create)
                    .collect(Collectors.toMap(RecipeEntryV2002::getId, RecipeEntryV2002::getValue))
            ));
    }

    @Override
    public synchronized void flush()
    {
        super.flush();
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManagerV1300();
        Map<Object, Object> result = new HashMap<>(this.rawIdRecipes0);
        for(RecipeRegistration recipe : this.recipeRegistrations)
        {
            result.put(recipe.getId().getWrapped(), RecipeEntryV2002.of(recipe).getWrapped());
        }
        recipeManager.setIdRecipes0V1800_2102(Collections.unmodifiableMap(result));

        ImmutableMultimap.Builder<Object, Object> builder = ImmutableMultimap.builder();
        builder.putAll(this.rawTypeRecipes0);
        for(RecipeRegistration recipe : this.recipeRegistrations)
        {
            builder.put(recipe.getRecipeV1300().getTypeV1400().getWrapped(), RecipeEntryV2002.of(recipe).getWrapped());
        }
        recipeManager.setTypeRecipes0V2005_2102(builder.build());
    }
}
