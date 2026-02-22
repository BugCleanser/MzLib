package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapArrayClass;
import mz.mzlib.util.wrapper.WrapperArray;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1300)
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.item.Itemable", end = 1400),
    @VersionName(name = "net.minecraft.item.ItemConvertible", begin = 1400)
})
public interface ItemConvertibleV1300 extends WrapperObject
{
    WrapperFactory<ItemConvertibleV1300> FACTORY = WrapperFactory.of(ItemConvertibleV1300.class);
    @WrapMinecraftMethod({ @VersionName(name = "getItem", end = 1400), @VersionName(name = "asItem", begin = 1400) })
    Item asItem();

    @VersionRange(begin = 1300)
    @WrapArrayClass(ItemConvertibleV1300.class)
    interface Array extends WrapperArray<ItemConvertibleV1300>
    {
        WrapperFactory<Array> FACTORY = WrapperFactory.of(Array.class);

        static Array newInstance(int size)
        {
            return (Array) FACTORY.getStatic().static$newInstance(size);
        }
    }
}

