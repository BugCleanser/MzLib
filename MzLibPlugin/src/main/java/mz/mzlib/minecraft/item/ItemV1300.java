package mz.mzlib.minecraft.item;

import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapSameClass(Item.class)
public interface ItemV1300 extends ItemConvertibleV1300
{
    @WrapperCreator
    static ItemV1300 create(Object wrapped)
    {
        return WrapperObject.create(ItemV1300.class, wrapped);
    }
}
