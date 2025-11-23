package mz.mzlib.minecraft.ui.window;

import mz.mzlib.Priority;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.player.async.EventAsyncPlayerDisplayItemInWindow;
import mz.mzlib.minecraft.event.window.async.EventAsyncWindowAction;
import mz.mzlib.minecraft.event.window.async.EventAsyncWindowClose;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventorySimple;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.mzitem.MzItemIconPlaceholder;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.Ui;
import mz.mzlib.minecraft.window.*;
import mz.mzlib.module.MzModule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class UiWindow implements Ui
{
    public WindowType windowType;
    public Inventory inventory;

    public UiWindow(WindowType windowType, Inventory inventory)
    {
        this.windowType = windowType;
        this.inventory = inventory;
    }
    public UiWindow(WindowType windowType, int size)
    {
        this(windowType, InventorySimple.newInstance(size));
    }
    public UiWindow(WindowType windowType)
    {
        this(windowType, windowType.upperSize);
    }

    public Map<Integer, BiFunction<Inventory, Integer, WindowSlot>> slots = new HashMap<>();
    public Map<Integer, Consumer<EventAsyncPlayerDisplayItemInWindow<?>>> icons = new ConcurrentHashMap<>();
    public Map<Integer, ButtonHandler> buttons = new HashMap<>();

    public WindowType getWindowType()
    {
        return this.windowType;
    }
    public Inventory getInventory()
    {
        return this.inventory;
    }

    public void clear()
    {
        this.inventory.clear();
        this.slots.clear();
        this.buttons.clear();
        this.icons.clear();
    }

    public void putSlot(int index, WindowSlot slot)
    {
        this.putSlot(index, (inv, i) -> slot);
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

    public void putIcon0(int index, Consumer<EventAsyncPlayerDisplayItemInWindow<?>> icon)
    {
        this.icons.put(index, icon);
    }

    public void putIcon(int index, Function<EntityPlayer, ItemStack> icon)
    {
        this.putSlot(index, MzItemIconPlaceholder.slot);
        this.putIcon0(index, event -> event.setItemStack(icon.apply(event.getPlayer())));
    }

    public void putButton(int index, ButtonHandler handler)
    {
        this.buttons.put(index, handler);
    }
    public void putButton(int index, Function<EntityPlayer, ItemStack> icon, ButtonHandler handler)
    {
        this.putIcon(index, icon);
        this.putButton(index, handler);
    }

    public abstract Text getTitle(EntityPlayer player);

    public void initWindow(WindowUiWindow window, EntityPlayer player)
    {
        for(int i = 0; i < this.windowType.upperSize; i++)
        {
            BiFunction<Inventory, Integer, WindowSlot> creator = this.slots.get(i);
            window.addSlot(
                creator == null ? WindowSlot.newInstance(this.inventory, i) : creator.apply(this.inventory, i));
        }
        this.addPlayerInventorySlots(window, player);
    }

    public void addPlayerInventorySlots(WindowUiWindow window, EntityPlayer player)
    {
        for(int i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 9; ++j)
            {
                int index = j + i * 9 + 9;
                BiFunction<Inventory, Integer, WindowSlot> creator = this.slots.get(window.getSlots().size());
                window.addSlot(creator == null ?
                    WindowSlot.newInstance(player.getInventory(), index) :
                    creator.apply(player.getInventory(), index));
            }
        }
        for(int i = 0; i < 9; ++i)
        {
            BiFunction<Inventory, Integer, WindowSlot> creator = this.slots.get(window.getSlots().size());
            window.addSlot(creator == null ?
                WindowSlot.newInstance(player.getInventory(), i) :
                creator.apply(player.getInventory(), i));
        }
    }

    public ItemStack quickMove(WindowUiWindow window, EntityPlayer player, int index)
    {
        return window.quickMoveSuper(player, index);
    }

    /**
     * @see WindowAbstract#onAction(int, int, WindowActionType, mz.mzlib.minecraft.entity.player.AbstractEntityPlayer)
     */
    public void onAction(WindowUiWindow window, int index, int data, WindowActionType actionType, EntityPlayer player)
    {
        window.onActionSuper(index, data, actionType, player);
    }

    public void onClosed(WindowUiWindow window, EntityPlayer player)
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
        WindowFactorySimple.newInstance(
            this.windowType, this.getTitle(player),
            (syncId, inventoryPlayer) -> WindowUiWindow.newInstance(this, player, syncId)
        ).open(player);
        player.updateWindow();
    }

    public static class Module extends MzModule
    {
        public static Module instance = new Module();

        @Override
        public void onLoad()
        {
            this.register(new EventListener<>(
                EventAsyncWindowClose.class, Priority.VERY_LOW,
                event -> event.sync(() ->
                {
                    if(event.isCancelled())
                        return;
                    Window window = event.getPlayer().getWindow(event.getSyncId());
                    if(!window.isInstanceOf(WindowUiWindow.FACTORY))
                        return;
                    window.castTo(WindowUiWindow.FACTORY).getUIWindow().onPlayerClose(event.getPlayer());
                })
            ));
            this.register(new EventListener<>(
                EventAsyncWindowAction.class,
                event -> event.sync(() ->
                {
                    if(event.isCancelled())
                        return;
                    Window window = event.getPlayer().getWindow(event.getSyncId());
                    if(!window.isInstanceOf(WindowUiWindow.FACTORY))
                        return;
                    UiWindow ui = window.castTo(WindowUiWindow.FACTORY).getUIWindow();
                    ButtonHandler button = ui.buttons.get(event.getSlotIndex());
                    if(button != null)
                        button.onClick(event.getPlayer(), event.getActionType(), event.getData());
                })
            ));
            this.register(new EventListener<>(
                EventAsyncPlayerDisplayItemInWindow.class, Priority.VERY_LOW,
                event -> event.sync(() ->
                {
                    if(event.isCancelled())
                        return;
                    Window window = event.getPlayer().getWindow(event.getSyncId());
                    if(!window.isInstanceOf(WindowUiWindow.FACTORY))
                        return;
                    Consumer<EventAsyncPlayerDisplayItemInWindow<?>> icon = window.castTo(WindowUiWindow.FACTORY)
                        .getUIWindow().icons.get(event.getSlotIndex());
                    if(icon != null)
                        icon.accept(event);
                })
            ));
        }
    }
}
