package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemConvertibleV1300;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.mzitem.MzItem;
import mz.mzlib.minecraft.mzitem.RegistrarMzItem;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapMethodFromBridge;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Arrays;
import java.util.function.Predicate;

@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.item.ItemStack", end = 1200),
        @VersionName(name = "net.minecraft.recipe.Ingredient", begin = 1200)
    }
)
public interface VanillaIngredient extends WrapperObject, Predicate<ItemStack>
{
    WrapperFactory<VanillaIngredient> FACTORY = WrapperFactory.of(VanillaIngredient.class);

    static VanillaIngredient emptyV_2102()
    {
        return FACTORY.getStatic().static$emptyV_2102();
    }

    static VanillaIngredient fromOptionV_2102(Option<VanillaIngredient> option)
    {
        return option.unwrapOrGet(VanillaIngredient::emptyV_2102);
    }

    @Override
    boolean test(ItemStack itemStack);

    static VanillaIngredient of(ItemStack itemStack)
    {
        return FACTORY.getStatic().static$of(itemStack);
    }

    @VersionRange(begin = 1200, end = 1300)
    static VanillaIngredient ofItemStacksV1200_1300(ItemStack... itemStacks)
    {
        ItemStack.Array array = ItemStack.Array.newInstance(itemStacks.length);
        for(int i = 0; i < itemStacks.length; i++)
        {
            array.set(i, itemStacks[i]);
        }
        return FACTORY.getStatic().static$ofItemStacksV1200_1300(array);
    }
    @VersionRange(begin = 1300)
    static VanillaIngredient ofItemsV1300(ItemConvertibleV1300... items)
    {
        ItemConvertibleV1300.Array array = ItemConvertibleV1300.Array.newInstance(items.length);
        for(int i = 0; i < items.length; i++)
        {
            array.set(i, items[i]);
        }
        return FACTORY.getStatic().static$ofItemsV1300(array);
    }
    @VersionRange(begin = 1200)
    static VanillaIngredient ofItemsV1200(Item... items)
    {
        return FACTORY.getStatic().static$ofItemsV1200(items);
    }


    VanillaIngredient static$emptyV_2102();
    @SpecificImpl("static$emptyV_2102")
    @VersionRange(end = 1200)
    default VanillaIngredient static$emptyV_1200()
    {
        return ItemStack.empty().as(FACTORY);
    }
    @SpecificImpl("static$emptyV_2102")
    @VersionRange(begin = 1200, end = 2102)
    @WrapMinecraftFieldAccessor(@VersionName(name = "EMPTY"))
    VanillaIngredient static$emptyV1200_2102();

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

    VanillaIngredient static$of(ItemStack itemStack);
    @SpecificImpl("static$of")
    @VersionRange(end = 1200)
    default VanillaIngredient static$ofV_1200(ItemStack itemStack)
    {
        return itemStack.as(FACTORY);
    }
    @SpecificImpl("static$of")
    @VersionRange(begin = 1200, end = 1300)
    default VanillaIngredient static$ofV1200_1300(ItemStack itemStack)
    {
        return ofItemStacksV1200_1300(itemStack);
    }
    @SpecificImpl("static$of")
    @VersionRange(begin = 1300)
    default VanillaIngredient static$ofV1300(ItemStack itemStack)
    {
        return ofItemsV1300(itemStack.getItem().as(Item.V1300.FACTORY));
    }

    VanillaIngredient static$ofItemsV1200(Item... items);
    @SpecificImpl("static$ofItemsV1200")
    @VersionRange(begin = 1200, end = 1300)
    default VanillaIngredient static$ofItemsV1200_1300(Item... items)
    {
        return ofItemStacksV1200_1300(Arrays.stream(items).map(ItemStack::newInstance).toArray(ItemStack[]::new));
    }
    @SpecificImpl("static$ofItemsV1200")
    @VersionRange(begin = 1300)
    default VanillaIngredient static$ofItemsV1300(Item... items)
    {
        return ofItemsV1300(Arrays.stream(items)
            .map(it -> it.as(Item.V1300.FACTORY)).toArray(ItemConvertibleV1300[]::new));
    }

    @VersionRange(begin = 1200, end = 1300)
    @WrapMinecraftMethod(@VersionName(name = "method_14248"))
    VanillaIngredient static$ofItemStacksV1200_1300(ItemStack.Array itemStacks);

    @VersionRange(begin = 1300)
    @WrapMinecraftMethod(@VersionName(name = "ofItems"))
    VanillaIngredient static$ofItemsV1300(ItemConvertibleV1300.Array items);
}
