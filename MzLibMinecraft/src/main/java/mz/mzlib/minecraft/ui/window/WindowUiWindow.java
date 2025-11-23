package mz.mzlib.minecraft.ui.window;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.EntityPlayerAbstract;
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
public interface WindowUiWindow extends WindowAbstract
{
    WrapperFactory<WindowUiWindow> FACTORY = WrapperFactory.of(WindowUiWindow.class);
    @Deprecated
    @WrapperCreator
    static WindowUiWindow create(Object wrapped)
    {
        return WrapperObject.create(WindowUiWindow.class, wrapped);
    }

    WindowUiWindow static$newInstance0(WindowType type, int syncId);

    @WrapConstructor
    @VersionRange(end = 1400)
    WindowUiWindow static$newInstance0V_1400();

    @SpecificImpl("static$newInstance0")
    @VersionRange(end = 1400)
    default WindowUiWindow static$newInstance0V_1400(WindowType type, int syncId)
    {
        return this.static$newInstance0V_1400();
    }

    @WrapConstructor
    @VersionRange(begin = 1400)
    WindowUiWindow static$newInstance0V1400(WindowTypeV1400 type, int syncId);
    @SpecificImpl("static$newInstance0")
    @VersionRange(begin = 1400)
    default WindowUiWindow static$newInstance0V1400(WindowType type, int syncId)
    {
        return this.static$newInstance0V1400(type.typeV1400, syncId);
    }

    @PropAccessor("uiWindow")
    UiWindow getUIWindow();

    @PropAccessor("uiWindow")
    void setUIWindow(UiWindow value);

    @PropAccessor("player")
    @Override
    EntityPlayerAbstract getPlayer();

    @PropAccessor("player")
    void setPlayer(EntityPlayerAbstract value);

    static WindowUiWindow newInstance0(WindowType type, int syncId)
    {
        return FACTORY.getStatic().static$newInstance0(type, syncId);
    }

    static WindowUiWindow newInstance(UiWindow uiWindow, EntityPlayerAbstract player, int syncId)
    {
        WindowUiWindow result = newInstance0(uiWindow.windowType, syncId);
        result.setUIWindow(uiWindow);
        result.setPlayer(player);
        uiWindow.initWindow(result, player.castTo(EntityPlayer.FACTORY));
        return result;
    }

    default ItemStack quickMoveSuper(EntityPlayerAbstract player, int index)
    {
        return WindowAbstract.super.quickMove(player, index);
    }

    @Override
    @CompoundOverride(parent = Window.class, method = "quickMove")
    default ItemStack quickMove(EntityPlayerAbstract player, int index)
    {
        return this.getUIWindow().quickMove(this, player.castTo(EntityPlayer.FACTORY), index);
    }

    @Override
    default Inventory getInventory()
    {
        return this.getUIWindow().inventory;
    }

    @Override
    default void onAction(int index, int data, WindowActionType actionType, EntityPlayerAbstract player)
    {
        this.getUIWindow().onAction(this, index, data, actionType, player.castTo(EntityPlayer.FACTORY));
    }

    @CompoundSuper(parent = Window.class, method = "onClosed")
    void onClosedSuper(EntityPlayerAbstract player);

    @Override
    @CompoundOverride(parent = Window.class, method = "onClosed")
    default void onClosed(EntityPlayerAbstract player)
    {
        this.getUIWindow().onClosed(this, player.castTo(EntityPlayer.FACTORY));
    }
}
