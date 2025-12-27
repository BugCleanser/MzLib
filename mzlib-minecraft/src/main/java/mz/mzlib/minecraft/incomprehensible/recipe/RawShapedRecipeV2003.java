package mz.mzlib.minecraft.incomprehensible.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.recipe.IngredientVanilla;
import mz.mzlib.minecraft.util.collection.DefaultedListV1100;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.Option;
import mz.mzlib.util.proxy.ListProxy;
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

    static RawShapedRecipeV2003 of(
        int width,
        int height,
        List<Option<IngredientVanilla>> ingredients,
        Option<Data> data)
    {
        return FACTORY.getStatic().static$of(width, height, ingredients, data);
    }
    static RawShapedRecipeV2003 of(
        int width,
        int height,
        List<Option<IngredientVanilla>> ingredients)
    {
        return of(width, height, ingredients, Option.none());
    }

    @WrapMinecraftMethod(
        {
            @VersionName(name = "width", end = 2100),
            @VersionName(name = "getWidth", begin = 2100)
        }
    )
    int getWidth();
    @WrapMinecraftMethod(
        {
            @VersionName(name = "height", end = 2100),
            @VersionName(name = "getHeight", begin = 2100)
        }
    )
    int getHeight();

    List<Option<IngredientVanilla>> getIngredients();


    RawShapedRecipeV2003 static$of(
        int width,
        int height,
        List<Option<IngredientVanilla>> ingredients,
        Option<Data> data);
    @SpecificImpl("static$of")
    @VersionRange(end = 2102)
    default RawShapedRecipeV2003 static$ofV_2102(
        int width,
        int height,
        List<Option<IngredientVanilla>> ingredients,
        Option<Data> data)
    {
        return this.static$ofV_2102(
            width, height, DefaultedListV1100.fromWrapper(
                ingredients.stream().map(IngredientVanilla::fromOptionV_2102).collect(Collectors.toList()),
                IngredientVanilla.EMPTY_V_2102
            ), data.map(Data::getWrapped).toOptional()
        );
    }
    @VersionRange(end = 2102)
    @WrapConstructor
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    RawShapedRecipeV2003 static$ofV_2102(
        int width,
        int height,
        DefaultedListV1100<?> ingredients,
        Optional<?> data);
    @SpecificImpl("static$of")
    @VersionRange(begin = 2102)
    default RawShapedRecipeV2003 static$ofV2102(
        int width,
        int height,
        List<Option<IngredientVanilla>> ingredients,
        Option<Data> data)
    {
        return this.static$ofV2102(
            width, height,
            ingredients.stream().map(it -> it.map(IngredientVanilla::getWrapped).toOptional())
                .collect(Collectors.toList()), data.map(Data::getWrapped).toOptional()
        );
    }
    @VersionRange(begin = 2102)
    @WrapConstructor
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    RawShapedRecipeV2003 static$ofV2102(
        int width,
        int height,
        List<Optional<?>> ingredients,
        Optional<?> data);

    @WrapMinecraftInnerClass(outer = RawShapedRecipeV2003.class, name = @VersionName(name = "Data"))
    interface Data extends WrapperObject
    {
        WrapperFactory<Data> FACTORY = WrapperFactory.of(Data.class);
    }

    @SpecificImpl("getIngredients")
    @VersionRange(end = 2102)
    default List<Option<IngredientVanilla>> getIngredientsV_2102()
    {
        return new ListProxy<>(
            this.getIngredients0V_2102().getWrapped(),
            FunctionInvertible.wrapper(IngredientVanilla.FACTORY)
                .thenApply(IngredientVanilla::toOptionV_2102, IngredientVanilla::fromOptionV_2102)
        );
    }
    @VersionRange(end = 2102)
    @WrapMinecraftMethod(
        {
            @VersionName(name = "ingredients", end = 2100),
            @VersionName(name = "getIngredients", begin = 2100)
        }
    )
    DefaultedListV1100<Object> getIngredients0V_2102();

    @SpecificImpl("getIngredients")
    @VersionRange(begin = 2102)
    default List<Option<IngredientVanilla>> getIngredientsV2102()
    {
        return new ListProxy<>(
            this.getIngredients0V2102(),
            FunctionInvertible.option2optional().inverse()
                .thenApply(FunctionInvertible.optionMap(FunctionInvertible.wrapper(IngredientVanilla.FACTORY)))
        );
    }
    @VersionRange(begin = 2102)
    @WrapMinecraftMethod(@VersionName(name = "getIngredients"))
    List<Optional<Object>> getIngredients0V2102();
}
