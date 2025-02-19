package mz.mzlib.minecraft.mzitem;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.CallOnce;
import mz.mzlib.util.wrapper.WrapSameClass;

@WrapSameClass(ItemStack.class)
public interface MzItem extends ItemStack
{
    Identifier staticGetId();
    
    ItemStack staticVanilla();
    
    @CallOnce
    void init();
    
    default Option<NbtCompound> getMzData()
    {
        for(NbtCompound mz: Item.getCustomData(this).getNBTCompound("mz"))
            return mz.getNBTCompound("data");
        return Option.none();
    }
    
    default void setMzData(NbtCompound value)
    {
        Item.editCustomData(this).getOrPutNewCompound("mz").put("data", value);
    }
    
    default NbtCompound editMzData()
    {
        return Item.editCustomData(this).getOrPutNewCompound("mz").getOrPutNewCompound("data");
    }
}
