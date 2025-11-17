package mz.mzlib.minecraft.incomprehensible.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.recipe.VanillaIngredient;
import mz.mzlib.minecraft.util.collection.DefaultedListV1100;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@VersionRange(begin = 2003)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.RawShapedRecipe"))
public interface RawShapedRecipeV2003 extends WrapperObject
{
    WrapperFactory<RawShapedRecipeV2003> FACTORY = WrapperFactory.of(RawShapedRecipeV2003.class);

    static RawShapedRecipeV2003 newInstance(
        int width,
        int height,
        List<Option<VanillaIngredient>> ingredients,
        Option<Data> data)
    {
        return FACTORY.getStatic().static$newInstance(width, height, ingredients, data);
    }
    static RawShapedRecipeV2003 newInstance(
        int width,
        int height,
        List<Option<VanillaIngredient>> ingredients)
    {
        return newInstance(width, height, ingredients, Option.none());
    }


    RawShapedRecipeV2003 static$newInstance(
        int width,
        int height,
        List<Option<VanillaIngredient>> ingredients,
        Option<Data> data);
    @SpecificImpl("static$newInstance")
    @VersionRange(end = 2102)
    default RawShapedRecipeV2003 static$newInstanceV_2102(
        int width,
        int height,
        List<Option<VanillaIngredient>> ingredients,
        Option<Data> data)
    {
        return this.static$newInstanceV_2102(
            width, height, DefaultedListV1100.fromWrapper(
                ingredients.stream().map(VanillaIngredient::fromOptionV_2102).collect(Collectors.toList()),
                VanillaIngredient.emptyV_2102()
            ), data.map(Data::getWrapped).toOptional()
        );
    }
    @VersionRange(end = 2102)
    @WrapConstructor
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    RawShapedRecipeV2003 static$newInstanceV_2102(
        int width,
        int height,
        DefaultedListV1100<?> ingredients,
        Optional<?> data);
    @SpecificImpl("static$newInstance")
    @VersionRange(begin = 2102)
    default RawShapedRecipeV2003 static$newInstanceV2102(
        int width,
        int height,
        List<Option<VanillaIngredient>> ingredients,
        Option<Data> data)
    {
        return this.static$newInstanceV2102(
            width, height,
            ingredients.stream().map(it -> it.map(VanillaIngredient::getWrapped).toOptional())
                .collect(Collectors.toList()), data.map(Data::getWrapped).toOptional()
        );
    }
    @VersionRange(begin = 2102)
    @WrapConstructor
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    RawShapedRecipeV2003 static$newInstanceV2102(
        int width,
        int height,
        List<Optional<?>> ingredients,
        Optional<?> data);

    @WrapMinecraftInnerClass(outer = RawShapedRecipeV2003.class, name = @VersionName(name = "Data"))
    interface Data extends WrapperObject
    {
        WrapperFactory<Data> FACTORY = WrapperFactory.of(Data.class);
    }
}
