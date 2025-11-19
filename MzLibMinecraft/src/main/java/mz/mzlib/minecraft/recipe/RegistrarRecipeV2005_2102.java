package mz.mzlib.minecraft.recipe;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.profiler.Profiler;
import mz.mzlib.minecraft.incomprehensible.resource.ResourceManager;
import mz.mzlib.minecraft.incomprehensible.resource.SinglePreparationResourceReloader;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapMethodFromBridge;
import mz.mzlib.util.wrapper.WrapSameClass;

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

    @VersionRange(begin = 2005, end = 2102)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManager extends Nothing, RecipeManager, SinglePreparationResourceReloader<Object>
    {
        @WrapMethodFromBridge(name = "apply", params = { Object.class, ResourceManager.class, Profiler.class })
        void apply$impl(Map<?, ?> prepared, ResourceManager manager, Profiler profiler);

        @NothingInject(
            wrapperMethodName = "apply$impl",
            wrapperMethodParams = { Map.class, ResourceManager.class, Profiler.class },
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void apply$impl$inject()
        {
            RegistrarRecipeV2005_2102.instance.rawIdRecipes0 = this.getIdRecipes0V1800_2102();
            RegistrarRecipeV2005_2102.instance.rawTypeRecipes0 = this.getTypeRecipes0V2005_2102();
            this.setIdRecipes0V1800_2102(RegistrarRecipeV2005_2102.instance.applyIdRecipes0());
            this.setTypeRecipes0V2005_2102(RegistrarRecipeV2005_2102.instance.applyTypeRecipes0());
        }
    }
}
