package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1300)
@WrapSameClass(Item.class)
public interface ItemV1300 extends WrapperObject, Item, ItemConvertibleV1300
{
    WrapperFactory<ItemV1300> FACTORY = WrapperFactory.of(ItemV1300.class);
    @Deprecated
    @WrapperCreator
    static ItemV1300 create(Object wrapped)
    {
        return WrapperObject.create(ItemV1300.class, wrapped);
    }
}
