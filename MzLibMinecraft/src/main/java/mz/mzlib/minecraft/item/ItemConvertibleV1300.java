package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1300)
@WrapMinecraftClass({@VersionName(name="net.minecraft.item.Itemable", end=1400), @VersionName(name="net.minecraft.item.ItemConvertible", begin=1400)})
public interface ItemConvertibleV1300 extends WrapperObject
{
    @WrapperCreator
    static ItemConvertibleV1300 create(Object wrapped)
    {
        return WrapperObject.create(ItemConvertibleV1300.class, wrapped);
    }

    @WrapMinecraftMethod({@VersionName(name="getItem",end=1400),@VersionName(name="asItem",begin=1400)})
    Item asItem();
}
