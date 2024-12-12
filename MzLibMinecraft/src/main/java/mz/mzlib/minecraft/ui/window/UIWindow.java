package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UI;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.minecraft.window.WindowFactorySimple;
import mz.mzlib.minecraft.window.WindowSlot;
import mz.mzlib.minecraft.window.WindowTypeV1400;

import java.util.HashMap;
import java.util.Map;

public abstract class UIWindow implements UI
{
    public String windowIdV_1400;
    public WindowTypeV1400 typeV1400;
    public Inventory inventory;
    
    public UIWindow(String windowIdV_1400, WindowTypeV1400 typeV1400, Inventory inventory)
    {
        this.windowIdV_1400=windowIdV_1400;
        this.typeV1400=typeV1400;
        this.inventory = inventory;
    }
    
    public Map<Integer, SlotCreator> slots = new HashMap<>();
    
    void setSlot(int index, SlotCreator slotCreator)
    {
        this.slots.put(index, slotCreator);
    }
    
    void setSlot(int index, SlotCreator slotCreator, ItemStack itemStack)
    {
        this.setSlot(index, slotCreator);
        this.inventory.setItemStack(index, itemStack);
    }
    
    public abstract Text getTitle(EntityPlayer player);
    
    public void initWindow(Window window)
    {
        // TODO
    }
    
    @Override
    public void open(EntityPlayer player)
    {
        WindowFactorySimple.newInstance(this.windowIdV_1400, this.getTitle(player), (syncId, inventoryPlayer)->WindowUIWindow.newInstance(this, player, syncId)).open(player);
    }
    
    @Override
    public void close(EntityPlayer player)
    {
        // TODO
    }
    
    @FunctionalInterface
    public interface SlotCreator
    {
        WindowSlot create(Inventory inventory, int index, int x, int y);
    }
}
