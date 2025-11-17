package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.incomprehensible.profiler.Profiler;
import mz.mzlib.minecraft.incomprehensible.resource.ResourceManager;
import mz.mzlib.minecraft.incomprehensible.resource.SinglePreparationResourceReloader;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapMethodFromBridge;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

@WrapSameClass(RecipeManager.class)
public interface NothingRecipeManager extends Nothing, RecipeManager, SinglePreparationResourceReloader<Object>
{
    @WrapMethodFromBridge(name = "prepare", params = {ResourceManager.class, Profiler.class})
    Object prepare$impl(ResourceManager manager, Profiler profiler);

    static void locate$prepare$begin(NothingInjectLocating locating)
    {
        locating.nextInvokeWrapped(PreparedRecipes.class, "static$of0", Iterable.class);
        locating.offset(1);
        if(locating.locations.size() != 1)
            throw new IllegalStateException();
    }
    @NothingInject(
        wrapperMethodName = "prepare$impl", wrapperMethodParams = { ResourceManager.class, Profiler.class },
        locateMethod = "locate$prepare$begin", type = NothingInjectType.INSERT_BEFORE
    )
    default Wrapper_void prepare$begin(@StackTop PreparedRecipes recipes)
    {
        RegistrarRecipe.instance.raw = recipes.castTo(PreparedRecipes.FACTORY);
        recipes.setWrappedFrom(RegistrarRecipe.instance.apply());
        return Nothing.notReturn();
    }
}
