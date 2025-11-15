package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.mzitem.MzItem;
import mz.mzlib.minecraft.mzitem.RegistrarMzItem;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Predicate;

@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.item.ItemStack", end = 1200),
    @VersionName(name = "net.minecraft.recipe.Ingredient", begin = 1200)
})
public interface VanillaIngredient extends WrapperObject, Predicate<ItemStack>
{
    WrapperFactory<VanillaIngredient> FACTORY = WrapperFactory.of(VanillaIngredient.class);

    @Override
    boolean test(ItemStack itemStack);
    @SpecificImpl("test")
    @VersionRange(end = 1200)
    default boolean testV_1200(ItemStack itemStack)
    {
        for(MzItem mzItem : RegistrarMzItem.instance.toMzItem(itemStack))
        {
            if(!mzItem.isVanilla())
                return false;
        }
        return itemStack.getItem().equals(this.as(ItemStack.FACTORY).getItem());
    }
    @SpecificImpl("test")
    @VersionRange(begin = 1200)
    @WrapMinecraftMethod({
        @VersionName(name = "apply", remap = false, end = 1300),
        @VersionName(name = "apply", end = 1300),
        @VersionName(name = "test", remap = false, begin = 1300),
        @VersionName(name = "test", begin = 1300, end = 1400),
        @VersionName(name = "method_8093", begin = 1400),
    })
    boolean testV1200(ItemStack itemStack);
}
