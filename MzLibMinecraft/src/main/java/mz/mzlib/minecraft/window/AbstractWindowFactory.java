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
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.text.Nameable", end=1400), @VersionName(name="net.minecraft.container.ContainerFactory",begin=1400, end=1600), @VersionName(name="net.minecraft.screen.ScreenHandlerFactory", begin=1600)})
public interface AbstractWindowFactory extends WrapperObject
{
    @WrapperCreator
    static AbstractWindowFactory create(Object wrapped)
    {
        return WrapperObject.create(AbstractWindowFactory.class, wrapped);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getTranslationKey", end=1400))
    String getTranslationKeyV_1400();
    @WrapMinecraftMethod(@VersionName(name="hasCustomName", end=1400))
    boolean hasDisplayNameV_1400();
    @WrapMinecraftMethod(@VersionName(name="getName", end=1400))
    Text getDisplayNameV_1400();
    
    @WrapMinecraftMethod(@VersionName(name="createMenu", begin=1400))
    Window createWindowV1400(int syncId, InventoryPlayer inventoryPlayer, AbstractEntityPlayer player);
}
