package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.entity.EntityItem;
import mz.mzlib.minecraft.entity.EntityLiving;
import mz.mzlib.minecraft.inventory.InventoryPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.window.Window;
import mz.mzlib.minecraft.window.WindowFactory;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
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
    
    /**
     * The open window
     * or player inventory window if no window is open
     */
    @WrapMinecraftFieldAccessor({@VersionName(name="openScreenHandler", end=1400), @VersionName(name="container", begin=1400, end=1600), @VersionName(name="currentScreenHandler", begin=1600)})
    Window getCurrentWindow();
    
    @WrapMinecraftFieldAccessor({@VersionName(name="openScreenHandler", end=1400), @VersionName(name="container", begin=1400, end=1600), @VersionName(name="currentScreenHandler", begin=1600)})
    void setCurrentWindow(Window value);
    
    @WrapMinecraftFieldAccessor({@VersionName(name="playerScreenHandler", end=1400), @VersionName(name="playerContainer", begin=1400, end=1600), @VersionName(name="playerScreenHandler", begin=1600)})
    Window getDefaultWindow();
    
    default Window getWindow(int syncId)
    {
        if(syncId==0 || syncId==-1)
            return this.getDefaultWindow();
        else if(syncId==this.getCurrentWindow().getSyncId())
            return this.getCurrentWindow();
        else
            return Window.create(null);
    }
    
    void sendMessage(Text message);
    @VersionRange(begin=1600)
    @SpecificImpl("sendMessage")
    default void sendMessageV1600(Text message)
    {
        this.sendMessageV1600(message, false);
    }
    @VersionRange(begin=1600)
    @WrapMinecraftMethod(@VersionName(name="sendMessage"))
    void sendMessageV1600(Text message, boolean actionBar);
    
    @WrapMinecraftMethod({@VersionName(name="openHandledScreen", end=1400), @VersionName(name="openContainer", begin=1400, end=1600), @VersionName(name="openHandledScreen", begin=1600)})
    OptionalInt openWindow(WindowFactory windowFactory);
    
    @WrapMinecraftMethod({@VersionName(name="closeHandledScreen", end=1400), @VersionName(name="closeContainer", begin=1400, end=1600), @VersionName(name="closeHandledScreen", begin=1600)})
    void closeInterface();
    
    @WrapMinecraftMethod(@VersionName(name="dropItem"))
    EntityItem drop(ItemStack itemStack, boolean retainOwnership);
    
    void openBook0(ItemStack book);
    
    @SpecificImpl("openBook0")
    @VersionRange(end=1300)
    @WrapMinecraftMethod(@VersionName(name="openEditBookScreen"))
    void openBook0V_1300(ItemStack book);
    
    @VersionRange(begin=1300)
    @WrapMinecraftMethod({@VersionName(name="method_3201", end=1400), @VersionName(name="openEditBookScreen", begin=1400, end=1605), @VersionName(name="useBook", begin=1605)})
    void openBook0V1300(ItemStack book, EnumHand hand);
    
    @SpecificImpl("openBook0")
    @VersionRange(begin=1300)
    default void openBook0V1300(ItemStack book)
    {
        this.openBook0V1300(book, EnumHand.mainHand());
    }
    
    default ItemStack getHandItemStack()
    {
        return this.getInventory().getHandItemStack();
    }
}
