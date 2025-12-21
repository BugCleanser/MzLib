package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.entity.player.EntityPlayerAbstract;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.window.WindowSlot;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;

/**
 * This slot is just an icon.
 * Player cannot place or take item on it
 */
@Compound
public interface WindowSlotIcon extends WindowSlot
{
    WrapperFactory<WindowSlotIcon> FACTORY = WrapperFactory.of(WindowSlotIcon.class);

    @WrapConstructor
    WindowSlotIcon static$newInstance(Inventory inventory, int index, int x, int y);

    static WindowSlotIcon newInstance(Inventory inventory, int index)
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
    default boolean canTake(EntityPlayerAbstract player)
    {
        return false;
    }
}
