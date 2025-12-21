package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemConvertibleV1300;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.mzitem.MzItem;
import mz.mzlib.minecraft.mzitem.RegistrarMzItem;
import mz.mzlib.minecraft.registry.entry.RegistryEntryV1802;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.item.ItemStack", end = 1200),
        @VersionName(name = "net.minecraft.recipe.Ingredient", begin = 1200)
    }
)
public interface IngredientVanilla extends WrapperObject, Ingredient
{
    WrapperFactory<IngredientVanilla> FACTORY = WrapperFactory.of(IngredientVanilla.class);

    static IngredientVanilla emptyV_2102()
    {
        return FACTORY.getStatic().static$emptyV_2102();
    }

    static IngredientVanilla fromOptionV_2102(Option<IngredientVanilla> option)
    {
        return option.unwrapOrGet(IngredientVanilla::emptyV_2102);
    }
    default Option<IngredientVanilla> toOptionV_2102()
    {
        if(this.equals(emptyV_2102()))
            return Option.none();
        return Option.some(this);
    }

    @Override
    boolean test(ItemStack itemStack);

    static IngredientVanilla of(ItemStack itemStack)
    {
        return FACTORY.getStatic().static$of(itemStack);
    }

    @VersionRange(begin = 1200, end = 1300)
    static IngredientVanilla ofItemStacksV1200_1300(ItemStack... itemStacks)
    {
        return FACTORY.getStatic().static$ofItemStacksV1200_1300(
            Arrays.stream(itemStacks).collect(WrapperArray.collector(ItemStack.Array.FACTORY)));
    }
    @VersionRange(begin = 1300)
    static IngredientVanilla ofItemsV1300(ItemConvertibleV1300... items)
    {
        return FACTORY.getStatic().static$ofItemsV1300(
            Arrays.stream(items).collect(WrapperArray.collector(ItemConvertibleV1300.Array.FACTORY)));
    }
    @VersionRange(begin = 1200)
    static IngredientVanilla ofItemsV1200(Item... items)
    {
        return FACTORY.getStatic().static$ofItemsV1200(items);
    }

    List<ItemStack> getMatchingStacks();

    @WrapArrayClass(IngredientVanilla.class)
    interface Array extends WrapperArray<IngredientVanilla>
    {
        WrapperFactory<Array> FACTORY = WrapperFactory.of(Array.class);

        static Array newInstance(int size)
        {
            return (Array) FACTORY.getStatic().static$newInstance(size);
        }
    }


    IngredientVanilla static$emptyV_2102();
    @SpecificImpl("static$emptyV_2102")
    @VersionRange(end = 1200)
    default IngredientVanilla static$emptyV_1200()
    {
        return ItemStack.empty().as(FACTORY);
    }
    @SpecificImpl("static$emptyV_2102")
    @VersionRange(begin = 1200, end = 2102)
    @WrapMinecraftFieldAccessor(
        {
            @VersionName(name = "field_15680", end = 1400),
            @VersionName(name = "EMPTY", begin = 1400)
        }
    )
    IngredientVanilla static$emptyV1200_2102();

    @SpecificImpl("test")
    @VersionRange(end = 1200)
    default boolean testV_1200(ItemStack itemStack)
    {
        for(MzItem mzItem : RegistrarMzItem.instance.toMzItem(itemStack))
        {
            if(!mzItem.isVanilla())
                return false;
        }
        ItemStack thisItemStack = this.as(ItemStack.FACTORY);
        if(!itemStack.getItem().equals(thisItemStack.getItem()))
            return false;
        return thisItemStack.getDamageV_1300() == Short.MAX_VALUE ||
            thisItemStack.getDamageV_1300() == itemStack.getDamageV_1300();
    }

    @VersionRange(begin = 1200)
    @WrapMinecraftMethod(
        {
            @VersionName(name = "apply", remap = false, end = 1300),
            @VersionName(name = "test", remap = false, begin = 1300)
        }
    )
    boolean testV1200$bridge(Object itemStack);
    @SpecificImpl("test")
    @VersionRange(begin = 1200)
    @WrapMethodFromBridge(name = "testV1200$bridge", params = { Object.class })
    boolean testV1200(ItemStack itemStack);

    IngredientVanilla static$of(ItemStack itemStack);
    @SpecificImpl("static$of")
    @VersionRange(end = 1200)
    default IngredientVanilla static$ofV_1200(ItemStack itemStack)
    {
        return itemStack.as(FACTORY);
    }
    @SpecificImpl("static$of")
    @VersionRange(begin = 1200, end = 1300)
    default IngredientVanilla static$ofV1200_1300(ItemStack itemStack)
    {
        return ofItemStacksV1200_1300(itemStack);
    }
    @SpecificImpl("static$of")
    @VersionRange(begin = 1300)
    default IngredientVanilla static$ofV1300(ItemStack itemStack)
    {
        return ofItemsV1300(itemStack.getItem().as(Item.V1300.FACTORY));
    }

