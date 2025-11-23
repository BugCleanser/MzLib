package mz.mzlib.minecraft.mzitem;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventorySimple;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.ui.window.WindowSlotIcon;
import mz.mzlib.minecraft.window.WindowSlot;

import java.util.function.Supplier;

@MzItemClass
public interface MzItemIconPlaceholder extends MzItem
{
    @SuppressWarnings("TrivialFunctionalExpressionUsage")
    Inventory inventory = ((Supplier<Inventory>)()->
    {
        InventorySimple result = InventorySimple.newInstance(1);
        result.setItemStack(0, RegistrarMzItem.instance.newMzItem(MzItemIconPlaceholder.class));
        return result;
    }).get();
    WindowSlot slot = WindowSlotIcon.newInstance(inventory, 0);

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
