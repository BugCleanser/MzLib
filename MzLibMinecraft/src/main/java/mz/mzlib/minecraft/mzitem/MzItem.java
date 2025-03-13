package mz.mzlib.minecraft.mzitem;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.util.Editor;
import mz.mzlib.util.Option;
import mz.mzlib.util.Ref;
import mz.mzlib.util.wrapper.CallOnce;
import mz.mzlib.util.wrapper.WrapSameClass;

@WrapSameClass(ItemStack.class)
public interface MzItem extends ItemStack
{
    Identifier staticGetMzId();
    
    ItemStack staticVanilla();
    
    @CallOnce
    void init();
    
    default Identifier getMzId()
    {
        return this.staticGetMzId();
    }
    
    default Option<NbtCompound> getMzData()
    {
        for(NbtCompound customData: Item.getCustomData(this))
            for(NbtCompound mz: customData.getNBTCompound("mz"))
                return mz.getNBTCompound("data");
        return Option.none();
    }
    
    default void setMzData(Option<NbtCompound> value)
    {
        editing:
        for(Ref<Option<NbtCompound>> ref: Item.editCustomData(this))
        {
            for(NbtCompound data: value)
            {
                NbtCompound customData = Ref.getOrSet(ref, NbtCompound::newInstance);
                customData.getOrPutNewCompound("mz").put("data", data);
                continue editing;
            }
            for(NbtCompound customData: ref.get())
                for(NbtCompound mz: customData.get("mz", NbtCompound.FACTORY))
                    mz.remove("data");
        }
    }
    
    default Editor<Ref<Option<NbtCompound>>> editMzData()
    {
        return Item.editCustomData(this).then(r->r.map(o->o.then(c->c.getNBTCompound("mz").then(mz->mz.getNBTCompound("data")))), (r, d)->
        {
            for(NbtCompound data: d.get())
            {
                NbtCompound customData = Ref.getOrSet(r, NbtCompound::newInstance);
                customData.getOrPutNewCompound("mz").put("data", data);
                return;
            }
            for(NbtCompound customData: r.get())
                for(NbtCompound mz: customData.get("mz", NbtCompound.FACTORY))
                    mz.remove("data");
        });
    }
}
