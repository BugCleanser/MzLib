package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.window.WindowSlot;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

/**
 * This slot is just a button.
 * Player cannot place or take item on it
 */
@Compound
public interface WindowSlotButton extends WindowSlot
{
    WrapperFactory<WindowSlotButton> FACTORY = WrapperFactory.of(WindowSlotButton.class);
    @Deprecated
    @WrapperCreator
    static WindowSlotButton create(Object wrapped)
    {
        return WrapperObject.create(WindowSlotButton.class, wrapped);
    }

    @WrapConstructor
    WindowSlot static$newInstance(Inventory inventory, int index, int x, int y);

    static WindowSlot newInstance(Inventory inventory, int index)
    {
        return FACTORY.getStatic().static$newInstance(inventory, index, 0, 0);
    }

    @Override
    @CompoundOverride(parent = WindowSlot.class, method = "canPlace")
    default boolean canPlace(ItemStack itemStack)
    {
        return false;
    }

    @Override
    @CompoundOverride(parent = WindowSlot.class, method = "canTake")
    default boolean canTake(AbstractEntityPlayer player)
    {
        return false;
    }
}
