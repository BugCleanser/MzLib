package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2610)
@WrapClassForName("net.minecraft.world.item.ItemInstance")
public interface ItemInstanceV2610 extends WrapperObject
{
    WrapperFactory<ItemInstanceV2610> FACTORY = WrapperFactory.of(ItemInstanceV2610.class);

    @WrapMethod("getMaxStackSize")
    int getMaxStackCount();
}
