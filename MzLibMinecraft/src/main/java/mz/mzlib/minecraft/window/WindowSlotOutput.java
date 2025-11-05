package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@Compound
public interface WindowSlotOutput extends WindowSlot
{
    WrapperFactory<WindowSlotOutput> FACTORY = WrapperFactory.of(WindowSlotOutput.class);
    @Deprecated
    @WrapperCreator
    static WindowSlotOutput create(Object wrapped)
    {
        return WrapperObject.create(WindowSlotOutput.class, wrapped);
    }
    
    @WrapConstructor
    WindowSlot static$newInstance(Inventory inventory, int index, int x, int y);
    
    static WindowSlot newInstance(Inventory inventory, int index)
    {
        return FACTORY.getStatic().static$newInstance(inventory, index, 0, 0);
    }
    
    @Override
    @CompoundOverride(parent=WindowSlot.class, method="canPlace")
    default boolean canPlace(ItemStack itemStack)
    {
        return false;
    }
}
