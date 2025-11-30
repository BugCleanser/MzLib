package mz.mzlib.minecraft.recipe;

import com.google.common.collect.ImmutableMultimap;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.CollectionUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@VersionRange(begin = 2005, end = 2102)
public class RegistrarRecipeV2005_2102 extends RegistrarRecipe
{
    public static RegistrarRecipeV2005_2102 instance;

    @Override
    protected void updateOriginal()
    {
        RecipeManager recipeManager = MinecraftServer.instance.getRecipeManagerV1300();
        this.originalRecipes = recipeManager.getTypeRecipes0V2005_2102().asMap().entrySet().stream()
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
        Map<Object, Object> result = new HashMap<>();
        for(Map.Entry<Identifier, Recipe> entry : CollectionUtil.asIterable(
            this.getEnabledRecipes().values().stream().map(Map::entrySet).flatMap(Set::stream).iterator()))
        {
            result.put(
                entry.getKey().getWrapped(),
                RecipeEntryV2002.of(RecipeRegistration.of(entry.getKey(), entry.getValue())).getWrapped()
            );
        }
        recipeManager.setIdRecipes0V1800_2102(Collections.unmodifiableMap(result));

        ImmutableMultimap.Builder<Object, Object> builder = ImmutableMultimap.builder();
        for(Map.Entry<RecipeType, Map<Identifier, Recipe>> entry : this.getEnabledRecipes().entrySet())
        {
            for(Map.Entry<Identifier, Recipe> e : entry.getValue().entrySet())
            {
                builder.put(
                    ((RecipeTypeV1400) entry.getKey()).getWrapped(),
                    RecipeEntryV2002.of(RecipeRegistration.of(e.getKey(), e.getValue())).getWrapped()
                );
            }
        }
        recipeManager.setTypeRecipes0V2005_2102(builder.build());
    }
}
