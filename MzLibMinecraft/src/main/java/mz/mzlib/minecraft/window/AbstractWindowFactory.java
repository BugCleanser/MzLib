package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.InventoryPlayer;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.text.Nameable", end = 1400),
        @VersionName(name = "net.minecraft.container.ContainerFactory", begin = 1400, end = 1500),
        @VersionName(name = "net.minecraft.container.ContainerProvider", begin = 1500, end = 1502),
        @VersionName(name = "net.minecraft.container.ContainerFactory", begin = 1502, end = 1600),
        @VersionName(name = "net.minecraft.screen.ScreenHandlerFactory", begin = 1600)
    })
public interface AbstractWindowFactory extends WrapperObject
{
    WrapperFactory<AbstractWindowFactory> FACTORY = WrapperFactory.of(AbstractWindowFactory.class);
    @Deprecated
    @WrapperCreator
    static AbstractWindowFactory create(Object wrapped)
    {
        return WrapperObject.create(AbstractWindowFactory.class, wrapped);
    }

    @VersionRange(begin = 1300, end = 1400)
    @WrapMinecraftMethod(@VersionName(name = "method_15540"))
    Text getDefaultNameV1300_1400();

    Text getDisplayNameV_1400();

    @VersionRange(end = 1300)
    @WrapMinecraftMethod(@VersionName(name = "getTranslationKey"))
    String getTranslationKeyV_1300();
    @VersionRange(end = 1400)
    @WrapMinecraftMethod(@VersionName(name = "hasCustomName"))
    boolean hasCustomNameV_1400();
    @SpecificImpl("getDisplayNameV_1400")
    @VersionRange(end = 1300)
    default Text getDisplayNameV_1300()
    {
        if(this.hasCustomNameV_1400())
            return this.getCustomNameV_1400();
        else
            return Text.translatable(this.getTranslationKeyV_1300());
    }
    @VersionRange(end = 1400)
    @WrapMinecraftMethod({
        @VersionName(name = "getName", end = 1300),
        @VersionName(name = "method_15541", begin = 1300)
    })
    Text getCustomNameV_1400();
    @SpecificImpl("getDisplayNameV_1400")
    @VersionRange(begin = 1300, end = 1400)
    @WrapMinecraftMethod(@VersionName(name = "getName"))
    Text getDisplayNameV1300_1400();

    @WrapMinecraftMethod(@VersionName(name = "createMenu", begin = 1400))
    Window createWindowV1400(int syncId, InventoryPlayer inventoryPlayer, AbstractEntityPlayer player);
}
