package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.util.collection.DefaultedList;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.*;

import java.util.Arrays;
import java.util.List;

@WrapMinecraftClass(
        {
                @VersionName(name = "net.minecraft.network.packet.s2c.play.InventoryS2CPacket", end=1500),
                @VersionName(name = "net.minecraft.server.network.packet.InventoryS2CPacket", begin=1500, end=1502),
                @VersionName(name = "net.minecraft.network.packet.s2c.play.InventoryS2CPacket", begin=1502)
        })
public interface PacketS2cWindowItems extends Packet
{
    @WrapperCreator
    static PacketS2cWindowItems create(Object wrapped)
    {
        return WrapperObject.create(PacketS2cWindowItems.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="screenId", end=1400), @VersionName(name="guiId", begin=1400, end=1600), @VersionName(name="syncId", begin=1600)})
    int getSyncId();
    List<ItemStack> getContents();
    @WrapMinecraftFieldAccessor(@VersionName(name="stacks", end=1400))
    Object[] getContents0V_1400();
    @SpecificImpl("getContents")
    @VersionRange(end=1400)
    default List<ItemStack> getContentsV_1400()
    {
        return new ListWrapper<>(Arrays.asList(this.getContents0V_1400()), ItemStack::create);
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="slotStackList", begin=1400, end=1600), @VersionName(name="contents", begin=1600)})
    List<?> getContents0V1400();
    @SpecificImpl("getContents")
    @VersionRange(begin=1400)
    default List<ItemStack> getContentsV1400()
    {
        return new ListWrapper<>(this.getContents0V1400(), ItemStack::create);
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="contents"))
    void setContents0(List<?> value);
    default void setContents(List<ItemStack> value)
    {
        this.setContents0(new ListWrapped<>(value, ItemStack::create));
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="cursorStack", begin=1701))
    ItemStack getCursorV1701();
    @WrapMinecraftFieldAccessor(@VersionName(name="cursorStack", begin=1701))
    void setCursorV1701(ItemStack itemStack);
    @WrapMinecraftFieldAccessor(@VersionName(name="revision", begin=1701))
    int getRevisionV1701();
    
    @VersionRange(end=1400)
    @WrapConstructor
    PacketS2cWindowItems staticNewInstance0V_1400(int syncId, List<Object> contents);
    @VersionRange(begin=1400, end=1701)
    @WrapConstructor
    PacketS2cWindowItems staticNewInstanceV1400_1701(int syncId, DefaultedList contents);
    @VersionRange(begin=1701)
    @WrapConstructor
    PacketS2cWindowItems staticNewInstanceV1701(int syncId, int revision, DefaultedList contents, ItemStack cursor);
}
