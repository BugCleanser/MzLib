package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventorySimple;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UI;
import mz.mzlib.minecraft.window.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class UIWindow implements UI
{
    public UnionWindowType windowType;
    public Inventory inventory;
    
    public UIWindow(UnionWindowType windowType, int size)
    {
        this.windowType=windowType;
        this.inventory = InventorySimple.newInstance(size);
    }
    
    public Map<Integer, BiFunction<Inventory, Integer, WindowSlot>> slots = new HashMap<>();
    
    public void setSlot(int index, BiFunction<Inventory, Integer, WindowSlot> slotCreator)
    {
        this.slots.put(index, slotCreator);
    }
    
    public void setSlot(int index, BiFunction<Inventory, Integer, WindowSlot> slotCreator, ItemStack itemStack)
    {
        this.setSlot(index, slotCreator);
        this.inventory.setItemStack(index, itemStack);
    }
    
    public abstract Text getTitle(EntityPlayer player);
    
    public void initWindow(WindowUIWindow window, AbstractEntityPlayer player)
    {
        for(int i=0; i<inventory.size(); i++)
        {
            BiFunction<Inventory, Integer, WindowSlot> creator = this.slots.get(i);
            window.addSlot(creator==null?WindowSlot.newInstance(this.inventory, i):creator.apply(this.inventory, i));
        }
        this.addPlayerInventorySlots(window, player);
    }
    
    public void addPlayerInventorySlots(WindowUIWindow window, AbstractEntityPlayer player)
    {
        for(int i = 0; i<3; ++i)
        {
            for(int j = 0; j<9; ++j)
            {
                int index = j+i*9+9;
                BiFunction<Inventory, Integer, WindowSlot> creator = this.slots.get(window.getSlots().size());
                window.addSlot(creator==null?WindowSlot.newInstance(player.getInventory(), index):creator.apply(player.getInventory(), index));
            }
        }
        for(int i = 0; i<9; ++i)
        {
            BiFunction<Inventory, Integer, WindowSlot> creator = this.slots.get(window.getSlots().size());
            window.addSlot(creator==null?WindowSlot.newInstance(player.getInventory(), i):creator.apply(player.getInventory(), i));
        }
    }
    
    /**
     * @see AbstractWindow#onAction(int, int, WindowActionType, AbstractEntityPlayer)
     */
    public ItemStack onAction(WindowUIWindow window, int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        return window.onActionSuper(index, data, actionType, player);
    }
    
    @Override
    public void open(EntityPlayer player)
    {
        WindowFactorySimple.newInstance(this.windowType.typeIdV_1400, this.getTitle(player), (syncId, inventoryPlayer)->WindowUIWindow.newInstance(this, player, syncId)).open(player);
    }
    
    @Override
    public void close(EntityPlayer player)
    {
        if(player.getCurrentWindow().isInstanceOf(WindowUIWindow::create) && player.getCurrentWindow().castTo(WindowUIWindow::create).getUIWindow()==this)
            player.closeWindow();
    }
}
