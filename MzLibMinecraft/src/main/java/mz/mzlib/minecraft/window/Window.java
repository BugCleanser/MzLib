package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.util.collection.DefaultedList;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.ListWrapper;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@WrapMinecraftClass({@VersionName(name="net.minecraft.screen.ScreenHandler", end=1400), @VersionName(name="net.minecraft.container.Container",begin=1400, end=1600), @VersionName(name="net.minecraft.screen.ScreenHandler", begin=1600)})
public interface Window extends WrapperObject
{
    @WrapperCreator
    static Window create(Object wrapped)
    {
        return WrapperObject.create(Window.class, wrapped);
    }
    
    List<?> getSlots0();
    @SpecificImpl("getSlots0")
    @WrapMinecraftFieldAccessor(@VersionName(name="slots", end=1700))
    List<?> getSlots0V_1700();
    @WrapMinecraftFieldAccessor(@VersionName(name="slots", begin=1700))
    DefaultedList getSlots00V1700();
    @SpecificImpl("getSlots0")
    @VersionRange(begin=1700)
    default List<?> getSlots0V1700()
    {
        return getSlots00V1700().getWrapped();
    }
    default List<WindowSlot> getSlots()
    {
        return new ListWrapper<>(getSlots0(), WindowSlot::create);
    }
    
    @WrapMinecraftMethod(@VersionName(name="insertItem"))
    boolean placeIn(ItemStack itemStack, int begin, int end, boolean inverted);
    
    @WrapMinecraftMethod({@VersionName(name="close", end=1904), @VersionName(name="onClosed", begin=1904)})
    void onClose(AbstractEntityPlayer player);
}
