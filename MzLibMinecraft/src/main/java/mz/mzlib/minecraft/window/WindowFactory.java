package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.inventory.InventoryPlayer;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UI;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.screen.NamedScreenHandlerFactory", end=1400), @VersionName(name="net.minecraft.container.NameableContainerFactory",begin=1400, end=1600), @VersionName(name="net.minecraft.screen.NamedScreenHandlerFactory", begin=1600)})
public interface WindowFactory extends WrapperObject, AbstractWindowFactory, UI
{
    @WrapperCreator
    static WindowFactory create(Object wrapped)
    {
        return WrapperObject.create(WindowFactory.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getId", end=1400))
    String getWindowIdV_1400();
    
    Text getDisplayName();
    @SpecificImpl("getDisplayName")
    @WrapMinecraftMethod(@VersionName(name="getDisplayName", begin=1400))
    Text getDisplayNameV1400();
    @SpecificImpl("getDisplayName")
    @VersionRange(end=1400)
    Text getDisplayNameV_1400();
    
    Window createWindow(int syncId, InventoryPlayer inventoryPlayer, AbstractEntityPlayer player);
    @WrapMinecraftMethod(@VersionName(name="createScreenHandler", end=1400))
    Window createWindowV_1400(InventoryPlayer inventoryPlayer, AbstractEntityPlayer player);
    @SpecificImpl("createWindow")
    @VersionRange(end=1400)
    default Window createWindowV_1400(int syncId, InventoryPlayer inventoryPlayer, AbstractEntityPlayer player)
    {
        return createWindowV_1400(inventoryPlayer, player);
    }
    @SpecificImpl("createWindow")
    @VersionRange(begin=1400)
    default Window createWindowSpecificImplV1400(int syncId, InventoryPlayer inventoryPlayer, AbstractEntityPlayer player)
    {
        return this.createWindowV1400(syncId, inventoryPlayer, player);
    }
    
    @Override
    default void open(EntityPlayer player)
    {
        player.openWindow(this);
    }
    
    @Override
    default void close(EntityPlayer player)
    {
        // TODO
    }
}
