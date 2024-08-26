package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.item.Itemable", end=1400), @VersionName(name="net.minecraft.item.ItemConvertible", begin=1400)})
public interface ItemConvertible extends WrapperObject
{
    @WrapperCreator
    static ItemConvertible create(Object wrapped)
    {
        return WrapperObject.create(ItemConvertible.class, wrapped);
    }

    @WrapMinecraftMethod({@VersionName(name="getItem",end=1400),@VersionName(name="asItem",begin=1400)})
    Item asItem();
}
