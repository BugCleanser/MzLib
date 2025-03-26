package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.window.*;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.compound.CompoundSuper;
import mz.mzlib.util.compound.PropAccessor;
import mz.mzlib.util.wrapper.*;

@Compound
public interface WindowUIWindow extends AbstractWindow
{
    WrapperFactory<WindowUIWindow> FACTORY = WrapperFactory.find(WindowUIWindow.class);
    @Deprecated
    @WrapperCreator
    static WindowUIWindow create(Object wrapped)
    {
        return WrapperObject.create(WindowUIWindow.class, wrapped);
    }
    
    WindowUIWindow staticNewInstance0(WindowType type, int syncId);
    
    @WrapConstructor
    @VersionRange(end=1400)
    WindowUIWindow staticNewInstance0V_1400();
    
    @SpecificImpl("staticNewInstance0")
    @VersionRange(end=1400)
    default WindowUIWindow staticNewInstance0V_1400(WindowType type, int syncId)
    {
        return this.staticNewInstance0V_1400();
    }
    
    @WrapConstructor
    @VersionRange(begin=1400)
    WindowUIWindow staticNewInstance0V1400(WindowTypeV1400 type, int syncId);
    @SpecificImpl("staticNewInstance0")
    @VersionRange(begin=1400)
    default WindowUIWindow staticNewInstance0V1400(WindowType type, int syncId)
    {
        return this.staticNewInstance0V1400(type.typeV1400, syncId);
    }
    
    @PropAccessor("uiWindow")
    UIWindow getUIWindow();
    
    @PropAccessor("uiWindow")
    void setUIWindow(UIWindow value);
    
    @PropAccessor("player")
    @Override
    AbstractEntityPlayer getPlayer();
    
    @PropAccessor("player")
    void setPlayer(AbstractEntityPlayer value);
    
    static WindowUIWindow newInstance0(WindowType type, int syncId)
    {
        return create(null).staticNewInstance0(type, syncId);
    }
    
    static WindowUIWindow newInstance(UIWindow uiWindow, AbstractEntityPlayer player, int syncId)
    {
        WindowUIWindow result = newInstance0(uiWindow.windowType, syncId);
        result.setUIWindow(uiWindow);
        result.setPlayer(player);
        uiWindow.initWindow(result, player.castTo(EntityPlayer.FACTORY));
        return result;
    }
    
    default ItemStack quickMoveSuper(AbstractEntityPlayer player, int index)
    {
        return AbstractWindow.super.quickMove(player, index);
    }
    
    @Override
    @CompoundOverride(parent=Window.class, method="quickMove")
    default ItemStack quickMove(AbstractEntityPlayer player, int index)
    {
        return this.getUIWindow().quickMove(this, player.castTo(EntityPlayer.FACTORY), index);
    }
    
    @Override
    default Inventory getInventory()
    {
        return this.getUIWindow().inventory;
    }
    
    @Override
    default void onAction(int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
    {
        this.getUIWindow().onAction(this, index, data, actionType, player.castTo(EntityPlayer.FACTORY));
    }
    
    @CompoundSuper(parent=Window.class, method="onClosed")
    void onClosedSuper(AbstractEntityPlayer player);
    
    @Override
    @CompoundOverride(parent=Window.class, method="onClosed")
    default void onClosed(AbstractEntityPlayer player)
    {
        this.getUIWindow().onClosed(this, player.castTo(EntityPlayer.FACTORY));
    }
}
