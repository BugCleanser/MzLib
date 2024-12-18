package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.entity.EntityItem;
import mz.mzlib.minecraft.entity.EntityLiving;
import mz.mzlib.minecraft.inventory.InventoryPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.minecraft.window.WindowFactory;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.OptionalInt;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.player.PlayerEntity"))
public interface AbstractEntityPlayer extends WrapperObject, EntityLiving
{
    @WrapperCreator
    static AbstractEntityPlayer create(Object wrapped)
    {
        return WrapperObject.create(AbstractEntityPlayer.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getGameProfile"))
    GameProfile getGameProfile();
    
    default String getName()
    {
        return this.getGameProfile().getName();
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="inventory"))
    InventoryPlayer getInventory();
    
    @WrapMinecraftFieldAccessor({@VersionName(name="openScreenHandler", end=1400), @VersionName(name="container", begin=1400, end=1600), @VersionName(name="currentScreenHandler", begin=1600)})
    Window getCurrentWindow();
    @WrapMinecraftFieldAccessor({@VersionName(name="openScreenHandler", end=1400), @VersionName(name="container", begin=1400, end=1600), @VersionName(name="currentScreenHandler", begin=1600)})
    void setCurrentWindow(Window value);
    
    @WrapMinecraftMethod({@VersionName(name="openHandledScreen", end=1400), @VersionName(name="openContainer", begin=1400, end=1600), @VersionName(name="openHandledScreen", begin=1600)})
    OptionalInt openWindow(WindowFactory windowFactory);
    
    @WrapMinecraftMethod({@VersionName(name="closeHandledScreen", end=1400), @VersionName(name="closeContainer", begin=1400, end=1600), @VersionName(name="closeHandledScreen", begin=1600)})
    void closeWindow();
    
    @WrapMinecraftMethod(@VersionName(name="dropItem"))
    EntityItem drop(ItemStack itemStack, boolean retainOwnership);
}
