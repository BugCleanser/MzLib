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
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.OptionalInt;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.player.PlayerEntity"))
public interface AbstractEntityPlayer extends WrapperObject, EntityLiving
{
    WrapperFactory<AbstractEntityPlayer> FACTORY = WrapperFactory.find(AbstractEntityPlayer.class);
    @Deprecated
    @WrapperCreator
    static AbstractEntityPlayer create(Object wrapped)
    {
        return WrapperObject.create(AbstractEntityPlayer.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getGameProfile"))
    GameProfile getGameProfile();
    
    default String getName()
    {
        return this.getGameProfile().getName().unwrap();
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
    
    @VersionRange(begin=1100, end=1300)
    @VersionRange(begin=1600)
    @WrapMinecraftMethod(@VersionName(name="sendMessage"))
    void sendMessageV1100_1300__1600(Text message, boolean actionBar);
    
    @VersionRange(begin=1100, end=1300)
    @VersionRange(begin=1600)
    @SpecificImpl("sendMessage")
    default void sendMessageV1100_1300__1600(Text message)
    {
        this.sendMessageV1100_1300__1600(message, false);
    }
    
    void openWindow(WindowFactory windowFactory);
    
    @SpecificImpl("openWindow")
    @VersionRange(end=1400)
    @WrapMinecraftMethod(@VersionName(name="openHandledScreen"))
    void openWindowV_1400(WindowFactory windowFactory);
    
    @VersionRange(begin=1400)
    @WrapMinecraftMethod({@VersionName(name="openContainer", end=1600), @VersionName(name="openHandledScreen", begin=1600)})
    OptionalInt openWindowV1400(WindowFactory windowFactory);
    
    @SpecificImpl("openWindow")
    @VersionRange(begin=1400)
    default void openWindowSpecificImplV1400(WindowFactory windowFactory)
    {
        OptionalInt ignored = this.openWindowV1400(windowFactory);
    }
    
    @WrapMinecraftMethod({@VersionName(name="closeHandledScreen", end=1400), @VersionName(name="closeContainer", begin=1400, end=1600), @VersionName(name="closeHandledScreen", begin=1600)})
    void closeInterface();
    
    @WrapMinecraftMethod(@VersionName(name="dropItem"))
    EntityItem drop(ItemStack itemStack, boolean retainOwnership);
    
    default void give(ItemStack is)
    {
        this.getInventory().addItemStack(is);
        if(!ItemStack.isEmpty(is))
        {
            EntityItem ignored = this.drop(is, true);
        }
    }
    
    void openBook0(ItemStack book);
    
    @SpecificImpl("openBook0")
    @VersionRange(end=900)
    @WrapMinecraftMethod(@VersionName(name="openBookEditScreen"))
    void openBook0V_900(ItemStack book);
    
    @VersionRange(begin=900)
    @WrapMinecraftMethod({@VersionName(name="method_3201", end=1400), @VersionName(name="openEditBookScreen", begin=1400, end=1605), @VersionName(name="useBook", begin=1605)})
    void openBook0V900(ItemStack book, EnumHandV900 hand);
    
    @SpecificImpl("openBook0")
    @VersionRange(begin=900)
    default void openBook0V900(ItemStack book)
    {
        this.openBook0V900(book, EnumHandV900.mainHand());
    }
    
    default ItemStack getHandItemStack()
    {
        return this.getInventory().getHandItemStack();
    }
}
