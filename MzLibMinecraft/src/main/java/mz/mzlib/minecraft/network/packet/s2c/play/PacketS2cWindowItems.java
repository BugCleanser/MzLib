package mz.mzlib.minecraft.network.packet.s2c.play;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.network.packet.Packet;
import mz.mzlib.minecraft.util.collection.DefaultedList;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.*;

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
    
    @WrapMinecraftFieldAccessor({@VersionName(name="guiId", end=1701), @VersionName(name="syncId", begin=1701)})
    int getSyncId();
    @WrapMinecraftFieldAccessor({@VersionName(name="slotStackList", end=1701), @VersionName(name="contents", begin=1701)})
    List<?> getContents0();
    default List<ItemStack> getContents()
    {
        return new ListWrapper<>(this.getContents0(), ItemStack::create);
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
    
    @VersionRange(end=1701)
    @WrapConstructor
    PacketS2cWindowItems staticNewInstanceV_1701(int syncId, DefaultedList contents);
    @VersionRange(begin=1701)
    @WrapConstructor
    PacketS2cWindowItems staticNewInstanceV1701(int syncId, int revision, DefaultedList contents, ItemStack cursor);
}
