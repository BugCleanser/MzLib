package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.event.recipe.EventRecipeLoad;
import mz.mzlib.minecraft.incomprehensible.profiler.Profiler;
import mz.mzlib.minecraft.incomprehensible.resource.ResourceManagerV1300;
import mz.mzlib.minecraft.incomprehensible.resource.ResourceReloaderV1300;
import mz.mzlib.minecraft.incomprehensible.resource.SinglePreparationResourceReloaderV1400;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.recipe.smelting.ModuleFurnaceUpdateV_1300;
import mz.mzlib.minecraft.recipe.smelting.RecipeFurnaceV_1300;
import mz.mzlib.minecraft.recipe.smelting.SmeltingManagerV_1300;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.WrapMethodFromBridge;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

import java.util.List;

public class ModuleRecipe extends MzModule
{
    public static ModuleRecipe instance = new ModuleRecipe();

    @Override
    public void onLoad()
    {
        final int version = MinecraftPlatform.instance.getVersion();
        // registrar
        if(version < 1200)
            this.register(new RegistrarRecipeV_1200());
        else if(version < 1300)
            this.register(new RegistrarRecipeV1200_1300());
        else if(version < 1400)
            this.register(new RegistrarRecipeV1300_1400());
        else if(version < 1800)
            this.register(new RegistrarRecipeV1400_2005());
        else if(version < 2005)
            this.register(new RegistrarRecipeV1800_2005());
        else if(version < 2102)
            this.register(new RegistrarRecipeV2005_2102());
        else
            this.register(new RegistrarRecipeV2102());

        // nothing
        if(version < 1300)
        {
            this.register(NothingRecipeManagerV_1300.class);
            this.register(NothingSmeltingManagerV_1300.class);
        }
        else if(version < 1400)
            this.register(NothingRecipeManagerV1300_1400.class);
        else
            this.register(NothingRecipeManagerV1400.class);

        // module
        if(version < 1300)
            this.register(ModuleFurnaceUpdateV_1300.instance);
    }

