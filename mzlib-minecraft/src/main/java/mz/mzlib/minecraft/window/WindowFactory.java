package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.EntityPlayerAbstract;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.inventory.InventoryPlayer;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.Ui;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.screen.NamedScreenHandlerFactory", end = 1400),
        @VersionName(name = "net.minecraft.container.NameableContainerFactory", begin = 1400, end = 1500),
        @VersionName(name = "net.minecraft.container.NameableContainerProvider", begin = 1500, end = 1502),
        @VersionName(name = "net.minecraft.container.NameableContainerFactory", begin = 1502, end = 1600),
        @VersionName(name = "net.minecraft.screen.NamedScreenHandlerFactory", begin = 1600)
    })
public interface WindowFactory extends WrapperObject, WindowFactoryAbstract, Ui
{
    WrapperFactory<WindowFactory> FACTORY = WrapperFactory.of(WindowFactory.class);
    @Deprecated
    @WrapperCreator
    static WindowFactory create(Object wrapped)
    {
        return WrapperObject.create(WindowFactory.class, wrapped);
    }

    @WrapMinecraftMethod(@VersionName(name = "getId", end = 1400))
    String getWindowTypeIdV_1400();

    Text getDisplayName();
    @SpecificImpl("getDisplayName")
    @VersionRange(end = 1400)
    @Override
    Text getDisplayNameV_1400();
    @SpecificImpl("getDisplayName")
    @VersionRange(begin = 1400)
    @WrapMinecraftMethod(@VersionName(name = "getDisplayName"))
    Text getDisplayNameV1400();

    Window createWindow(int syncId, InventoryPlayer inventoryPlayer, EntityPlayerAbstract player);
    @WrapMinecraftMethod(@VersionName(name = "createScreenHandler", end = 1400))
    Window createWindowV_1400(InventoryPlayer inventoryPlayer, EntityPlayerAbstract player);
    @SpecificImpl("createWindow")
    @VersionRange(end = 1400)
    default Window createWindowV_1400(int syncId, InventoryPlayer inventoryPlayer, EntityPlayerAbstract player)
    {
        return createWindowV_1400(inventoryPlayer, player);
    }
    @SpecificImpl("createWindow")
    @VersionRange(begin = 1400)
    default Window createWindowSpecificImplV1400(
        int syncId,
        InventoryPlayer inventoryPlayer,
        EntityPlayerAbstract player)
    {
        return this.createWindowV1400(syncId, inventoryPlayer, player);
    }

    @Override
    default void open(EntityPlayer player)
    {
        player.openWindow(this);
    }
}
