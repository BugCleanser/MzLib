package mz.mzlib.minecraft.mzitem;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.util.Editor;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.CallOnce;
import mz.mzlib.util.wrapper.WrapSameClass;

@WrapSameClass(ItemStack.class)
public interface MzItem extends ItemStack
{
    Identifier staticGetMzId();
    
    ItemStack staticVanilla();
    
    @CallOnce
    void init(NbtCompound data);
    
    default Identifier getMzId()
    {
        return this.staticGetMzId();
    }
    
    default Option<NbtCompound> getMzData()
    {
        for(NbtCompound customData: Item.getCustomData(this))
            for(NbtCompound mz: customData.getNbtCompound("mz"))
                return mz.getNbtCompound("data");
        return Option.none();
    }
    
    default Editor<NbtCompound> reviseMzData()
    {
        return Item.reviseCustomData(this)
                .then(nbt -> nbt.reviseNbtCompoundOrNew("mz"))
                .then(nbt -> nbt.reviseNbtCompoundOrNew("data"));
    }
}
