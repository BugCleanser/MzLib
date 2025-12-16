package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.entity.player.EntityPlayerAbstract;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.window.WindowSlot;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.compound.PropAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperFactory;

/**
 * This slot is just an icon.
 * Player cannot place or take item on it
 */
@Compound
public interface WindowSlotUiWindow extends WindowSlot
{
    WrapperFactory<WindowSlotUiWindow> FACTORY = WrapperFactory.of(WindowSlotUiWindow.class);

    @WrapConstructor
    WindowSlotUiWindow static$newInstance(Inventory inventory, int index, int x, int y);

    @PropAccessor("ui")
    UiWindow getUi();
    @PropAccessor("ui")
    void setUi(UiWindow value);

    static WindowSlotUiWindow newInstance(UiWindow ui, Inventory inventory, int index)
    {
        WindowSlotUiWindow result = FACTORY.getStatic().static$newInstance(inventory, index, 0, 0);
        result.setUi(ui);
        return result;
    }

    @Override
    @CompoundOverride(parent = WindowSlot.class, method = "canPlace")
    default boolean canPlace(ItemStack itemStack)
    {
        for(UiWindow.ControlHit hit : this.getUi().slot2point(this.getSlotIndex()))
        {
            if(!hit.enabled)
                return false;
            return hit.control.canPlace(hit.point);
        }
        return true;
    }

    @Override
    @CompoundOverride(parent = WindowSlot.class, method = "canTake")
    default boolean canTake(EntityPlayerAbstract player)
    {
        for(UiWindow.ControlHit hit : this.getUi().slot2point(this.getSlotIndex()))
        {
            if(!hit.enabled)
                return false;
            return hit.control.canTake(hit.point);
        }
        return true;
    }
}
