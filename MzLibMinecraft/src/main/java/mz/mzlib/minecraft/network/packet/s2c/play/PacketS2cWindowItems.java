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

@WrapMinecraftClass(@VersionName(name="net.minecraft.network.packet.s2c.play.InventoryS2CPacket"))
public interface PacketS2cWindowItems extends Packet
{
    @WrapperCreator
    static PacketS2cWindowItems create(Object wrapped)
    {
        return WrapperObject.create(PacketS2cWindowItems.class, wrapped);
    }
    
    /**
     * nameV_1400: screenId
     * nameV1400_1600: guiId
     * nameV1600: syncId
     */
    @WrapMinecraftFieldAccessor({@VersionName(name="screenId", end=1400), @VersionName(name="field_12146", begin=1400)})
    int getSyncId();
    
    List<?> getContents0();
    
    default List<ItemStack> getContents()
    {
        return new ListWrapper<>(this.getContents0(), ItemStack::create);
    }
    
    @VersionRange(end=1000)
    @WrapMinecraftFieldAccessor(@VersionName(name="stacks"))
    Object[] getContents0V_1000();
    
    @SpecificImpl("getContents0")
    @VersionRange(end=1000)
    default List<?> getContentsV_1000()
    {
        return Arrays.asList(this.getContents0V_1000());
    }
    
    @SpecificImpl("getContents0")
    @VersionRange(begin=1000)
    @WrapMinecraftFieldAccessor({@VersionName(name="field_15348", end=1400), @VersionName(name="slotStackList", begin=1400, end=1600), @VersionName(name="contents", begin=1600)})
    List<?> getContents0V1000();
    
    void setContents0(List<?> value);
    
    @VersionRange(end=1000)
    @WrapMinecraftFieldAccessor(@VersionName(name="stacks"))
    void setContents0V_1000(Object[] value);
    
    @SpecificImpl("setContents0")
    @VersionRange(end=1000)
    default void setContents0V_1000(List<?> value)
    {
        this.setContents0V_1000(value.toArray());
    }
    
    @SpecificImpl("setContents0")
    @VersionRange(begin=1000)
    @WrapMinecraftFieldAccessor({@VersionName(name="field_15348", end=1400), @VersionName(name="slotStackList", begin=1400, end=1600), @VersionName(name="contents", begin=1600)})
    void setContents0V1000(List<?> value);
    
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
    
    @VersionRange(end=1000)
    @WrapConstructor
    PacketS2cWindowItems staticNewInstance0V_1000(int syncId, List<Object> contents);
    
    @VersionRange(begin=1000, end=1701)
    @WrapConstructor
    PacketS2cWindowItems staticNewInstanceV1000_1701(int syncId, DefaultedList contents);
    
    @VersionRange(begin=1701)
    @WrapConstructor
    PacketS2cWindowItems staticNewInstanceV1701(int syncId, int revision, DefaultedList contents, ItemStack cursor);
}
