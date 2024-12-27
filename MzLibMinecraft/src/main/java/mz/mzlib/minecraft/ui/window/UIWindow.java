package mz.mzlib.minecraft.ui.window;

import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.player.async.EventPlayerCloseWindowAsync;
import mz.mzlib.minecraft.event.player.async.EventPlayerDisplayItemInWindowAsync;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventorySimple;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UI;
import mz.mzlib.minecraft.window.*;
import mz.mzlib.module.MzModule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

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
    public Map<Integer, Consumer<EventPlayerDisplayItemInWindowAsync>> icons = new ConcurrentHashMap<>();
    public Map<Integer, ButtonHandler> buttons = new HashMap<>();
    
    public void clear()
    {
        this.inventory.clear();
        this.slots.clear();
        this.buttons.clear();
        this.icons.clear();
    }
    
    public void putSlot(int index, BiFunction<Inventory, Integer, WindowSlot> slotCreator)
    {
        this.slots.put(index, slotCreator);
    }
    
    public void putSlot(int index, BiFunction<Inventory, Integer, WindowSlot> slotCreator, ItemStack itemStack)
    {
        this.putSlot(index, slotCreator);
        this.inventory.setItemStack(index, itemStack);
    }
    
    public void putIcon0(int index, Consumer<EventPlayerDisplayItemInWindowAsync> icon)
    {
        this.icons.put(index, icon);
    }
    
    public void putIcon(int index, Function<EntityPlayer, ItemStack> icon)
    {
        this.putIcon0(index, event->
        {
            event.setItemStack(icon.apply(event.getPlayer()));
        });
    }
    
    public void putButton(int index, ButtonHandler handler)
    {
        this.putSlot(index, WindowSlotButton::newInstance);
        this.buttons.put(index, handler);
    }
    public void putButton(int index, Function<EntityPlayer, ItemStack> icon, ButtonHandler handler)
    {
        this.putIcon(index, icon);
        this.putButton(index, handler);
    }
    
    public abstract Text getTitle(EntityPlayer player);
    
    public void initWindow(WindowUIWindow window, EntityPlayer player)
    {
        for(int i = 0; i<inventory.size(); i++)
        {
            BiFunction<Inventory, Integer, WindowSlot> creator = this.slots.get(i);
            window.addSlot(creator==null ? WindowSlot.newInstance(this.inventory, i) : creator.apply(this.inventory, i));
        }
        this.addPlayerInventorySlots(window, player);
    }
    
    public void addPlayerInventorySlots(WindowUIWindow window, EntityPlayer player)
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
    
    public ItemStack quickMove(WindowUIWindow window, EntityPlayer player, int index)
    {
        return window.quickMoveSuper(player, index);
    }
    
    /**
     * @see AbstractWindow#onAction(int, int, WindowActionType, mz.mzlib.minecraft.entity.player.AbstractEntityPlayer)
     */
    public ItemStack onAction(WindowUIWindow window, int index, int data, WindowActionType actionType, EntityPlayer player)
    {
        ButtonHandler button = buttons.get(index);
        if(button!=null)
            button.onClick(player, actionType, data);
        return window.onActionSuper(index, data, actionType, player);
    }
    
    public void onClosed(WindowUIWindow window, EntityPlayer player)
    {
        window.onClosedSuper(player);
    }
    
    @FunctionalInterface
    public interface ButtonHandler
    {
        void onClick(EntityPlayer player, WindowActionType actionType, int data);
    }
    
    @Override
    public void open(EntityPlayer player)
    {
        WindowFactorySimple.newInstance(this.windowType.typeIdV_1400, this.getTitle(player), (syncId, inventoryPlayer)->WindowUIWindow.newInstance(this, player, syncId)).open(player);
    }
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(new EventListener<>(EventPlayerCloseWindowAsync.class, event->event.whenComplete(()->
            {
                if(event.isCancelled())
                    return;
                if(event.getWindow().isInstanceOf(WindowUIWindow::create))
                    MinecraftServer.instance.execute(()->event.getWindow().castTo(WindowUIWindow::create).getUIWindow().onPlayerClose(event.getPlayer()));
            })));
            this.register(new EventListener<>(EventPlayerDisplayItemInWindowAsync.class, event->
            {
                if(!event.getWindow().isInstanceOf(WindowUIWindow::create))
                    return;
                Consumer<EventPlayerDisplayItemInWindowAsync> icon = event.getWindow().castTo(WindowUIWindow::create).getUIWindow().icons.get(event.getSlotIndex());
                if(icon!=null)
                    icon.accept(event);
            }));
        }
    }
}
