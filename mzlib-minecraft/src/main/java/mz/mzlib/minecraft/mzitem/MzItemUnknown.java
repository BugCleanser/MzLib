package mz.mzlib.minecraft.mzitem;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@WrapSameClass(MzItem.class)
public interface MzItemUnknown extends MzItem
{
    WrapperFactory<MzItemUnknown> FACTORY = WrapperFactory.of(MzItemUnknown.class);

    @Override
    default Identifier static$getMzId()
    {
        return Identifier.of(MzLibMinecraft.instance.MOD_ID, "unknown");
    }

    @Override
    default Identifier getMzId()
    {
        return Identifier.of(
            Item.CUSTOM_DATA.get(this).unwrap().getNbtCompound("mz").unwrap().getString("id").unwrap());
    }
}
