package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.*;

@VersionRange(begin = 1300)
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.item.Itemable", end = 1400),
    @VersionName(name = "net.minecraft.item.ItemConvertible", begin = 1400)
})
public interface ItemConvertibleV1300 extends WrapperObject
{
    WrapperFactory<ItemConvertibleV1300> FACTORY = WrapperFactory.of(ItemConvertibleV1300.class);
    @Deprecated
    @WrapperCreator
    static ItemConvertibleV1300 create(Object wrapped)
    {
        return WrapperObject.create(ItemConvertibleV1300.class, wrapped);
    }

    @WrapMinecraftMethod({ @VersionName(name = "getItem", end = 1400), @VersionName(name = "asItem", begin = 1400) })
    Item asItem();

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
