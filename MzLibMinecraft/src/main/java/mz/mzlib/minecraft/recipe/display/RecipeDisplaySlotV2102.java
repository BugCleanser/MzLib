package mz.mzlib.minecraft.recipe.display;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.context.MojangContextV2102;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@VersionRange(begin = 2102)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.display.SlotDisplay"))
public interface RecipeDisplaySlotV2102 extends WrapperObject
{
    WrapperFactory<RecipeDisplaySlotV2102> FACTORY = WrapperFactory.of(RecipeDisplaySlotV2102.class);

    default List<ItemStack> getItemStacks(MojangContextV2102 context)
    {
        return new ListProxy<>(this.getItemStacks0(context), FunctionInvertible.wrapper(ItemStack.FACTORY));
    }


    @WrapMinecraftMethod(@VersionName(name = "getStacks"))
    List<Object> getItemStacks0(MojangContextV2102 context);
}
