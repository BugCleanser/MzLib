package mz.mzlib.minecraft.mzitem;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;

@MzItemClass
public interface MzItemIconPlaceholder extends MzItem
{
    MzItemIconPlaceholder instance = RegistrarMzItem.instance.newMzItem(MzItemIconPlaceholder.class);

    @Override
    default ItemStack static$vanilla()
    {
        return ItemStack.newInstance(Item.fromId(Identifier.minecraft("stick")));
    }

    @Override
    default Identifier static$getMzId()
    {
        return Identifier.newInstance(MzLibMinecraft.instance.MOD_ID, "icon_placeholder");
    }
}
