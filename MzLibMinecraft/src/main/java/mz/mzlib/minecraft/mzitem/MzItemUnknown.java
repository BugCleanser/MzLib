package mz.mzlib.minecraft.mzitem;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@WrapSameClass(MzItem.class)
public interface MzItemUnknown extends MzItem
{
    WrapperFactory<MzItemUnknown> FACTORY = WrapperFactory.find(MzItemUnknown.class);
    
    @Override
    default Identifier staticGetMzId()
    {
        return Identifier.newInstance(MzLibMinecraft.instance.MOD_ID, "unknown");
    }
    
    @Override
    default Identifier getMzId()
    {
        return Identifier.newInstance(Item.getCustomData(this).unwrap().getNBTCompound("mz").unwrap().getString("id").unwrap());
    }
}
