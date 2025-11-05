package mz.mzlib.minecraft.mzitem;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Editor;
import mz.mzlib.util.Option;
import mz.mzlib.util.wrapper.CallOnce;
import mz.mzlib.util.wrapper.WrapSameClass;

@WrapSameClass(ItemStack.class)
public interface MzItem extends ItemStack
{
    Identifier static$getMzId();
    
    ItemStack static$vanilla();
    
    @CallOnce
    default void init(NbtCompound data)
    {
        for(NbtCompound customData: Item.reviseCustomData(this))
        {
            for(NbtCompound mz: customData.reviseNbtCompoundOrNew("mz"))
            {
                mz.put("id", this.getMzId().toString());
            }
        }
    }
    
    default Identifier getMzId()
    {
        return this.static$getMzId();
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
    
    class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(RegistrarMzItem.instance);
            
            this.register(MzItemUsable.Module.instance);
            
            this.register(MzItemDebugStick.class);
        }
    }
}