    @VersionRange(end = 1300)
    @WrapSameClass(SmeltingManagerV_1300.class)
    public interface NothingSmeltingManagerV_1300 extends Nothing, SmeltingManagerV_1300
    {
        @NothingInject(
            wrapperMethodName = "register",
            wrapperMethodParams = { ItemStack.class, ItemStack.class, float.class },
            locateMethod = "",
            type = NothingInjectType.INSERT_BEFORE
        )
        default Wrapper_void register$before(
            @CustomVar("eventRecipeLoad") WrapperObject.Generic<EventRecipeLoad> event,
            @LocalVar(1) ItemStack ingredient,
            @LocalVar(2) ItemStack result,
            @LocalVar(3) float xp)
        {
            RecipeFurnaceV_1300 recipe = new RecipeFurnaceV_1300(ingredient, result, xp);
            event.setWrapped(new EventRecipeLoad(recipe.calcId(), recipe));
            event.getWrapped().call();
            if(event.getWrapped().isCancelled())
            {
                event.getWrapped().finish();
                return Wrapper_void.FACTORY.create(null);
            }
            return Nothing.notReturn();
        }
        @NothingInject(
            wrapperMethodName = "register",
            wrapperMethodParams = { ItemStack.class, ItemStack.class, float.class },
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void register$end(@CustomVar("eventRecipeLoad") WrapperObject.Generic<EventRecipeLoad> event)
        {
            event.getWrapped().finish();
        }

        @NothingInject(
            wrapperMethodName = "<init>",
            wrapperMethodParams = {},
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void static$of$end()
        {
            RegistrarRecipeV_1300.instance.onReloadEnd(this);
        }
    }
    @VersionRange(end = 1300)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManagerV_1300 extends Nothing, RecipeManager
    {
        static void locate$static$ofV_1200$begin(NothingInjectLocating locating)
        {
            locating.nextAccessWrapped(RecipeManager.class, "setRecipes0V_1200", List.class);
            locating.offset(1);
            if(locating.locations.size() != 1)
                throw new IllegalStateException();
        }
        @VersionRange(end = 1200)
        @NothingInject(
            wrapperMethodName = "<init>",
            wrapperMethodParams = {},
            locateMethod = "locate$static$ofV_1200$begin",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void static$ofV_1200$begin()
        {
            this.setRecipes0V_1200(
                new ListProxy<Object, Object>(this.getRecipes0V_1200(), FunctionInvertible.identity())
                {
                    @Override
                    public boolean add(Object o)
                    {
                        this.add(this.size(), o);
                        return true;
                    }
                    @Override
                    public void add(int index, Object element)
                    {
                        RecipeVanilla recipe = RecipeVanilla.FACTORY.create(element).autoCast();
                        EventRecipeLoad event = new EventRecipeLoad(recipe.calcIdV_1200(), recipe);
                        event.call();
                        if(!event.isCancelled())
                            super.add(index, element);
                        event.finish();
                    }
                });
        }
        @VersionRange(end = 1200)
        @NothingInject(
            wrapperMethodName = "<init>",
            wrapperMethodParams = {},
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void static$ofV_1200$end()
        {
            RegistrarRecipeV_1200.instance.onReloadEnd(this);
        }

        @VersionRange(begin = 1200)
        @NothingInject(
            wrapperMethodName = "static$setupV1200_1300",
            wrapperMethodParams = {},
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        static void setupV1200_1300$end()
        {
            RegistrarRecipe.instance.onReloadEnd();
        }

        @VersionRange(begin = 1200)
        @NothingInject(
            wrapperMethodName = "static$registerV1200_1300",
            wrapperMethodParams = { Identifier.class, RecipeVanilla.class },
            locateMethod = "",
            type = NothingInjectType.INSERT_BEFORE
        )
        static Wrapper_void registerV1200_1300$begin(
            @CustomVar("eventRecipeLoad") WrapperObject.Generic<EventRecipeLoad> event,
            @LocalVar(0) Identifier id,
            @LocalVar(1) RecipeVanilla recipe)
        {
            event.setWrapped(new EventRecipeLoad(id, recipe));
            event.getWrapped().call();
            if(event.getWrapped().isCancelled())
            {
                event.getWrapped().finish();
                return Wrapper_void.FACTORY.create(null);
            }
            return Nothing.notReturn();
        }
        @VersionRange(begin = 1200)
        @NothingInject(
            wrapperMethodName = "static$registerV1200_1300",
            wrapperMethodParams = { Identifier.class, RecipeVanilla.class },
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        static void registerV1200_1300$end(@CustomVar("eventRecipeLoad") WrapperObject.Generic<EventRecipeLoad> event)
        {
            event.getWrapped().finish();
        }
    }
    @VersionRange(begin = 1300, end = 1400)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManagerV1300_1400 extends Nothing, RecipeManager, ResourceReloaderV1300
    {
        @NothingInject(
            wrapperMethodName = "reloadV_1400",
            wrapperMethodParams = { ResourceManagerV1300.class },
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void reload$end()
        {
            RegistrarRecipe.instance.onReloadEnd();
        }
        @NothingInject(
            wrapperMethodName = "registerV1300_1400",
            wrapperMethodParams = { RecipeVanilla.class },
            locateMethod = "",
            type = NothingInjectType.INSERT_BEFORE
        )
        default Wrapper_void registerV1300_1400$begin(@CustomVar("eventRecipeLoad") WrapperObject.Generic<EventRecipeLoad> event, @LocalVar(1) RecipeVanilla recipe)
        {
            event.setWrapped(new EventRecipeLoad(recipe.getIdV1300_2002(), recipe));
            event.getWrapped().call();
            if(event.getWrapped().isCancelled())
            {
                event.getWrapped().finish();
                return Wrapper_void.FACTORY.create(null);
            }
            return Nothing.notReturn();
        }
        @NothingInject(
            wrapperMethodName = "registerV1300_1400",
            wrapperMethodParams = { RecipeVanilla.class },
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void registerV1300_1400$end(@CustomVar("eventRecipeLoad") WrapperObject.Generic<EventRecipeLoad> event)
        {
            event.getWrapped().finish();
        }
    }
    @VersionRange(begin = 1400)
    @WrapSameClass(RecipeManager.class)
    public interface NothingRecipeManagerV1400 extends Nothing, RecipeManager, SinglePreparationResourceReloaderV1400<Object>
    {
        @WrapMethodFromBridge(
            name = "applyV1400", params = { Object.class, ResourceManagerV1300.class, Profiler.class }
        )
        void apply$impl(Object prepared, ResourceManagerV1300 manager, Profiler profiler);

        @NothingInject(
            wrapperMethodName = "apply$impl",
            wrapperMethodParams = { Object.class, ResourceManagerV1300.class, Profiler.class },
            locateMethod = "locateAllReturn",
            type = NothingInjectType.INSERT_BEFORE
        )
        default void apply$impl$end()
        {
            RegistrarRecipe.instance.onReloadEnd();
        }
    }
}
