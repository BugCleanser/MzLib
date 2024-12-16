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
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class UIWindow implements UI
{
    public UnionWindowType windowType;
    public Inventory inventory;
    
    public UIWindow(UnionWindowType windowType, Inventory inventory)
    {
        this.windowType = windowType;
        this.inventory = inventory;
    }
    public UIWindow(UnionWindowType windowType, int size)
    {
        this(windowType, InventorySimple.newInstance(size));
    }
    
    public Map<Integer, BiFunction<Inventory, Integer, WindowSlot>> slots = new HashMap<>();
    public Map<Integer, ButtonHandler> buttons = new HashMap<>();
    
    public void clear()
    {
        this.inventory.clear();
        this.slots.clear();
        this.buttons.clear();
    }
    
    public void setSlot(int index, BiFunction<Inventory, Integer, WindowSlot> slotCreator)
    {
        this.slots.put(index, slotCreator);
    }
    
    public void setSlot(int index, BiFunction<Inventory, Integer, WindowSlot> slotCreator, ItemStack itemStack)
    {
        this.setSlot(index, slotCreator);
        this.inventory.setItemStack(index, itemStack);
    }
    
    public void setButton(int index, ButtonHandler handler)
    {
        this.setSlot(index, WindowSlotButton::newInstance);
        this.buttons.put(index, handler);
    }
    
    public abstract Text getTitle(AbstractEntityPlayer player);
    
    public void initWindow(WindowUIWindow window, AbstractEntityPlayer player)
    {
        for(int i = 0; i<inventory.size(); i++)
        {
            BiFunction<Inventory, Integer, WindowSlot> creator = this.slots.get(i);
            window.addSlot(creator==null ? WindowSlot.newInstance(this.inventory, i) : creator.apply(this.inventory, i));
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
                window.addSlot(creator==null ? WindowSlot.newInstance(player.getInventory(), index) : creator.apply(player.getInventory(), index));
            }
        }
        for(int i = 0; i<9; ++i)
        {
            BiFunction<Inventory, Integer, WindowSlot> creator = this.slots.get(window.getSlots().size());
            window.addSlot(creator==null ? WindowSlot.newInstance(player.getInventory(), i) : creator.apply(player.getInventory(), i));
        }
    }
    
    public void onContentChanged(WindowUIWindow window, Inventory inventory)
    {
        window.onContentChangedSuper(inventory);
    }
    
    public ItemStack quickMove(WindowUIWindow window, AbstractEntityPlayer player, int index)
    {
        return window.quickMove(player, index);
    }
    
    /**
     * @see AbstractWindow#onAction(int, int, WindowActionType, AbstractEntityPlayer)
     */
    public ItemStack onAction(WindowUIWindow window, int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        ButtonHandler button = buttons.get(index);
        if(button!=null)
            button.onClick(player, actionType, data);
        return window.onActionSuper(index, data, actionType, player);
    }
    
    @FunctionalInterface
    public interface ButtonHandler
    {
        void onClick(AbstractEntityPlayer player, WindowActionType actionType, int data);
    }
    
    @Override
    public void open(AbstractEntityPlayer player)
    {
        WindowFactorySimple.newInstance(this.windowType.typeIdV_1400, this.getTitle(player), (syncId, inventoryPlayer)->WindowUIWindow.newInstance(this, player, syncId)).open(player);
    }
    
    @Override
    public void close(AbstractEntityPlayer player)
    {
        if(player.getCurrentWindow().isInstanceOf(WindowUIWindow::create) && player.getCurrentWindow().castTo(WindowUIWindow::create).getUIWindow()==this)
            player.closeWindow();
    }
}
