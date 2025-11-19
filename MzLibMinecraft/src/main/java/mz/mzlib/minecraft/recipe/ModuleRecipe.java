package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.profiler.Profiler;
import mz.mzlib.minecraft.incomprehensible.resource.ResourceManager;
import mz.mzlib.minecraft.incomprehensible.resource.ResourceReloaderV1300;
import mz.mzlib.minecraft.incomprehensible.resource.SinglePreparationResourceReloaderV1400;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapMethodFromBridge;
import mz.mzlib.util.wrapper.WrapSameClass;

public class ModuleRecipe extends MzModule
{
    public static ModuleRecipe instance = new ModuleRecipe();

    @Override
    public void onLoad()
    {
        final int version = MinecraftPlatform.instance.getVersion();
        if(version < 1300)
        {
            // TODO
        }
        else if(version < 1400)
        {
            // TODO
        }
        else if(version < 1800)
            this.register(new RegistrarRecipeV1400_2005());
        else if(version < 2005)
            this.register(new RegistrarRecipeV1800_2005());
        else if(version < 2102)
            this.register(new RegistrarRecipeV2005_2102());
        else
            this.register(new RegistrarRecipeV2102());

        if(version < 1300)
            RuntimeUtil.nop();
        else if(version < 1400)
        {
            // TODO
        }
        else
            this.register(NothingRecipeManagerV1400.class);
    }

    @VersionRange(begin = 1300, end = 1400)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManagerV1300_1400 extends Nothing, RecipeManager, ResourceReloaderV1300
    {
        @NothingInject(
            wrapperMethodName = "reloadV_1400",
            wrapperMethodParams = { ResourceManager.class },
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void reload$end()
        {
            RegistrarRecipeV1300.instance.onReloadEnd();
        }
    }
    @VersionRange(begin = 1400)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManagerV1400 extends Nothing, RecipeManager, SinglePreparationResourceReloaderV1400<Object>
    {
        @WrapMethodFromBridge(name = "applyV1400", params = { Object.class, ResourceManager.class, Profiler.class })
        void apply$impl(Object prepared, ResourceManager manager, Profiler profiler);

        @NothingInject(
            wrapperMethodName = "apply$impl",
            wrapperMethodParams = { Object.class, ResourceManager.class, Profiler.class },
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void apply$impl$end()
        {
            RegistrarRecipeV1300.instance.onReloadEnd();
        }
    }
}
