package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.window.WindowSlot;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

/**
 * @see WindowSlotIcon
 */
@Deprecated
@Compound
public interface WindowSlotButton extends WindowSlotIcon
{
    WrapperFactory<WindowSlotButton> FACTORY = WrapperFactory.of(WindowSlotButton.class);
    @Deprecated
    @WrapperCreator
    static WindowSlotButton create(Object wrapped)
    {
        return WrapperObject.create(WindowSlotButton.class, wrapped);
    }

    @WrapConstructor
    WindowSlotButton static$newInstance(Inventory inventory, int index, int x, int y);

    static WindowSlot newInstance(Inventory inventory, int index)
    {
        return FACTORY.getStatic().static$newInstance(inventory, index, 0, 0);
    }
}
