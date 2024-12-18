package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.bukkit.BukkitOnly;
import mz.mzlib.minecraft.bukkit.inventory.BukkitInventoryView;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.util.collection.DefaultedList;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.*;

import java.util.List;

@WrapMinecraftClass({@VersionName(name="net.minecraft.screen.ScreenHandler", end=1400), @VersionName(name="net.minecraft.container.Container",begin=1400, end=1600), @VersionName(name="net.minecraft.screen.ScreenHandler", begin=1600)})
public interface Window extends WrapperObject
{
    @WrapperCreator
    static Window create(Object wrapped)
    {
        return WrapperObject.create(Window.class, wrapped);
    }
    
    @WrapConstructor
    @VersionRange(end=1400)
    Window staticNewInstanceV_1400();
    @WrapConstructor
    @VersionRange(begin=1400)
    Window staticNewInstanceV1400(WindowTypeV1400 type, int syncId);
    
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
    default WindowSlot getSlot(int index)
    {
        return this.getSlots().get(index);
    }
    @WrapMinecraftMethod(@VersionName(name="addSlot"))
    WindowSlot addSlot(WindowSlot slot);
    default void setSlot(int index, WindowSlot slot)
    {
        slot.setSlotIndex(index);
        this.getSlots().set(index, slot);
    }
    
    @WrapMinecraftMethod(@VersionName(name="insertItem"))
    boolean placeIn(ItemStack itemStack, int begin, int end, boolean inverted);
    
    @WrapMinecraftMethod({@VersionName(name="close", end=1904), @VersionName(name="onClosed", begin=1904)})
    void onClosed(AbstractEntityPlayer player);
    
    @BukkitOnly
    @WrapMinecraftMethod(@VersionName(name="getBukkitView"))
    BukkitInventoryView getBukkitView();
    
    /**
     * @return {@link ItemStack#empty()} when no item stack can be transferred, otherwise the original item stack
     */
    @WrapMinecraftMethod({@VersionName(name="transferSlot", end=1903), @VersionName(name="quickMove", begin=1903)})
    ItemStack quickMove(AbstractEntityPlayer player, int index);
    
    @WrapMinecraftMethod(@VersionName(name="canUse"))
    boolean checkReachable(AbstractEntityPlayer player);
    
//    @WrapMinecraftMethod(@VersionName(name="onContentChanged"))
//    void onContentChanged(Inventory inventory);
    
    @WrapMinecraftMethod({@VersionName(name="method_3252", end=1400), @VersionName(name="onSlotClick", begin=1400, end=1700)})
    ItemStack onActionV_1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player);
    @WrapMinecraftMethod(@VersionName(name="onSlotClick", begin=1700))
    void onActionV1700(int index, int data, WindowActionType actionType, AbstractEntityPlayer player);
}
