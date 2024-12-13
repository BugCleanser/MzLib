package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
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
import java.util.function.BiFunction;

public abstract class UIWindow implements UI
{
    public String windowIdV_1400;
    public WindowTypeV1400 typeV1400;
    public Inventory inventory;
    
    public UIWindow(String windowIdV_1400, WindowTypeV1400 typeV1400, Inventory inventory)
    {
        this.windowIdV_1400 = windowIdV_1400;
        this.typeV1400 = typeV1400;
        this.inventory = inventory;
    }
    
    public Map<Integer, BiFunction<Inventory, Integer, WindowSlot>> slots = new HashMap<>();
    
    void setSlot(int index, BiFunction<Inventory, Integer, WindowSlot> slotCreator)
    {
        this.slots.put(index, slotCreator);
    }
    
    void setSlot(int index, BiFunction<Inventory, Integer, WindowSlot> slotCreator, ItemStack itemStack)
    {
        this.setSlot(index, slotCreator);
        this.inventory.setItemStack(index, itemStack);
    }
    
    public abstract Text getTitle(EntityPlayer player);
    
    public void initWindow(Window window, AbstractEntityPlayer player)
    {
        for(int i=0; i<inventory.size(); i++)
        {
            BiFunction<Inventory, Integer, WindowSlot> creator = slots.get(i);
            window.addSlot(creator==null?WindowSlot.newInstance(inventory, i):creator.apply(inventory, i));
        }
        this.addPlayerInventorySlots(window, player);
    }
    
    public void addPlayerInventorySlots(Window window, AbstractEntityPlayer player)
    {
        for(int i = 0; i<3; ++i)
        {
            for(int j = 0; j<9; ++j)
            {
                window.addSlot(WindowSlot.newInstance(player.getInventory(), j+i*9+9));
            }
        }
        
        for(int i = 0; i<9; ++i)
        {
            window.addSlot(WindowSlot.newInstance(player.getInventory(), i));
        }
    }
    
    @Override
    public void open(EntityPlayer player)
    {
        WindowFactorySimple.newInstance(this.windowIdV_1400, this.getTitle(player), (syncId, inventoryPlayer)->WindowUIWindow.newInstance(this, player, syncId)).open(player);
    }
    
    @Override
    public void close(EntityPlayer player)
    {
        if(player.getCurrentWindow().isInstanceOf(WindowUIWindow::create) && player.getCurrentWindow().castTo(WindowUIWindow::create).getUIWindow()==this)
            player.closeWindow();
    }
}
