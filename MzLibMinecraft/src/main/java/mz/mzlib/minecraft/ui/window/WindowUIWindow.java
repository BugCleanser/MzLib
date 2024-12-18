package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.window.AbstractWindow;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.minecraft.window.WindowTypeV1400;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.compound.CompoundSuper;
import mz.mzlib.util.compound.PropAccessor;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@Compound
public interface WindowUIWindow extends AbstractWindow
{
    @WrapperCreator
    static WindowUIWindow create(Object wrapped)
    {
        return WrapperObject.create(WindowUIWindow.class, wrapped);
    }
    
    WindowUIWindow staticNewInstance0(WindowTypeV1400 typeV1400, int syncId);
    @WrapConstructor
    @VersionRange(end=1400)
    WindowUIWindow staticNewInstance0V_1400();
    @SpecificImpl("staticNewInstance0")
    @VersionRange(end=1400)
    default WindowUIWindow staticNewInstance0V_1400(WindowTypeV1400 type, int syncId)
    {
        return this.staticNewInstance0V_1400();
    }
    @SpecificImpl("staticNewInstance0")
    @WrapConstructor
    @VersionRange(begin=1400)
    WindowUIWindow staticNewInstance0V1400(WindowTypeV1400 type, int syncId);
    
    @PropAccessor("uiWindow")
    UIWindow getUIWindow();
    @PropAccessor("uiWindow")
    void setUIWindow(UIWindow value);
    
    @PropAccessor("player")
    @Override
    AbstractEntityPlayer getPlayer();
    @PropAccessor("player")
    void setPlayer(AbstractEntityPlayer value);
    
    static WindowUIWindow newInstance0(WindowTypeV1400 typeV1400, int syncId)
    {
        return create(null).staticNewInstance0(typeV1400, syncId);
    }
    static WindowUIWindow newInstance(UIWindow uiWindow, AbstractEntityPlayer player, int syncId)
    {
        WindowUIWindow result = newInstance0(uiWindow.windowType.typeV1400, syncId);
        result.setUIWindow(uiWindow);
        result.setPlayer(player);
        uiWindow.initWindow(result, player);
        return result;
    }
    
    default ItemStack quickMoveSuper(AbstractEntityPlayer player, int index)
    {
        return AbstractWindow.super.quickMove(player, index);
    }
    
//    @CompoundSuper(parent=Window.class, method="onContentChanged")
//    void onContentChangedSuper(Inventory inventory);
//
//    @Override
//    @CompoundOverride(parent=Window.class, method="onContentChanged")
//    default void onContentChanged(Inventory inventory)
//    {
//        this.getUIWindow().onContentChanged(this, inventory);
//    }
    
    @Override
    @CompoundOverride(parent=Window.class, method="quickMove")
    default ItemStack quickMove(AbstractEntityPlayer player, int index)
    {
        return this.getUIWindow().quickMove(this, player, index);
    }
    
    @Override
    default Inventory getInventory()
    {
        return this.getUIWindow().inventory;
    }
    
    @Override
    default ItemStack onAction(int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        return this.getUIWindow().onAction(this, index, data, actionType, player);
    }
    
    @CompoundSuper(parent=Window.class, method="onClosed")
    void onClosedSuper(AbstractEntityPlayer player);
    @Override
    @CompoundOverride(parent=Window.class, method="onClosed")
    default void onClosed(AbstractEntityPlayer player)
    {
        this.getUIWindow().onClosed(this, player);
    }
}