    IngredientVanilla static$ofItemsV1200(Item... items);
    @SpecificImpl("static$ofItemsV1200")
    @VersionRange(begin = 1200, end = 1300)
    default IngredientVanilla static$ofItemsV1200_1300(Item... items)
    {
        return ofItemStacksV1200_1300(Arrays.stream(items).map(ItemStack::newInstance).toArray(ItemStack[]::new));
    }
    @SpecificImpl("static$ofItemsV1200")
    @VersionRange(begin = 1300)
    default IngredientVanilla static$ofItemsV1300(Item... items)
    {
        return ofItemsV1300(Arrays.stream(items)
            .map(it -> it.as(Item.V1300.FACTORY)).toArray(ItemConvertibleV1300[]::new));
    }

    @VersionRange(begin = 1200, end = 1300)
    @WrapMinecraftMethod(@VersionName(name = "method_14248"))
    IngredientVanilla static$ofItemStacksV1200_1300(ItemStack.Array itemStacks);

    @VersionRange(begin = 1300)
    @WrapMinecraftMethod(@VersionName(name = "ofItems"))
    IngredientVanilla static$ofItemsV1300(ItemConvertibleV1300.Array items);

    @SpecificImpl("getMatchingStacks")
    @VersionRange(end = 1200)
    default List<ItemStack> getMatchingStacksV_1200()
    {
        return Collections.singletonList(this.as(ItemStack.FACTORY));
    }

    @SpecificImpl("getMatchingStacks")
    @VersionRange(begin = 1200, end = 1300)
    default List<ItemStack> getMatchingStacksV1200_1300()
    {
        return this.getCachedMatchingStacks0V1200_2102().asList();
    }

    @SpecificImpl("getMatchingStacks")
    @VersionRange(begin = 1300, end = 1701)
    default List<ItemStack> getMatchingStacksV1300_1701()
    {
        this.cacheMatchingStacksV1300_1903();
        return this.getCachedMatchingStacks0V1200_2102().asList();
    }

    @SpecificImpl("getMatchingStacks")
    @VersionRange(begin = 1701, end = 2102)
    default List<ItemStack> getMatchingStacksV1701_2102()
    {
        return this.getMatchingStacks0V1701_2102().asList();
    }
    @VersionRange(begin = 1701, end = 2102)
    @WrapMinecraftMethod(@VersionName(name = "getMatchingStacks"))
    ItemStack.Array getMatchingStacks0V1701_2102();

    @SpecificImpl("getMatchingStacks")
    @VersionRange(begin = 2102)
    default List<ItemStack> getMatchingStacksV2102()
    {
        return this.getMatchingItemsV2102().map(RegistryEntryV1802.Wrapper::getValue)
            .map(ItemStack::newInstance).collect(Collectors.toList());
    }
    default Stream<RegistryEntryV1802.Wrapper<Item>> getMatchingItemsV2102()
    {
        return this.getMatchingItems0V2102().map(FunctionInvertible.wrapper(RegistryEntryV1802.FACTORY)
            .thenApply(it -> new RegistryEntryV1802.Wrapper<>(it, Item.FACTORY), RegistryEntryV1802.Wrapper::getBase));
    }
    Stream<Object> getMatchingItems0V2102();
    @SpecificImpl("getMatchingItems0V2102")
    @VersionRange(begin = 2102, end = 2104)
    default Stream<Object> getMatchingItems0V2102_2104()
    {
        return this.getMatchingItems00V2102_2104().stream();
    }
    @VersionRange(begin = 2102, end = 2104)
    @WrapMinecraftMethod(@VersionName(name = "getMatchingItems"))
    List<Object> getMatchingItems00V2102_2104();
    @SpecificImpl("getMatchingItems0V2102")
    @VersionRange(begin = 2104)
    @WrapMinecraftMethod(@VersionName(name = "getMatchingItems"))
    Stream<Object> getMatchingItems0V2104();

    @VersionRange(begin = 1200, end = 2102)
    @WrapMinecraftFieldAccessor(
        {
            @VersionName(name = "field_15681", end = 1400),
            @VersionName(name = "matchingStacks", begin = 1400)
        }
    )
    ItemStack.Array getCachedMatchingStacks0V1200_2102();
    @VersionRange(begin = 1300, end = 1903)
    @WrapMinecraftMethod(
        {
            @VersionName(name = "method_16197", end = 1400),
            @VersionName(name = "cacheMatchingStacks", begin = 1400)
        }
    )
    void cacheMatchingStacksV1300_1903();
}
